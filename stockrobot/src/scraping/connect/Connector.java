package scraping.connect;

import generic.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private long latestStocks;
	
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
		DataOutputStream toConnector;
		try {
			ServerSocket recieve = new ServerSocket(45000);
			while(true){
				Socket newDataSocket = recieve.accept();
				BufferedReader fromHarvester = new BufferedReader(new InputStreamReader(newDataSocket.getInputStream()));
				int getKey = fromHarvester.read();
				if(getKey == key){
					toConnector = new DataOutputStream(newDataSocket.getOutputStream());
					toConnector.writeLong(latestStocks);
				}
				//Send ping upwards saying that new data is available.
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
	public void setLatestStockTime(long time) {
		this.latestStocks = time;
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








