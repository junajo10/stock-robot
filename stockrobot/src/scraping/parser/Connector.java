package scraping.parser;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

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
}



