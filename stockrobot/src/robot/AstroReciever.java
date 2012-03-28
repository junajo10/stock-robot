package robot;

import generic.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class for receiving notifications that there's new stock data.
 * <p>
 * Receives messages from Connector class on port 45000.
 * <p>
 * @author Erik
 *
 */
public class AstroReciever {
	private Socket sendRefresh;
	private boolean newData;
	private final int secretKey = 19286;
	private String stockTime;
	
	/** Code should be somewhere in Astro.java
	 * <p>
	 * AstroReciever should be run as a thread.
	 * 
	 */

	/**
	 * Sends a message to the robot saying that new data is available.
	 * @return true if it was sent successfully.
	 */
	public boolean newData() {
		DataOutputStream outToServer;
		DataInputStream inFromServer;
		try {
			//TODO: change "localhost" into an correct address,
			sendRefresh = new Socket("localhost", 45000);
			outToServer = new DataOutputStream(sendRefresh.getOutputStream());
			outToServer.write(secretKey);
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(sendRefresh.getInputStream()));
			String latestStocks = fromServer.readLine();
			if(stockTime != latestStocks){
				newData = true;
			}
			else {
				newData = false;
			}
			outToServer.close();
			sendRefresh.close();
			return newData;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Method only for testing!
	 * <p>
	 * Not to be included in final version.
	 */
	public static void main(String[] args){
		AstroReciever rec = new AstroReciever();
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
			System.out.print("New data? " + rec.newData());
		} 			
	 }
	
	/**
	 * Checks if new stock data is available.
	 * <p>
	 * Thread safe method.
	 * <P>
	 * @return true if new stock data is available.
	 * <p>
	 * Otherwise false.
	 */
	public boolean newStockData(){
		if(newData){
			newData = false;
			return true;
		}
		return false;
	}
}
