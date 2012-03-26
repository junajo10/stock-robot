package scraping.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Reciever implements Runnable {

	/**
	 * Test class to see if it recieves data from Connector class.
	 * <p>
	 * This should be somewhere in the algorithms package.
	 * @author Erik
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ServerSocket recieve = new ServerSocket(45000);
			while(true){
				Socket newDataSocket = recieve.accept();
				BufferedReader fromHarvester = new BufferedReader(new InputStreamReader(newDataSocket.getInputStream()));
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
}
