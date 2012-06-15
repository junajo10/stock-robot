package model.robot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AndroidServer {
	
	private boolean shouldRun = true;
	private ServerSocket socket;
	private ArrayList<ClientRunner> clients;
	
	public AndroidServer(){
		clients = new ArrayList<ClientRunner>();
		AcceptServer server = new AcceptServer();
		Thread serverThread = new Thread(server);
		serverThread.start();

	}
	
	public boolean shutdown(){
		shouldRun = false;
		try {
			socket.close();
		} catch (IOException e) {
			
		}
		for(ClientRunner c : clients){
			c.disconnect();
			clients.remove(c);
		}
		return true;
	}
	
	private class AcceptServer implements Runnable {

		public AcceptServer(){
			shouldRun = true;
			try {
				socket = new ServerSocket(44000);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			while(shouldRun){
				try {
					Socket newClient = socket.accept();
					clients.add(new ClientRunner(newClient));
				} catch (IOException e) {
				}
			}
		}
		
	}	
}
