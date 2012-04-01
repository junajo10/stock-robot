package scraping.connect;

import generic.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
	private final int PORT_NR = 45000;
	private Map<Socket,OutputStream > clients;
	
	public Connector() {
		clients = new HashMap<Socket, OutputStream>();
		AstroServer server = new AstroServer();
		Thread serverThread = new Thread(server);
		serverThread.start();
	}

	/**
	 * Sends a message to all connected clients,
	 * that new data is available.
	 * 
	 */
	@Override
	public void sendDataAvailable() {
		System.out.println("Sending new data to clients.");
		Set<Socket> clientSockets = clients.keySet();
		for(Socket s : clientSockets){
			//s.notify();
			String send = "" + System.currentTimeMillis();
			OutputStream stream = clients.get(s);
			PrintWriter pw = new PrintWriter(stream, true);
			pw.println(send);
			pw.flush();
			System.out.println("Sending to:"+s.getPort());

		}
	}
	
	private class AstroServer implements Runnable {

		@Override
		public void run() {
			ServerSocket recieve;
			try {
				recieve = new ServerSocket(PORT_NR);
				while (true) {
					System.out.println("Connector is accepting calls...");
					Socket clientSocket = recieve.accept();
					clientSocket.setKeepAlive(true);
					OutputStream pw = clientSocket.getOutputStream();
					clients.put(clientSocket, pw);
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








