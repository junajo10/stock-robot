package scraping.connect;

import generic.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import robot.AstroReciever;


/**
 * Class for sending messages to robot, through the internet.
 * @author Erik
 *
 */
public class Connector implements IConnector {

	private final int key = 159286251;
	
	public static void main(String args[]){
		//Startup code. Must have.
		IConnector connector = new Connector();
		Thread recieveThread = new Thread(connector);
		recieveThread.start();
		

		
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


	@Override
	public boolean sendRefresh() {
		// TODO Auto-generated method stub
		return false;
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








