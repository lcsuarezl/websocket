/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.websocket.utils.model;

/**
 * Modela la estructura de un mensaje
 *
 * {<br/>
 * "url":"message url",  <br/>
 * "content":"message content",<br/>
 * "comand":"message command",<br/>
 * "time": 12381893218,<br/>
 * "active": true<br/>
 * }<br/>
 *
 * @author lsuarezl
 */
public class Message {

    public Message(String url,String message, String command, Long time, boolean active) {
        this.url = url;
        this.message = message;
        this.command = command;
        this.time = time;
        this.active = active;
    }

    public Message() {

    }

    private String url;

    private String message;

    private String command;
    
    private boolean active;

    private Long time;

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the content to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @return the time
     */
    public Long getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Long time) {
        this.time = time;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Message{" + "url=" + url + ", message=" + message + ", command=" + command + ", active=" + active + ", time=" + time + '}';
    }
    
}
