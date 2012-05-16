package robot;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RobotSchedulerServerTest extends Thread {
	
	ServerSocket serverSocket;
	Socket socket;
	OutputStream outputStream;
	boolean alive = true;
	int port;
	
	public RobotSchedulerServerTest(int port) {
		this.port = port;
	}
	
	public boolean sendSignal() {
		try {
			outputStream.write(22);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void kill() {
		alive = false;
		try {
			outputStream.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
			outputStream = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}	
}