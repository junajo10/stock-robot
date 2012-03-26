package scraping.parser;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * @author Erik
 *
 */
public class Connector {
	private Socket sendRefresh;
	
	public Connector(){
		try {
			sendRefresh = new Socket("localhost", 42000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Method only for testing!
	 */
	public static void main(String[] args){
		Reciever rec = new Reciever();
		Thread recThread = new Thread(rec);
		
	}
}








