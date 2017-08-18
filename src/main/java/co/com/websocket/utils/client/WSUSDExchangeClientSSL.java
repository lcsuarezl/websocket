/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.websocket.utils.client;

import co.com.websocket.utils.server.USDExchangeEndPoint;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.ClientEndpointConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.xnio.IoUtils;


/**
 *
 * @author lsuarezl
 */
@ClientEndpoint
public class WSUSDExchangeClientSSL {

    private static Object waitLock = new Object();

    @OnMessage
    public void onMessage(String message) {
        //the new USD rate arrives from the websocket server side.
        System.out.println("Received msg: " + message);

    }

    private static void wait4TerminateSignal() {
        synchronized (waitLock) {
            try {
                waitLock.wait();
            } catch (InterruptedException e) {

            }
        }
    }

    public static void main(String[] args) {
        WebSocketContainer container = null;//
        Session session = null;
        try {
            //Tyrus is plugged via ServiceLoader API. See notes above
            container = ContainerProvider.getWebSocketContainer();

            //WS1 is the context-root of my web.app 
            //ratesrv is the  path given in the ServerEndPoint annotation on server implementation
            KeyStore keyStore = loadKeyStore("");
            KeyStore trustStore = loadKeyStore("");
            SSLContext context = createSSLContext(keyStore, trustStore, true);
            ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder.create().build();
            clientEndpointConfig.getUserProperties().put("io.undertow.websocket.SSL_CONTEXT", context);


            session = container.connectToServer(USDExchangeEndPoint.class, clientEndpointConfig, URI.create("wss://localhost:8443/WebSocketSample/usdexchangeep"));
            wait4TerminateSignal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static SSLContext createSSLContext(final KeyStore keyStore, final KeyStore trustStore, boolean client) throws IOException {
        KeyManager[] keyManagers = null;
        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, "password".toCharArray());
            keyManagers = keyManagerFactory.getKeyManagers();
        } catch (NoSuchAlgorithmException  e) {
            throw new IOException("Unable to initialise KeyManager[]", e);
        } catch (KeyStoreException ex) {
            Logger.getLogger(WSUSDExchangeClientSSL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(WSUSDExchangeClientSSL.class.getName()).log(Level.SEVERE, null, ex);
        }

        TrustManager[] trustManagers = null;
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);
            trustManagers = trustManagerFactory.getTrustManagers();
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            throw new IOException("Unable to initialise TrustManager[]", e);
        }

        SSLContext sslContext;
        try {
            if (client) {
                sslContext = SSLContext.getInstance("openssl.TLS");
            } else {
                sslContext = SSLContext.getInstance("TLS");
            }
            sslContext.init(keyManagers, trustManagers, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new IOException("Unable to create and initialise the SSLContext", e);
        }

        return sslContext;
    }
    
    
    private static KeyStore loadKeyStore(final String name) throws IOException {
        final InputStream stream = WSUSDExchangeClientSSL.class.getClassLoader().getResourceAsStream(name);
        if(stream == null) {
            throw new RuntimeException("Could not load keystore");
        }
        try {
            KeyStore loadedKeystore = KeyStore.getInstance("JKS");
            loadedKeystore.load(stream, "password".toCharArray());
            return loadedKeystore;
        } catch (NoSuchAlgorithmException | CertificateException e) {
            throw new IOException(String.format("Unable to load KeyStore %s", name), e);
        } catch (KeyStoreException ex) {
            Logger.getLogger(WSUSDExchangeClientSSL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            IoUtils.safeClose(stream);
        }
        return null;
    }
}
