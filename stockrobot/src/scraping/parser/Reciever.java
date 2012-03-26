package scraping.parser;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Reciever implements Runnable {

	/**
	 * Test class to see if it recieves data from Connector class.
	 * <p>
	 * This should be somewhere in the algorithms package.
	 * @author Erik
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Socket recieve = new Socket("localhost", 45000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
