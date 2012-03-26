package robot;

import generic.Log;

import java.io.BufferedReader;
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
public class AstroReciever implements Runnable {
	private boolean newData;
	
	/** Code should be somewhere in Astro.java
	 * <p>
	 * AstroReciever should be run as a thread.
	 * 
	 */
	public static void main(String args[]){
		//Startup code.
		AstroReciever reciever = new AstroReciever();
		Thread recieveThread = new Thread(reciever);
		recieveThread.start();
		
		
		//Example of a blocking call to AstroReciever.
		while(!reciever.newStockData()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Rest of the code...
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ServerSocket recieve = new ServerSocket(45000);
			while(true){
				Socket newDataSocket = recieve.accept();
				//BufferedReader fromHarvester = new BufferedReader(new InputStreamReader(newDataSocket.getInputStream()));
				//Send ping upwards saying that new data is available.
				newData = true;
				Log.instance().log(Log.TAG.DEBUG, "Have recieved new stock data.");
				newDataSocket.close();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
