package portfolio;

import java.util.Date;
import java.util.List;

import stock.IStock;

import generic.Pair;

public interface IPortfolio {
	IStock[] getAvalibleStocks();
	
	IStock[] getCurrentStocks();
	
	Pair<IStock, Date>[] getHistoryStocks();
	
	int getAlgorithmId();
	boolean setAlgorithm(int algorithmId);
	
	int getInvestedAmount();
	int getUnusedAmount();
	
	boolean investNAmount(int n);
	
	boolean setAlgorithm(String algorithm);
	
	boolean setStocksToWatch(List<IStock> stocks);
	
	
	// Köp/sälj stopp
	void stopBuying(boolean flag);
	void stopSelling(boolean flag);
	boolean isStopBuyingFlagSet();
	boolean isStopSellingFlagSet();
}
