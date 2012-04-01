package robot;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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
	
	private final int PORT_NR = 45000;
	private final String SERVER_ADRESS = "localhost";
	private boolean newData = false;
	
	public AstroReciever() {
		AstroClient client = new AstroClient();
		Thread clientThread = new Thread(client);
		clientThread.start();
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
		return false;

	}
	
	private class AstroClient implements Runnable {
		private Socket serverSocket;
		DataOutputStream outToServer;
		InputStreamReader inFromServer;
		BufferedReader fromServer;
		boolean isConnected;
		@Override
		public void run() {
			while(true){
				connect();
				try {
		
					
					while (serverSocket.isConnected()) {
						print("Waiting for new data...");
						
						while (!fromServer.ready()
								&&  !serverSocket.isClosed() && isConnected) {
							//System.out.println("Connected?:" + serverSocket.isClosed());
						}
						String latestStocks = fromServer.readLine();
						print("GOt new data:" + latestStocks);
						newData = true;
						// fromServer.close();
					}
					//fromServer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.err.println("Error: Connection problems, retrying...");
				}
				/*while(serverSocket.isConnected()){
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}*/
			}
			
			
		}
		
		public void print(String str){
			System.out.println(str);
		}
		
		public boolean connect(){
			try {
				serverSocket = new Socket(SERVER_ADRESS, PORT_NR);
				serverSocket.setKeepAlive(true);
				inFromServer = new InputStreamReader(serverSocket.getInputStream());
				fromServer = new BufferedReader(inFromServer);
				print("Connnected");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.err.println("Error: Cant connect to host, reconnecting...");
				connect();
			}

			return true;
		}
		
	}

	/**
	 * Method only for testing!
	 * Change PORT_NR and address to the correct value.
	 * <p>
	 * Not to be included in final version.
	 * <p>
	 */
	public static void main(String[] args) {
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
			System.out.println("New data? " + rec.newData());
		} 			
	 }
}
