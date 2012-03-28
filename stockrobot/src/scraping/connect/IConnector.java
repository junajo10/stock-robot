package scraping.connect;

public interface IConnector extends Runnable{
	public boolean sendRefresh();
	public void run();
}
