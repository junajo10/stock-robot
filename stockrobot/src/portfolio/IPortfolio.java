package portfolio;

import java.util.List;

import stock.IStock;

/**
 * 
 * @author daniel
 *
 * Interface for portfolios
 */
public interface IPortfolio {
	/**
	 * Will retrive a list of stocks avalible to the algorithm in charge of this portfolio
	 * @return A list of stockId's
	 */
	List<Integer> getAvalibleStocks();
	
	/**
	 * Will retrive a list of all the stocks that are currently in this portfolio
	 * @return A list of stocks
	 */
	List<IStock> getCurrentStocks();
	
	/**
	 * Will retrive a list of all the stocks that has previosly been in this portfolio
	 * @return A list of stocks
	 */
	List<IStock> getHistoryStocks();
	
	/**
	 * Gets the id of the algorithm in charge of the portfolio
	 * Will give -1 if no algorithm is set yet.
	 * @return The algorithmId
	 */
	int getAlgorithmId();
	
	/**
	 * Sets a new algorithm to a portfolio
	 * @param algorithmId Id of the new algorithm
	 * @return returns True if the change was able to be completed
	 */
	boolean setAlgorithm(int algorithmId);
	
	/**
	 * Will look up how much money has been put in to a portfolio
	 * @return Total invested amount
	 */
	int getInvestedAmount();
	
	/**
	 * Will retrive the current unused amount
	 * @return The unused amount currently in the portfolio
	 */
	int getUnusedAmount();
	
	/**
	 * @return name of the portfolio
	 */
	String getName();
	
	/**
	 * Will invest n more money into the portfolio
	 * @param n Amount to invest
	 * @return True if investment went ok
	 */
	boolean investAmount(int n);
	
	/**
	 * 
	 * @param stocks A list of stocks this portfolio will watch
	 * @return True if setting was ok
	 */
	boolean setStocksToWatch(List<IStock> stocks);
	
	
	void stopBuying(boolean flag);
	void stopSelling(boolean flag);
	boolean isStopBuyingFlagSet();
	boolean isStopSellingFlagSet();
}
