package model.scraping.connect;

import java.beans.PropertyChangeSupport;
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
 * @deprecated
 */
public class HarvesterServer implements Runnable, IConnector{
	ServerSocket serverSocket;
	List<Socket> clients = new ArrayList<Socket>();
	boolean run  = true;
	boolean sendingDate = false;

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
		while (run) {
			try {
				clients.add(serverSocket.accept());
				Log.log(TAG.NORMAL, "A new client has connected.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** 
	 * For each client create a new socket and send number of new rows to it.
	 * 
	 * If a client is nonresponsive, add it to a removal list.
	 * 
	 * In each socket
	 */
	@Override
	public void sendDataAvailable(final int newRows) {
		final List<Socket> socketsToRemove = new ArrayList<Socket>();
		final List<Thread> dataThreads = new ArrayList<Thread>();
		for (int i = 0; i < clients.size(); i++) {
			final Socket clientSocket = clients.get(i);
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						clientSocket.getOutputStream().write(newRows);
					} catch (IOException e) {
						socketsToRemove.add(clientSocket);
					}

					boolean allDone = true;
					for (Thread dt : dataThreads) {
						if (dt.isAlive()) {
							allDone = false;
							break;
						}
					}

					if (allDone) {
						for (Socket s : socketsToRemove) {
							clients.remove(s);
						}
					}
				}
			});
			t.start();

			dataThreads.add(t);
		}

		for (int i = 0; i < clients.size(); i++) {
			try {
				clients.get(i).getOutputStream().write(newRows);
			} catch (IOException e) {
				Log.log(TAG.NORMAL, "Removing nonresponsive client");
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
		try {
			run = false;
			for (Socket s : clients) {
				s.close();
			}
			serverSocket.close();
		} catch (IOException e) {
		}
	}

	public int getConnected() {
		return clients.size();
	}
	@Override
	public boolean isRunning() {
		return getConnected()!=0 && !serverSocket.isClosed();
	}

	class ClientThread extends Thread {
		Socket client;
		int rows;
		boolean remove = false;
		boolean done = false;
		public ClientThread(Socket client, int rows) {
			this.client = client;
			this.rows = rows;
		}
		@Override
		public void run() {

		}
	}

	@Override
	public void setPropertyChangeSupport(PropertyChangeSupport pcs) {
		// TODO Auto-generated method stub
		
	}
}
