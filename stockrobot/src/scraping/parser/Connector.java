package scraping.parser;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class for sending messages to robot, through the internet.
 * @author Erik
 *
 */
public class Connector {
	private Socket sendRefresh;
	
	public Connector(){

	}
	
	/**
	 * Sends a message to the robot saying that new data is available.
	 * @return
	 */
	public boolean sendRefresh() {
		DataOutputStream outToServer;
		try {
			sendRefresh = new Socket("localhost", 45000);
			outToServer = new DataOutputStream(sendRefresh.getOutputStream());
			outToServer.writeBytes("");
			outToServer.close();
			sendRefresh.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	/*
	 * Method only for testing!
	 */
	public static void main(String[] args){
		Reciever rec = new Reciever();
		Thread recThread = new Thread(rec);
		recThread.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connector conn = new Connector();
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.sendRefresh();
		}

		
		
	}


}








