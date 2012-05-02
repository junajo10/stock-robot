package model.scraping.connect;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Class for sending messages to robot, through the internet.
 * 
 * Uses TCP-sockets, on port 45000 by default.
 * 
 * @author Erik
 *
 */
public class Connector implements IConnector {
	private final int PORT_NR;
	private final int PING_DELAY = 550;
	
	private Map<Socket,Socket > clients;
	
	boolean sendNewData = false;
	ServerSocket recieve;
	
	public Connector(int PORT_NR) {
		this.PORT_NR = PORT_NR;
		clients = new ConcurrentHashMap<Socket, Socket>();
		AstroServer server = new AstroServer();
		AstroSender sender = new AstroSender();
		PingReciever pinger = new PingReciever();
		
		Thread serverThread = new Thread(server);
		Thread astroSender = new Thread(sender);
		Thread pingerThread = new Thread(pinger);
		
		serverThread.start();
		astroSender.start();
		pingerThread.start();
	}

	/**
	 * Sends a message to all connected clients,
	 * that new data is available.
	 * 
	 */
	@Override
	public void sendDataAvailable(int newRows) {
		System.out.println("Sending new data to clients.");
		sendNewData = true;
	}
	
	/**
	 * 
	 * @return Number of connected clients.
	 */
	public int getConnected(){
		return clients.size();
	}
	
	private class AstroSender implements Runnable {

		@Override
		public void run() {
			while (true) {
				Collection<Socket> clientSockets = clients.keySet();
				for (Socket s : clientSockets) {
					String send = "" + System.currentTimeMillis();
					try {
						PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
						if (sendNewData) {
							pw.println(send);
						} else {
							pw.println("");
						}
						pw.flush();
						pw.checkError();
					} catch (Exception e) {
						e.printStackTrace();
						clients.remove(s);
					}
				}
				if(sendNewData){
					sendNewData = false;
				}

				try {
					int delay = 200 - (clientSockets.size()*10);
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class PingReciever implements Runnable {
		public void run() {
			while (true) {
				int delay = 0;
				Collection<Socket> pSockets = clients.keySet();
				for (Socket s : pSockets) {
					try {
						delay = PING_DELAY / pSockets.size();
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					BufferedReader reader = null;
					try {
						reader = new BufferedReader(
								new InputStreamReader(s.getInputStream()));
					} catch (IOException e1) {
						removeClientSocket(s);
					}
					try {
						if (!reader.ready()) {
							removeClientSocket(s);
						} else {
							reader.readLine();
						}
					} catch (IOException e) {
						removeClientSocket(s);
					}
				}
			}
		}
		
		private void removeClientSocket(Socket s){
			System.out.println("Client disconnected.");
			clients.remove(s);
		}
	}
	
	private class AstroServer implements Runnable {

		@Override
		public void run() {
			try {
				recieve = new ServerSocket(PORT_NR);
				while (true) {
					Socket clientSocket = recieve.accept();
					clientSocket.setKeepAlive(true);
					clients.put(clientSocket, clientSocket);
					System.out.println("Client connected, total clients: " + clients.size());
					
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		Connector connect = new Connector(45000);
		while(true){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			connect.sendDataAvailable(22);
		}
	}
}