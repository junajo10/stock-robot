package model.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		private AtomicBoolean sendPortfolioValue;
		private long portfolioValue;

		public AndroidClient(Socket socket) {
			this.s = socket;
			shouldRun = true;
			sendLogEvent = new AtomicBoolean(false);
			isConnected = new AtomicBoolean(true);
			sendPortfolioValue = new AtomicBoolean(false);
			portfolioValue = 0;
			logMessage = "";
			Log.instance().log(TAG.NORMAL, "A new Android Client has connected to ASTRo.");
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
			sendLogEvent.set(true);
			logMessage = msg;
			return true;
		}

		public void sendStockValue(Long value) {
			sendPortfolioValue.set(true);
			portfolioValue = value;
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
					} else if(sendPortfolioValue.get()){
						try {
							Log.instance().log(TAG.NORMAL, "Sending portfoliovalue refresh :" + portfolioValue);
							pw = new PrintWriter(s.getOutputStream(), true);
							pw.println("PFV" + portfolioValue);
							pw.flush();
							sendPortfolioValue.set(false);
						} catch (IOException e) {
							e.printStackTrace();
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
		
		private class Reciever implements Runnable {

			public Reciever(){
			}
			
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
