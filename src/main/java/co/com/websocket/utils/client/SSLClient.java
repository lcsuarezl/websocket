package co.com.websocket.utils.client;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class SSLClient {
	
	public static void main(String args[]){
		
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
			          System.out.println("### Received: " + message);
			        }
			      });
			      // send a message
			      session.getBasicRemote().sendText("Do or do not, there is no try.");
			    } catch (IOException e) {
			      // do nothing
			    }
			  }
			}, ClientEndpointConfig.Builder.create().build(),
			   URI.create("wss://45.33.7.205:8443/WebSocketSample/broadcast"));
		} catch (DeploymentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
