package model.robot;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import utils.global.Log;
import utils.global.Log.TAG;

/**
 * Class for receiving notifications that there's new stock data.
 * <p>
 * Receives a message from Connector class on port 45000,
 * containing the time in ms on the latest parsing.
 * <p>
 * @author Erik
 *
 */
public class AstroReciever {
	
	private final int PORT_NR;
	private final String SERVER_ADRESS;
	private final int RECIEVE_DELAY = 300;
	private final int PING_DELAY = 450;
	
	private boolean newData = false;
	private boolean shouldRun;
	
	private Socket serverSocket;
	private boolean isConnected = false;
	
	
	public AstroReciever(String SERVER_ADRESS, int PORT_NR ) {
		shouldRun = true;
		this.PORT_NR = PORT_NR;
		this.SERVER_ADRESS = SERVER_ADRESS;
	}
	
	public void startReciever() {
		AstroClient client = new AstroClient();
		Pinger ping = new Pinger();
		
		Thread clientThread = new Thread(client);
		Thread pingerThread = new Thread(ping);
		
		clientThread.start();
		pingerThread.start();
	}

	/**
	 * Asks Connector to get the time of the latest parsing in ms.
	 * <p>
	 * 
	 * @return true if it was sent successfully.
	 */
	public boolean newData() {

		if (newData) {
			newData = false;
			return true;
		}
		while(!newData){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
		newData = false;
		return true;
	}
	
	/**
	 * Closes the connection to the server
	 * <p>
	 * Used for testing.
	 *
	 */
	public void close() {
		shouldRun = false;
		try {
			serverSocket.close();
		} catch (IOException e) {
		}
	}
	
	/**
	 * Checks if AstroReciever is connected to server.
	 * <p>
	 * Used for testing.
	 */
	public boolean isConnected() {
		return isConnected;
	}
	
	private class AstroClient implements Runnable {
		
		InputStreamReader inFromServer;
		BufferedReader fromServer;
		@Override
		public void run() {
			while (shouldRun) {
				if(!isConnected){
					connect();
				}
				while (isConnected) {
					try {
						if (!fromServer.ready()) {
							try {
								Thread.sleep(RECIEVE_DELAY / 2);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if (!fromServer.ready()) {
								try {
									Thread.sleep(RECIEVE_DELAY / 2);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
						if (!fromServer.ready()) {
							isConnected = false;
						}
							
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (isConnected) {
						String latestStocks = null;
						try {
							latestStocks = fromServer.readLine();
						} catch (IOException e) {
							isConnected = false;
							e.printStackTrace();
						}
						if(latestStocks != null && !latestStocks.equals("")){
							newData = true;
						} 
						else {
							
						}
					}
				}
				
			}

		}
		
		public boolean connect(){
			try {
				serverSocket = new Socket(SERVER_ADRESS, PORT_NR);
				serverSocket.setKeepAlive(true);
				inFromServer = new InputStreamReader(serverSocket.getInputStream());
				fromServer = new BufferedReader(inFromServer);
				Log.log(TAG.NORMAL, "Connected to server: "+SERVER_ADRESS);
				isConnected = true;
			} catch (IOException e) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Log.log(TAG.ERROR, "Cant connect to host, reconnecting...");
				isConnected = false;
				connect();
			}

			return true;
		}
		
	}
	
	private class Pinger implements Runnable {
		OutputStream outToServer;
		@Override
		public void run() {
			while(shouldRun){
				while(isConnected && serverSocket.isConnected()){
					try {
						outToServer = serverSocket.getOutputStream();
						PrintWriter pw = new PrintWriter(outToServer, true);
						pw.println("");
						pw.flush();
						try {
							Thread.sleep(PING_DELAY);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} catch (IOException e) {
						e.printStackTrace();
						Log.log(TAG.ERROR, "Disconnected from server.");
						isConnected = false;
					}
				}
				while(!isConnected){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}

}
