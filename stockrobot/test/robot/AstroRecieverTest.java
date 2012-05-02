package robot;



import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import model.robot.AstroReciever;
import model.scraping.connect.Connector;

/**
 * Test-class for AstroReciver, also Uses Connector-class as a server.
 * <p>
 * @author Erik
 *
 */

public class AstroRecieverTest {
	Connector server;
	AstroReciever client;
	int port = 45000;
	
	
	/**
	 * Method only for testing!
	 * Change PORT_NR and address to the correct value.
	 * <p>
	 * Not to be included in final version.
	 * <p>

	public static void main(String[] args) {
		//Added this suppress because I'm not sure it the GC will remove this 
		//variable if it's not assigned to a local variable
		@SuppressWarnings("unused")
		AstroReciever rec = new AstroReciever("localhost", 45000);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 			
	 */
	
	@Before
	public void setup(){
		if(server==null){
			server = new Connector(port);
		}
	}
	
	@Test 
	public void SendNewData(){
		client = new AstroReciever("localhost", port);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		server.sendDataAvailable(20);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(client.newData());
	}	
	
	//@Test 
	public void ClientDisconnect(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(client.newData());
	}	
	
	//@Test 
	public void ServerDisconnect(){
		server.sendDataAvailable(20);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(client.newData());
	}
	

}
