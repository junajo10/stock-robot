package model.robot;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import utils.global.Log;
import utils.global.Log.TAG;

public class AndroidClient {
		private Socket s;
		private boolean shouldRun = true;
		private AtomicBoolean sendLogEvent;
		private String logMessage;
		private AtomicBoolean isConnected; 

		public AndroidClient(Socket socket) {
			this.s = socket;
			shouldRun = true;
			sendLogEvent = new AtomicBoolean(false);
			isConnected = new AtomicBoolean(true);
			Log.instance().log(TAG.NORMAL, "A new Android Client has connected to ASTRo.");
			Sender send = new Sender();
			Thread senderThread = new Thread(send);
			senderThread.start();
		}

		public boolean disconnect() {
			shouldRun = false;
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
			sendLogEvent.set(true);
			logMessage = msg;
			return true;
		}
		
		private class Sender implements Runnable {

			@Override
			public void run() {
				while(shouldRun){
					PrintWriter pw;
					if(sendLogEvent.get()){
						try {
							pw = new PrintWriter(s.getOutputStream(), true);
							pw.println("LOG" + logMessage);
							pw.flush();
							sendLogEvent.set(false);
						} catch (IOException e) {
							setDisconnected();
						}
					} else {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}
}
