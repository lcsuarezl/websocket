/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.websocket.utils.client;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.websocket.WebSocketContainer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.glassfish.tyrus.client.SslContextConfigurator;
import org.glassfish.tyrus.client.SslEngineConfigurator;


/**
 *
 * @author lsuarezl
 */
public class SSLClientTrustAll {
     private static final Logger LOG = Log.getLogger(SecureClientSocket.class);

    public static void main(String[] args) {
        String url = "wss://45.33.7.205:8443/WebSocketSample/broadcast";
        WebSocketContainer container = null;//
        javax.websocket.Session session = null;
        try {
            System.getProperties().put("javax.net.debug", "all"); //usefull to understand problems
            System.getProperties().put(SSLContextConfigurator.KEY_STORE_FILE, "foofacturaloco.crt");
            System.getProperties().put(SSLContextConfigurator.TRUST_STORE_FILE, "1111");
            System.getProperties().put(SSLContextConfigurator.KEY_STORE_PASSWORD, "1111");
            System.getProperties().put(SSLContextConfigurator.TRUST_STORE_PASSWORD, "111111"); 
            ClientManager client = ClientManager.createClient();
            SslEngineConfigurator sslEngineConfigurator = new SslEngineConfigurator(new SslContextConfigurator());
            sslEngineConfigurator.setHostVerificationEnabled(false); //skip host verification
            client.getProperties().put(ClientProperties.SSL_ENGINE_CONFIGURATOR, sslEngineConfigurator);
            session = client.connectToServer(WSUSDExchangeClient.class, URI.create(url));
            while (true) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                session.getBasicRemote().sendText("Current Datetime:" + dateFormat.format(date));
                Thread.sleep(1000);
            }
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

  
}