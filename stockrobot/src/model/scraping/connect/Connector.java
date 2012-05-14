package model.scraping.connect;


import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Class for sending messages to robot, through the internet.
 * 
 * Uses TCP-sockets, on port 45000 by default.
 * 
 * @author Erik
 *
 */
public class Connector implements IConnector {
	private final int PORT_NR;
	private final int PING_DELAY = 550;
	
	private Map<Socket,Socket > clients;
	
	boolean sendNewData = false;
	boolean shouldRun;
	
	private ServerSocket recieve;
	private PropertyChangeSupport pcs;
	
	private Thread astroSender;
	private Thread serverThread;
	
	public Connector(int PORT_NR, PropertyChangeSupport pcs) {
		this.pcs = pcs;
		shouldRun = true;
		this.PORT_NR = PORT_NR;
		clients = new ConcurrentHashMap<Socket, Socket>();
		AstroServer server = new AstroServer();
		AstroSender sender = new AstroSender();
		
		//Commented away pinger until this is added to RobotScheduler /Daniel
		//PingReciever pinger = new PingReciever();
		
		serverThread= new Thread(server);
		astroSender = new Thread(sender);
		//Thread pingerThread = new Thread(pinger);
		
		serverThread.start();
		astroSender.start();
		//pingerThread.start();

	}

	/**
	 * Sends a message to all connected clients,
	 * that new data is available.
	 * 
	 */
	@Override
	public void sendDataAvailable(int newRows) {
		sendNewData = true;
	}
	
	/**
	 * 
	 * @return Number of connected clients.
	 */
	public int getConnected(){
		return clients.size();
	}
	
	/**
	 * Shutdowns the server.
	 * <p>
	 * Method used for testing.
	 */
	public void shutdown(){
		pcs.firePropertyChange("Server shutdown.", null, null);
		shouldRun = false;
		Collection<Socket> clientCpy = clients.keySet();
		for(Socket s : clientCpy){
			clients.remove(s);
			try {
				s.close();
			} catch (IOException e) {

			}
		}
		try {
			if(recieve!=null){
				recieve.close();
			}
		} catch (IOException e1) {
			
		}
	}
	
	/**
	 * Shutdowns the server.
	 * <p>
	 * Method used for testing.
	 */
	public boolean isRunning(){
		if(recieve != null){
			return ( getConnected()!=0 || !recieve.isClosed() || serverThread.isAlive() || astroSender.isAlive() );
		}
		return  getConnected()!=0 || serverThread.isAlive() || astroSender.isAlive();
	}
	
	@Override
	public void setPropertyChangeSupport(PropertyChangeSupport pcs) {
		this.pcs = pcs;
	}
	
	
	private class AstroSender implements Runnable {

		@Override
		public void run() {
			while (shouldRun) {
				Collection<Socket> clientSockets = clients.keySet();
				for (Socket s : clientSockets) {
					String send = "" + System.currentTimeMillis();
					try {
						PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
						if (sendNewData) {
							pw.println(send);
						} else {
							pw.println("");
						}
						pw.flush();
						pw.checkError();
					} catch (Exception e) {
						e.printStackTrace();
						clients.remove(s);
						pcs.firePropertyChange("Disconnected.", null, s.getInetAddress().toString());
					}
				}
				if(sendNewData){
					sendNewData = false;
				}

				try {
					int delay = 200 - (clientSockets.size()*10);
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private class PingReciever implements Runnable {
		public void run() {
			while (shouldRun) {
				int delay = 0;
				Collection<Socket> pSockets = clients.keySet();
				for (Socket s : pSockets) {
					try {
						delay = PING_DELAY / pSockets.size();
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					BufferedReader reader = null;
					try {
						reader = new BufferedReader(
								new InputStreamReader(s.getInputStream()));
					} catch (IOException e1) {
						removeClientSocket(s);
					}
					try {
						if(reader != null){
							if (!reader.ready()) {
								removeClientSocket(s);
							} else {
								reader.readLine();
							}
						}
					} catch (IOException e) {
						removeClientSocket(s);
					}
				}
			}
		}
		
		private void removeClientSocket(Socket s){
			pcs.firePropertyChange("Disconnected.", null, s.getInetAddress().toString());
			clients.remove(s);
		}
	}
	
	private class AstroServer implements Runnable {

		@Override
		public void run() {
			try {
				recieve = new ServerSocket(PORT_NR);
				pcs.firePropertyChange("Server up.", null, null);
				while (shouldRun) {
					Socket clientSocket = recieve.accept();					
					clientSocket.setKeepAlive(true);
					clients.put(clientSocket, clientSocket);
					pcs.firePropertyChange("Connected.", null, clientSocket.getInetAddress().toString());
				}
			} catch (IOException e) {
			}
		}
	}
}