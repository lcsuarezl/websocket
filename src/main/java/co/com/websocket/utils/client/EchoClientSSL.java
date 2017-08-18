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
import java.util.concurrent.Future;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.WebSocketClient;

/**
 *
 * @author lsuarezl
 */
@WebSocket
public class EchoClientSSL {
    
    private static final Logger LOG = Log.getLogger(SecureClientSocket.class);


    public static void main(String[] args) {
       futureClient();
    }
    
    private static void otherClient(){
    	
    	String url; 
        url="wss://45.33.7.205:8443/WebSocketSample/broadcast";
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setTrustAll(true); // The magic

        WebSocketClient client = new WebSocketClient(sslContextFactory);
        try {
            client.start();
            EchoClientSSL socket = new EchoClientSSL();
             Future<Session> fut = client.connect(socket, URI.create(url));
            Session session = fut.get();
            while (true){
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                //session.getRemote().sendString("Current Datetime:"+dateFormat.format(date));
                session.getRemote().sendString("{\"msisdn\":\"53\",\"message\":\"hola websocket seguro on date: "+dateFormat.format(date)+"\"}");
                Thread.sleep(1000);
            }
        } catch (Throwable t) {
            LOG.warn(t);
        }
    	
    }
    
    
    private static void futureClient(){
    	 String url; 
         url="wss://45.33.7.205:8443/WebSocketSample/broadcast";
         SslContextFactory sslContextFactory = new SslContextFactory();
         sslContextFactory.setTrustAll(true); // The magic

         WebSocketClient client = new WebSocketClient(sslContextFactory);
         try {
             client.start();
             EchoClientSSL socket = new EchoClientSSL();
             Future<Session> fut = client.connect(socket, URI.create(url));
             Session session = fut.get();
             while (true){
                 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                 Date date = new Date();
                 //session.getRemote().sendString("Current Datetime:"+dateFormat.format(date));
                 session.getRemote().sendString("{\"msisdn\":\"53\",\"message\":\"hola websocket seguro on date: "+dateFormat.format(date)+"\"}");
                 Thread.sleep(1000);
             }
         } catch (Throwable t) {
             LOG.warn(t);
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
