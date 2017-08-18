/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.websocket.utils.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author lsuarezl
 */
@ServerEndpoint("/broadcast") 
public class BroadCastServer {
    
        //queue holds the list of connected clients
    private static Queue<Session> queue = new ConcurrentLinkedQueue<Session>();
     /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was 
     * successful.
     */
    @OnOpen
    public void onOpen(Session session){

        System.out.println(session.getId() + " has opened a connection"); 
        try {
                    queue.add(session);
            
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     */
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("Message from " + session.getId() + ": " + message);
        try {
            session.getBasicRemote().sendText(message);
            sendAll(message, session);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
 
    /**
     * The user closes the connection.
     * 
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session){
        
        System.out.println("Session " +session.getId()+" has ended");
        queue.remove(session);
    }
    
    
    
    private static void sendAll(String msg,Session current ) {
        try {
            /* Send the new rate to all open WebSocket sessions */
            ArrayList<Session> closedSessions = new ArrayList<>();
            for (Session session : queue) {
                if (!session.isOpen()) {
                    System.err.println("Closed session: " + session.getId());
                    closedSessions.add(session);
                } else {
                    if(!current.getId().equals(session.getId())){
                        session.getBasicRemote().sendText(msg);
                    }
                }
            }
            queue.removeAll(closedSessions);
            System.out.println("Sending " + msg + " to " + queue.size() + " clients");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
