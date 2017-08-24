/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.websocket.utils.procesor;

import co.com.websocket.utils.commands.SendSSL;
import co.com.websocket.utils.commands.SendSSLTrustAll;
import co.com.websocket.utils.model.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Procesa un mensaje recibido por websocket
 *
 * @author lsuarezl
 */
public class MessageController {

    private Message message;

    private static final Logger logger = LogManager.getLogger(MessageController.class);

    /**
     * Constructor transforma el string message de entrada en un objeto Message
     *
     * @param message
     */
    public MessageController(String message) {
        logger.info("Inicio construrctor MessageController message input: "+message);
        try {
            Gson gson = new GsonBuilder().create();
            this.message = gson.fromJson(message, Message.class);
            this.message.setTime(System.currentTimeMillis());
        } catch (Throwable t) {
            logger.error("Error construrctor MessageController", t);
        }finally{
            this.message = new Message("wss://localhost:8081/WebsocketUtils/command", "default message builded", "SendSSL" , System.currentTimeMillis(), true);
        }
        logger.info("Fin construrctor MessageController messae:"+this.message.toString());
    }
    
    
    
    public void processMessage(){
        logger.info("Start MessageControler.processMessage()");
        if(message !=null){
            if(message.getCommand().equals("SendSSLTrustAll")){
                logger.info("MessageControler.processMessage.SendSSLTrustAll");
                SendSSLTrustAll sendSSLTrustAll = SendSSLTrustAll.getInstace();
                sendSSLTrustAll.setMessage(message.getMessage());
                sendSSLTrustAll.setActive(message.isActive());
                sendSSLTrustAll.setUrl(message.getUrl());
                sendSSLTrustAll.sendMessage();
            }
            if(message.getCommand().equals("SendSSL")){
                logger.info("MessageControler.processMessage.SendSSL");
                SendSSL sendSSL = SendSSL.getInstance();
                sendSSL.setMessage(message.getMessage());
                sendSSL.setActive(message.isActive());
                sendSSL.setUrl(message.getUrl());
                sendSSL.sendMessage();
            }
        }
        logger.info("End MessageControler.processMessage()");
    }

}
