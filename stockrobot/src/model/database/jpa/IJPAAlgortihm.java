package model.database.jpa;


import java.util.Date;
import java.util.List;

import utils.global.Pair;

import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;



/**
 * This interface that algorithms use.
 * 
 * @author Daniel
 * 
 */
public interface IJPAAlgortihm {
	/**
	 * Gets the stockNames this portfolio is set to watch.
	 * @param portfolioTable The portfolio
	 * @return A list of stockNames
	 */
	public List<StockNames> getStockNames(PortfolioEntity portfolioTable);
	
	/**
	 * Returns a list of currently owned stocks.
	 * @param portfolioTable The portfolio.
	 * @return A List of currently owned stocks.
	 */
	public List<StockPrices> getCurrentStocks(PortfolioEntity portfolioTable);
	
	/**
	 * Returns a list of pairs with old stocks, left is the stock point when it was bought
	 * the right one is the stock point of when it was sold
	 * @param portfolioTable
	 * @return
	 */
	public List<Pair<StockPrices, StockPrices>> getOldStocks(
			PortfolioEntity portfolioTable);
	
	/**
	 * Will give back stockInformation in the form of: A list of pairs, where the left side is the StockName, and the right part is a list of nLatest maxSize with StockPrices
	 * @return A list Pairs of StockNames, List of StockPrices
	 */
	public List<Pair<StockNames, List<StockPrices>>> getStockInfo(int nLatest) ;

	/**
	 * Method for getting ALL prices for a specific stock  
	 * 
	 * @param st Stock to get for
	 * @return List of all prices
	 */
	public List<StockPrices> getPricesForStock( StockNames st );

	/**
	 * Method for querying a specific stock's value within a specified time span. 
	 * 
	 * @param st The stock to query for
	 * @param start Date where to start looking for prices
	 * @param end Date where to stop looking for prices
	 * @return
	 */
	public List<StockPrices> getPricesForStockPeriod( StockNames st, Date start, Date end );

	/**
	 * Will give a list of all the different StockNames
	 * @return A list of stockNames
	 */
	public List<StockNames> getAllStockNames();
	
	/**
	 * Given a StockPrice will return the latest stock price with the same name.
	 * @param from The old stockPrice
	 * @return The latest stockPrice with same name as given stockPrice
	 */
	public StockPrices getLatestStockPrice(StockPrices from);
	/**
	 * Returns the total amount invested in this portfolio
	 * @param portfolioTable The portfolio to be audited.
	 * @return The total amount invested
	 */
	public long getTotalInvestedAmount(PortfolioEntity portfolioTable);
	
	/**
	 * Returns a list of Stock prices with same name as a given StockPrice, with max size of a given value.
	 * @param from The StockPrice that has the same name 
	 * @param n How many max results
	 * @return A list of stockPrices
	 */
	public List<StockPrices> getNLatest(StockPrices from, int n);
	
	/**
	 * Returns a list of portfolioHistorys associated with a given portfolio
	 * @param portfolio
	 * @return a list of portfolioHistorys
	 */
	public List<PortfolioHistory> getPortfolioHistory(PortfolioEntity portfolio);

	/**
	 * Returns a list of portfolioHistorys associated with a given portfolio and a StockPrice
	 * @param sp
	 * @param portfolioTable
	 * @return a list of portfolioHistorys
	 */
	public List<PortfolioHistory> getPortfolioHistory(StockPrices sp,
			PortfolioEntity portfolioTable);
	
	/**
	 * Updates an object to the database
	 * @param o The object to be updated.
	 * @return True if it went ok.
	 */
	public boolean updateObject(Object o);
	
	public List<PortfolioHistory> getCurrentStocksHistory(PortfolioEntity portfolioTable);
}
