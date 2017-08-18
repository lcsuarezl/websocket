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
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 *
 * @author lsuarezl
 */
@WebSocket
public class EchoClient {

    private static final Logger LOG = Log.getLogger(SecureClientSocket.class);

    public static void main(String[] args) {
        String url = "ws://45.33.7.205:8080/WebSocketSample/broadcast";

        WebSocketContainer container = null;//
        javax.websocket.Session session = null;
        try {
            //Tyrus is plugged via ServiceLoader API. See notes above
            container = ContainerProvider.getWebSocketContainer();
            //WS1 is the context-root of my web.app 
            //ratesrv is the  path given in the ServerEndPoint annotation on server implementation
            session = container.connectToServer(WSUSDExchangeClient.class, URI.create(url));
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

    @OnWebSocketConnect
    public void onConnect(Session sess) {
        LOG.info("onConnect({})", sess);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        LOG.info("onClose({}, {})", statusCode, reason);
    }

    @OnWebSocketError
    public void onError(Throwable cause) {
        LOG.warn(cause);
    }

    @OnWebSocketMessage
    public void onMessage(String msg) {
        LOG.info("onMessage() - {}", msg);
    }

}
