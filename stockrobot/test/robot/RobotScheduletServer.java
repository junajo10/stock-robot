package robot;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RobotScheduletServer extends Thread{
	ServerSocket serverSocket;
	Socket socket;
	OutputStream apa;
	boolean alive = true;
	int port;
	public RobotScheduletServer(int port) {
		this.port = port;
	}
	
	public boolean sendSignal() {
		try {
			apa.write(22);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void kill() {
		alive = false;
		try {
			apa.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
			apa = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
