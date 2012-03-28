package scraping.connect;

import generic.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
	private final int PORT_NR = 45000;
	private long latestStocks;


	@Override
	public void run() {
		try {
			ServerSocket recieve = new ServerSocket(PORT_NR);
			while(true){
				System.out.println("Connector is accepting calls...");
				Socket clientSocket = recieve.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				String send = "" + latestStocks;
				out.print(send);
				out.close();
				clientSocket.close();
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
		System.out.println("New Data");
	}
	



}








