/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.websocket.utils.commands;

/**
 *
 * @author lsuarezl
 */
public interface ISendMessage {
    
    public String getUrl();
    public void setUrl(String url);
    
    
    public String getMessage();
    public void setMessage(String message);
    
    public boolean isActive();
    public void setActive(boolean bool);
    
    
    public void sendMessage();
}
