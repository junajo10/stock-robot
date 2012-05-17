package model.portfolio;


import java.util.List;

import utils.global.Pair;

import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;

/**
 * @author Daniel
 *
 * Interface for portfolios
 */
public interface IPortfolio {
	/**
	 * Will retrieve a list of stockNames available to the algorithm in charge of this portfolio
	 * @return A list of stockId's
	 */
	List<StockNames> getAvalibleStocks();
	
	/**
	 * Will retrieve a list of all the stocks that are currently in this portfolio
	 * @return A list of stocks
	 */
	List<StockPrices> getCurrentStocks();
	
	/**
	 * Will retrieve a list of all the stocks that has previosly been in this portfolio
	 * @return A list of stocks
	 */
	List<Pair<StockPrices, StockPrices>> getHistoryStocks();
	
	/**
	 * Starts this portfolios algorithm
	 * @return returns false if something went wrong, like no algorithm set.
	 */
	boolean updateAlgorithm();
	/**
	 * Sets a new algorithm to a portfolio
	 * @param algorithmId Id of the new algorithm
	 * @return returns True if the change was able to be completed
	 */
	boolean setAlgorithm(IAlgorithm algorithm);
	
	/**
	 * Returns whatever was set by setAlgorithm
	 * @return the current algorithm that was sent in by setAlgorithm
	 */
	IAlgorithm getAlgorithm();
	
	/**
	 * Will look up how much money has been put in to a portfolio
	 * @return Total invested amount
	 */
	long getInvestedAmount();
	
	/**
	 * Will retrieve the current unused amount
	 * @return The unused amount currently in the portfolio
	 */
	long getUnusedAmount();
	
	/**
	 * @return name of the portfolio
	 */
	String getName();
	
	/**
	 * Will invest n more money into the portfolio
	 * @param n Amount to invest
	 * @return True if investment went ok
	 */
	boolean investAmount(long n);
		
	/**
	 * Will set the stop buying flag
	 * @param flag 
	 */
	void stopBuying(boolean flag);
	
	/**
	 * Will set the stop selling flag
	 * @param flag
	 */
	void stopSelling(boolean flag);
	
	/**
	 * @return True if stopBuying flag is set
	 */
	boolean isStopBuyingFlagSet();
	
	/**
	 * @return True if stopSelling flag is set
	 */
	boolean isStopSellingFlagSet();
	
	/**
	 * @return Returns the portfolioEntity coupled with this portfolio
	 */
	PortfolioEntity getPortfolioTable();
	
	/**
	 * Calculates the current worth this portfolio has.
	 * Balance + for all stocks not sold: worth += this stocks latest value
	 * @return The worth of this portfolio
	 */
	long getCurrentWorth();

	/**
	 * Updates the algorithm's settings
	 */
	void updateSettings();

}
