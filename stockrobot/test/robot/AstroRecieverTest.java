package robot;

import org.junit.Test;

import model.robot.AstroReciever;

/**
 * Test-class for AstroReciver, also Uses Connector-class as a server.
 * @author Erik
 *
 */
public class AstroRecieverTest {

	
	
	/**
	 * Method only for testing!
	 * Change PORT_NR and address to the correct value.
	 * <p>
	 * Not to be included in final version.
	 * <p>
	 */
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
	 }
	
	@Test 
	public void testClientDisconnect(){
		//IMPLEMENT
	}
	
	@Test 
	public void testServerDisconnect(){
		//IMPLEMENT
	}
	

}
