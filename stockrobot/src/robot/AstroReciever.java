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
	
	/** Code should be somewhere in Astro.java
	 * <p>
	 * AstroReciever should be run as a thread.
	 * 
	 */

	/**
	 * Sends a message to the robot saying that new data is available.
	 * @return true if it was sent successfully.
	 */
	public boolean sendRefresh() {
		DataOutputStream outToServer;
		DataInputStream inFromServer;
		try {
			//TODO: change "localhost" into an correct address,
			sendRefresh = new Socket("localhost", 45000);
			outToServer = new DataOutputStream(sendRefresh.getOutputStream());
			outToServer.write(key);
			inFromServer = new DataInputStream(sendRefresh.getInputStream());
			long latestStocks = inFromServer.readLong();
			outToServer.close();
			sendRefresh.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
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
