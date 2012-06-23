package model.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import utils.global.Log;
import utils.global.Log.TAG;

public class AndroidClient {
		private Socket s;
		private boolean shouldRun = true;

		private Queue<NetworkObject> networkQueue;
		private AtomicBoolean isConnected; 

		public AndroidClient(Socket socket) {
			this.s = socket;
			shouldRun = true;
			isConnected = new AtomicBoolean(true);
			Log.instance().log(TAG.NORMAL, "A new Android Client has connected to ASTRo.");
			networkQueue = new ConcurrentLinkedQueue<NetworkObject>();
			Sender send = new Sender();
			Reciever rec = new Reciever();
			Thread senderThread = new Thread(send);
			Thread recieverThread = new Thread(rec);
			senderThread.start();
			
		}

		public boolean disconnect() {
			shouldRun = false;
			setDisconnected();
			try {
				s.close();
			} catch (IOException e) {
			}
			
			return true;
		}
		
		private void setDisconnected() {
			isConnected.set(false);
		}
		
		public boolean isConnected() {
			return isConnected.get();
		}
		
		public boolean sendLogEvent(String msg){
			networkQueue.add(new NetworkObject(NetworkCode.LogEvent, msg));
			return true;
		}

		public void sendStockValue(Long value) {
			networkQueue.add(new NetworkObject(NetworkCode.PortfolioValue, value + ""));
		}
		
		private class Sender implements Runnable {
			@Override
			public void run() {
				while(shouldRun){
					PrintWriter pw;
					while(!networkQueue.isEmpty()){
						try {
							pw = new PrintWriter(s.getOutputStream(), true);
							pw.println(networkQueue.poll().getMessage());
							pw.flush();
						} catch (IOException e) {
							setDisconnected();
						}
					}			
				}
			}
			
		}
		
		private class Reciever implements Runnable {
			@Override
			public void run() {
				while(shouldRun){
					BufferedReader input = null;
					while(isConnected.get() && shouldRun){
						try {	
							input = new BufferedReader(new InputStreamReader(s.getInputStream()));
							if(!input.ready()){
								String strInput = input.readLine();
								handleInput(strInput);
							}
						} catch (IOException e) {
							disconnect();
						}
					}
				}
			}
			
			private void handleInput(String input){
				
			}
			
		}
}
