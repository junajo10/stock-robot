package scraping.connect;

import generic.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import robot.AstroReciever;


/**
 * Class for sending messages to robot, through the internet.
 * 
 * Uses TCP-sockets, on port 45000 by default.
 * 
 * @author Erik
 *
 */
public class Connector implements IConnector {
	private final int PORT_NR      = 45000;
	private final int PING_PORT_NR = 45001;
	
	private Map<Socket,Socket > clients;
	
	boolean sendNewData = false;
	ServerSocket recieve;
	ServerSocket pingSocket;
	
	public Connector() {
		clients = new HashMap<Socket, Socket>();
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
	public void sendDataAvailable() {
		System.out.println("Sending new data to clients.");
		sendNewData = true;
	}
	
	private class AstroSender implements Runnable {

		@Override
		public void run() {
			while (true) {
				Collection<Socket> clientSockets = clients.values();
				for (Socket s : clientSockets) {
					String send = "" + System.currentTimeMillis();
					try {
						PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
						if (sendNewData) {
							pw.println(send);
							sendNewData = false;
						} else {
							pw.println("");
						}
						pw.flush();
						pw.checkError();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						clients.remove(s);
					}
					// System.out.println("Sending to:"+s.getPort());
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private class AstroPinger implements Runnable {

		@Override
		public void run() {
			try {
				pingSocket = new ServerSocket(PING_PORT_NR);
				while (true) {
					Socket clientPingSocket = pingSocket.accept();
					clientPingSocket.setKeepAlive(true);
					InputStream inputStream = clientPingSocket.getInputStream();
					//clientPingSockets.put(clientPingSocket, inputStream);
					//System.out.print("Client connected, total clients: " + clientPingSockets.size());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private class PingReciever implements Runnable {
		public void run() {
			while (true) {
				Collection<Socket> pSockets = clients.keySet();
				for (Socket s : pSockets) {
					BufferedReader reader = null;
					try {
						reader = new BufferedReader(
								new InputStreamReader(s.getInputStream()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						removeClientSocket(s);
					}
					/*try {
						if (!reader.ready()) {
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						removeClientSocket(s);
					}*/
					try {
						System.out.println(reader.ready());
						if (!reader.ready()) {
							removeClientSocket(s);
						} else {
							String str = reader.readLine();
							System.out.println("Recieved ping from client.");
						}
					} catch (IOException e) {
						removeClientSocket(s);
					}

				}
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				pingSocket = new ServerSocket(PING_PORT_NR);
				while (true) {
					System.out.println("Connector is accepting calls...");
					Socket clientSocket = recieve.accept();
					Socket clientPingSocket = pingSocket.accept();
					clientPingSocket.setKeepAlive(true);
					clientSocket.setKeepAlive(true);
					clients.put(clientPingSocket, clientSocket);
					System.out.print("Client connected, total clients: " + clients.size());
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public static void main(String[] args){
		Connector connect = new Connector();
		while(true){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			connect.sendDataAvailable();
		}
	}


}








