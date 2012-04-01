package robot;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
		DataInputStream inFromServer;
		@Override
		public void run() {
			connect();
			while(true){
				try {
					BufferedReader fromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
					while(!fromServer.ready()){}
					String latestStocks = fromServer.readLine();
					newData = true;
					fromServer.close();
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("Error: Connection problems, retrying...");
					connect();
				}
			}
			
			
		}
		
		public boolean connect(){
			try {
				serverSocket = new Socket(SERVER_ADRESS, PORT_NR);
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
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("New data? " + rec.newData());
		} 			
	 }
}
