package scraping.connect;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Class for sending messages to robot, through the internet.
 * @author Erik
 *
 */
public class Connector implements IConnector {
	private Socket sendRefresh;
	
	/**
	 * Sends a message to the robot saying that new data is available.
	 * @return true if it was sent successfully.
	 */
	public boolean sendRefresh() {
		DataOutputStream outToServer;
		try {
			//TODO: change "localhost" into an correct address,
			sendRefresh = new Socket("localhost", 45000);
			outToServer = new DataOutputStream(sendRefresh.getOutputStream());
			outToServer.writeBytes("");
			outToServer.close();
			sendRefresh.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Method only for testing!
	 * <p>
	 * Not to be included in final version.
	 */
	/*public static void main(String[] args){
		Reciever rec = new Reciever();
		Thread recThread = new Thread(rec);
		recThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		Connector conn = new Connector();
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			conn.sendRefresh();
		} 			
	 }*/


}








