package database.jpa;

import generic.Pair;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import database.jpa.tables.StocksToWatch;

public interface IJPAHelper {
	public void stopJPASystem();
	/**
	 * Returns a list of all the algorithms.
	 * @return a list of all the algorithms.
	 */
	public List<AlgorithmEntitys> getAllAlgorithms();
	/**
	 * Will give back all portfolios in the JPA system.
	 * @return A list with PortfolioTables
	 */
	public List<PortfolioEntitys> getAllPortfolios();
	/**
	 * Will give back all stockPrices
	 * @return A list of stockPrices
	 */
	public List<StockPrices> getAllStockPrices();
	/**
	 * Will give back all stockPrices
	 * @return A list of stockPrices
	 */
	public List<StockPrices> getAllStockPricesReverseOrdered();
	/**
	 * Will give back all PortfolioInvestment
	 * @return A list of PortfolioInvestment
	 */
	public List<PortfolioInvestment> getAllPortfolioInvestment();
	/**
	 * Will give back all StocksToWatch
	 * @return A list of StocksToWatch
	 */
	public List<StocksToWatch> getAllStocksToWatch();
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
	 * Method for querying a specific stock's value within a specified timespan. 
	 * 
	 * @param st The stock to query for
	 * @param start Date where to start looking for prices
	 * @param end Date where to stop looking for prices
	 * @return
	 */
	public List<StockPrices> getPricesForStockPeriod( StockNames st, Date start, Date end );

	/**
	 * Will give a list of all the diffrent StockNames
	 * @return A list of stockNames
	 */
	public List<StockNames> getAllStockNames();
	/**
	 * Updates an object to the database
	 * @param o The object to be updated.
	 * @return True if it went ok.
	 */
	public boolean updateObject(Object o);
	/**
	 * Store 1 object in the database.
	 * @param o Object to be stored
	 * @return True if it went ok
	 */
	public boolean storeObject(Object o);
	public boolean storeObjectIfPossible(Object o);
	/**
	 * Stores a list of objects to the database
	 * @param list List of objects
	 * @return True if it went ok
	 */
	public boolean storeListOfObjects(List list);
	/**
	 * A special case of storeListOfObjects, this will store the list but ignore duplicates.
	 * @param list List of objects
	 * @return the number of objects not stored.
	 */
	public int storeListOfObjectsDuplicates(List list);
	/**
	 * Invests money in a given portfolio
	 * @param amount The amount of money to invest
	 * @param portfolio The portfolio to invest to
	 * @return Returns true if everything went ok
	 */
	public boolean investMoney(long amount, PortfolioEntitys portfolio);
	/**
	 * Deletes an object in the database.
	 * @param objectToBeRemoved The object to be removed.
	 */
	public void remove(Object objectToBeRemoved);
	/**
	 * Gets the stockNames this portfolio is set to watch.
	 * @param portfolioTable The portfolio
	 * @return A list of stockNames
	 */
	public List<StockNames> getStockNames(PortfolioEntitys portfolioTable);
	/**
	 * Returns a list of currently owned stocks.
	 * @param portfolioTable The portfolio.
	 * @return A List of currently owned stocks.
	 */
	public List<StockPrices> getCurrentStocks(PortfolioEntitys portfolioTable);
	/**
	 * Returns a list of pairs with old stocks, left is the stockpoint when it was bought
	 * the right one is the stockpoint of when it was sold
	 * @param portfolioTable
	 * @return
	 */
	public List<Pair<StockPrices, StockPrices>> getOldStocks(
			PortfolioEntitys portfolioTable);
	/**
	 * Given a StockPrice will return the latest stockprice with the same name.
	 * @param from The old stockPrice
	 * @return The latest stockPrice with same name as given stockPrice
	 */
	public StockPrices getLatestStockPrice(StockPrices from);
	/**
	 * Returns the total amount invested in this portfolio
	 * @param portfolioTable The portfolio to be audited.
	 * @return The total amount invested
	 */
	public long getTotalInvestedAmount(PortfolioEntitys portfolioTable);
	/**
	 * Gives the AlgorithmTable for a given portfolio
	 * @param portfolioTable The portfolioTable to get the AlgorithmTable from
	 * @return An algorithmTable
	 */
	public AlgorithmEntitys getAlgorithmTable(PortfolioEntitys portfolioTable);

	/**
	 * Given a stockPrice and a portfolio, will find the PortfolioHistory that has the StockPrice as buying price, 
	 * and coupled with the same portfolio.
	 * 
	 * This method will be used for example in the TraderSimulator to get the right PortfolioHistory.
	 * 
	 * @param stockPrice The buying stockPrice
	 * @param portfolio The portfolio
	 * @param amount 
	 * @return A PortfolioHistory.
	 */
	public PortfolioHistory getSpecificPortfolioHistory(StockPrices stockPrice, PortfolioEntitys portfolio, long amount);
	/**
	 * Returns a list of Stockprices with same name as a given StockPrice, with max size of a given value.
	 * @param from The StockPrice that has the same name 
	 * @param n How many max results
	 * @return A list of stockPrices
	 */
	public List<StockPrices> getNLatest(StockPrices from, int n);


	public EntityManager getEntityManager();
	
	public List<PortfolioHistory> getPortfolioHistory(PortfolioEntitys portfolio);
	public List<PortfolioHistory> getPortfolioHistory(StockPrices sp,
			PortfolioEntitys portfolioTable);
}
