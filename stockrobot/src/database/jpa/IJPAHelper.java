package database.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import database.jpa.tables.AlgorithmEntity;
import database.jpa.tables.AlgorithmSetting;
import database.jpa.tables.AlgorithmSettings;
import database.jpa.tables.PortfolioEntity;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.StockPrices;
import database.jpa.tables.StocksToWatch;

/**
 * @author Daniel
 *
 * This is the main interface to the JPAHelper system,
 * it is implemented by JPAHelper and JPAHelperSimulator
 */
public interface IJPAHelper extends IJPAAlgortihm, IJPAParser{
	public void stopJPASystem();
	/**
	 * Returns a list of all the algorithms.
	 * @return a list of all the algorithms.
	 */
	public List<AlgorithmEntity> getAllAlgorithms();
	/**
	 * Will give back all portfolios in the JPA system.
	 * @return A list with PortfolioTables
	 */
	public List<PortfolioEntity> getAllPortfolios();
	/**
	 * Will give back all stockPrices
	 * @return A list of stockPrices
	 */
	public List<StockPrices> getAllStockPrices();
	/**
	 * Will give back all stockPrices
	 * @param limit 
	 * @return A list of stockPrices
	 */
	public List<StockPrices> getStockPricesReverseOrdered(int limit);
	/**
	 * Will give back all PortfolioInvestment
	 * @return A list of PortfolioInvestment
	 */
	//public List<PortfolioInvestment> getAllPortfolioInvestment();
	/**
	 * Will give back all StocksToWatch
	 * @return A list of StocksToWatch
	 */
	public List<StocksToWatch> getAllStocksToWatch();
	
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
	public boolean investMoney(long amount, PortfolioEntity portfolio);
	/**
	 * Deletes an object in the database.
	 * @param objectToBeRemoved The object to be removed.
	 */
	public void remove(Object objectToBeRemoved);


	/**
	 * Gives the AlgorithmTable for a given portfolio
	 * @param portfolioTable The portfolioTable to get the AlgorithmTable from
	 * @return An algorithmTable
	 */
	public AlgorithmEntity getAlgorithmTable(PortfolioEntity portfolioTable);

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
	//public PortfolioHistory getSpecificPortfolioHistory(StockPrices stockPrice, PortfolioEntity portfolio, long amount);



	public EntityManager getEntityManager();
	
	
	
	public List<AlgorithmSetting> getSettings(AlgorithmSettings settings);
	
	public List<StockPrices> getLatestStockPrices();
}
