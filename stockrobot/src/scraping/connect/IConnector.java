package scraping.connect;

public interface IConnector extends Runnable{
	public void run();
	public void setLatestStockTime(long time);
}
