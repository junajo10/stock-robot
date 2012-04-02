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
import java.util.concurrent.ConcurrentHashMap;

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
	public void sendDataAvailable() {
		System.out.println("Sending new data to clients.");
		sendNewData = true;
	}
	
	private class AstroSender implements Runnable {

		@Override
		public void run() {
			while (true) {
				Collection<Socket> clientSockets = clients.values();
				boolean newDataSent;
				for (Socket s : clientSockets) {
					if(sendNewData){
						newDataSent = true;
					}
					newDataSent = true;
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
						// TODO Auto-generated catch block
						e.printStackTrace();
						clients.remove(s);
					}
					// System.out.println("Sending to:"+s.getPort());
				}
				if(sendNewData){
					newDataSent = false;
					sendNewData = false;
				}

				try {
					int delay = 200 - (clientSockets.size()*10);
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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
						delay = 550 / pSockets.size();
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
							String str = reader.readLine();
							System.out.println("Recieved ping from client. Delay:"+delay);
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








