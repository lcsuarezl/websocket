/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.websocket.utils.commands;

import co.com.websocket.utils.client.WSUSDExchangeClient;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.websocket.WebSocketContainer;
import org.apache.log4j.LogManager;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.glassfish.tyrus.client.SslContextConfigurator;
import org.glassfish.tyrus.client.SslEngineConfigurator;

/**
 *
 * @author lsuarezl
 */
public class SendSSLTrustAll implements ISendMessage{

    private static SendSSLTrustAll instance=null; 
    
    private static final org.apache.log4j.Logger logger = LogManager.getLogger(SendSSLTrustAll.class);
    
    private String url="wss://45.33.7.205:8443/WebSocketSample/broadcast"; 
    
    private String message="default message"; 
    
    private boolean active = true; 
    
    
    public static SendSSLTrustAll getInstace(){
        if(instance == null){
            instance = new SendSSLTrustAll();
        }
        return instance; 
    }

    private  SendSSLTrustAll() {
        super();
    }
    
    @Override
    public void sendMessage(){
        logger.info("Start SendSSLTrustAll.sendMessage");
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
            session = client.connectToServer(WSUSDExchangeClient.class, URI.create(this.url));
            while (isActive()) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String msg = "["+dateFormat.format(date)+"] >"+message;
                session.getBasicRemote().sendText(msg);
                logger.info("Start SendSSLTrustAll.sendMessage sendText:"+msg);
                Thread.sleep(1000);
            }
        } catch (Throwable t) {
            logger.error("Error", t);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("End SendSSLTrustAll.sendMessage");
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
