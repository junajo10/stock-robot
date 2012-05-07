package model.scraping.connect;

public interface IConnector {
	public void sendDataAvailable(int newRows);
	public void shutdown();
	public int getConnected();
}
