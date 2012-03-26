package robot;

import generic.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class AstroReciever implements Runnable {

	private boolean newData;
	/**
	 * Class to see if it recieves data from Connector class.
	 * Recieves message from Harvester saying that new data is available.
	 * <p>
	 * Part of this class should be somewhere in the algorithms package.
	 * <p>
	 * @author Erik
	 */
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
				Log.instance().log(Log.TAG.DEBUG, "Have recieved new stock data!");
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
	 * Checks if new stockdata is available.
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
