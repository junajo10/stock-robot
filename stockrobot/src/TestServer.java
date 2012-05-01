

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(44433);
			Socket apa = server.accept();
			System.out.println("acceptat");
			while (true) {
				OutputStream out = apa.getOutputStream();
				
				System.out.println("Writing to outchannel");
				out.write(33);
				
				Thread.sleep(10000);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
