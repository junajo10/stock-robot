package model.robot;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * @author Daniel
 */
public class RobotSchedulerClient extends Thread{
	private InputStream inputStream;
	private Socket socket;
	boolean keepRunning = true;
	private RobotScheduler robotScheduler;
	private String host;
	private int port;

	public RobotSchedulerClient(RobotScheduler robotScheduler, String host, int port) throws Exception {
		this.host = host;
		this.port = port;

		this.robotScheduler = robotScheduler;
		try {
			socket = new Socket(host, port);
			inputStream = socket.getInputStream();
		} catch (ConnectException e) {
			throw new Exception();
		} catch (UnknownHostException e) {
			keepRunning = false;
			e.printStackTrace();
		} catch (IOException e) {
			keepRunning = false;
			e.printStackTrace();
		}
	}
	public boolean isAlivee() {
		return keepRunning;
	}
	@Override
	public void run() {
		while(keepRunning) {
			try {
				// Blocking call:
				inputStream.read();

				robotScheduler.doWork();
			} catch (IOException e) {
				// Try to reconnect if fail it fails give up.
				if (socket != null) {
					try {
						socket.close();
						socket = new Socket(host, port);
						inputStream = socket.getInputStream();
					}
					catch (UnknownHostException e2) {
						keepRunning = false;
					} catch (IOException e2) {
						keepRunning = false;
					}
				} 
			}
		}
	}
	public void cleanup() {
		keepRunning = false;
		socket = null;
		try {
			inputStream.close();
		} catch (IOException e) {
		} 

	}
}
