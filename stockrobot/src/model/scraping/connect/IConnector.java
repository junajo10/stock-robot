package model.scraping.connect;

import java.beans.PropertyChangeSupport;

public interface IConnector {
	public void sendDataAvailable(int newRows);
	public void shutdown();
	public int getConnected();
	public boolean isRunning();
	void setPropertyChangeSupport(PropertyChangeSupport pcs);
}
