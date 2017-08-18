/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.websocket.utils.client;

import java.net.URI;
import java.util.concurrent.Future;
import javax.websocket.OnMessage;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.WebSocketClient;

/**
 *
 * @author lsuarezl
 */
@WebSocket
public class WSUSDExchangeClientS {
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
        String url = "wss://localhost:8443/WebSocketSample/usdexchange";
        Session session = null;
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setTrustAll(true); // The magic
        WebSocketClient client = new WebSocketClient(sslContextFactory);
        try
        {
            client.start();
            SecureClientSocket socket = new SecureClientSocket();
            Future<Session> fut = client.connect(socket,URI.create(url));
            session = fut.get();
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
}
