package model.robot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


import utils.global.Log;
import utils.global.Log.TAG;

public class AndroidServer {
	
	private boolean shouldRun = true;
	private ServerSocket socket;
	private ArrayList<AndroidClient> clients;
	static AndroidServer instance;
	
	public static AndroidServer instance(){
		if(instance == null){
			instance = new AndroidServer();
			return instance;
		} else {
			return instance;
		}
	}
	
	public boolean sendLogEvent(String msg){
		for(AndroidClient c: clients){
			c.sendLogEvent(msg);
		}
		return true;
	}
	
	private AndroidServer(){
		clients = new ArrayList<AndroidClient>();
		AcceptServer server = new AcceptServer();
		Thread serverThread = new Thread(server);
		serverThread.start();
		new Thread(new Pinger()).start();

	}
	
	
	public boolean shutdown(){
		shouldRun = false;
		try {
			socket.close();
		} catch (IOException e) {
			
		}
		for(AndroidClient c : clients){
			c.disconnect();
			clients.remove(c);
		}
		return true;
	}
	
	private class Pinger implements Runnable {

		@Override
		public void run() {
			while(true){
				Log.instance().log(TAG.NORMAL, "Sending a little ping.");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
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
					clients.add(new AndroidClient(newClient));
				} catch (IOException e) {
				}
			}
		}
		
	}	
}
