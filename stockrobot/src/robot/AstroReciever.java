package robot;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

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
	private String stockTime = System.currentTimeMillis() + "";

	/**
	 * Asks Connector to get the time of the latest parsing in ms.
	 * <p>
	 * 
	 * @return true if it was sent successfully.
	 */
	public boolean newData() {
		DataOutputStream outToServer;
		DataInputStream inFromServer;
		Socket serverSocket;
		try {
			serverSocket = new Socket(SERVER_ADRESS, PORT_NR);
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			while(!fromServer.ready()){}
			String latestStocks = fromServer.readLine();
			fromServer.close();
			serverSocket.close();
			if(!stockTime.equals(latestStocks)){
				stockTime = latestStocks;
				return true;
			}
			return false;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Method only for testing!
	 * <p>
	 * Not to be included in final version.
	 * <p>
	 */
	public static void main(String[] args){
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
