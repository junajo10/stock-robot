package model.scraping.connect;

import java.beans.PropertyChangeSupport;

public interface IConnector {
	void startConnector();
	void sendDataAvailable(int newRows);
	void shutdown();
	int getConnected();
	boolean isRunning();
	void setPropertyChangeSupport(PropertyChangeSupport pcs);
}
