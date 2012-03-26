package scraping.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Reciever implements Runnable {

	private boolean newData;
	/**
	 * Test class to see if it recieves data from Connector class.
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
				System.out.println("Have recieved data!");
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
	 * @return true if new stock data is available.
	 * otherwise false
	 */
	public boolean newStockData(){
		if(newData){
			newData = false;
			return true;
		}
		return false;
	}
}
