package model.scraping.connect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


import utils.global.Log;
import utils.global.Log.TAG;

/**
 * 
 * @author Daniel
 */
public class HarvesterServer implements Runnable, IConnector{
	ServerSocket serverSocket;
	List<Socket> clients = new ArrayList<Socket>();
	
	public HarvesterServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Thread thread = new Thread(this);
		//thread.setDaemon(true);
		thread.start();
	}
	@Override
	public void run() {
		while (true) {
			try {
				clients.add(serverSocket.accept());
				Log.instance().log(TAG.NORMAL, "A new client has connected.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void sendDataAvailable(int newRows) {
		for (int i = 0; i < clients.size(); i++) {
			try {
				clients.get(i).getOutputStream().write(newRows);
			} catch (IOException e) {
				Log.instance().log(TAG.NORMAL, "Removing nonresponsive client");
				clients.remove(i);
				i--;
			}
		}
	}
	
	public static void main(String args[]) {
		new HarvesterServer(12333);
	}
	
	@Override
	public void shutdown() {

	}

	public int getConnected() {
		return clients.size();
	}
	@Override
	public boolean isRunning() {
		return getConnected()!=0;
	}
	

}
