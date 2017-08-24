/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.websocket.utils.commands;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import org.apache.log4j.LogManager;

/**
 *
 * @author lsuarezl
 */
public class SendSSL implements ISendMessage {

    private static SendSSL instance = null;

    private static final org.apache.log4j.Logger logger = LogManager.getLogger(SendSSL.class);

    private String url = "wss://45.33.7.205:8443/WebSocketSample/broadcast";

    private String message = "default message";

    private boolean active = true;

    public static SendSSL getInstance() {
        if (instance == null) {
            instance = new SendSSL();
        }
        return instance;
    }

    private SendSSL() {
        super();
    }

    @Override
    public void sendMessage() {
        logger.info("Start SendSSL.sendMessage");
        final WebSocketContainer client = ContainerProvider.getWebSocketContainer();
        try {
            client.connectToServer(new Endpoint() {
                @Override
                public void onOpen(Session session, EndpointConfig EndpointConfig) {
                    try {
                        // register message handler - will just print out the
                        // received message on standard output.
                        session.addMessageHandler(new MessageHandler.Whole<String>() {
                            @Override
                            public void onMessage(String message) {
                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                Date date = new Date();
                                logger.info("### Received at [" + dateFormat.format(date) + "] >" + message);

                            }
                        });
                        while (isActive()) {
                            // send a message
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date date = new Date();
                            String msg = "["+dateFormat.format(date)+"] >"+message;
                            session.getBasicRemote().sendText(msg);
                            logger.info("Start SendSSL.sendMessage sendText:"+msg);
                            Thread.sleep(1000);
                        }
                    } catch (IOException e) {
                        logger.error("Error", e);
                    } catch (InterruptedException ex) {
                        logger.error("Error", ex);
                    }
                }
            }, ClientEndpointConfig.Builder.create().build(),
                    URI.create(url));
        } catch (DeploymentException e) {
            logger.error("Error", e);
        } catch (IOException e) {
            logger.error("Error", e);
        }
        logger.info("End SendSSL.sendMessage");
    }

    /**
     * @return the url
     */
    @Override
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the active
     */
    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

}
