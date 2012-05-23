package model.database.jpa;

import java.util.Date;
import java.util.List;

import org.apache.openjpa.persistence.OpenJPAEntityManager;

import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;


/**
 * This is the main interface to the JPAHelper system,
 * it is implemented by JPAHelper and JPAHelperSimulator
 * 
 * @author Daniel
 */
public interface IJPAHelper extends IJPAAlgortihm, IJPAParser{
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
	 * Updates an object to the database
	 * @param o The object to be updated.
	 * @return True if it went ok.
	 */
	@Override
	public boolean updateObject(Object o);
	/**
	 * Store 1 object in the database.
	 * @param o Object to be stored
	 * @return True if it went ok
	 */
	@Override
	public boolean storeObject(Object o);
	
	/**
	 * Stores an object in the database, if for some reason this wont work it will just rollback.
	 * @param o Object to store
	 * @return Returns true if store was done, false if database couldent store this
	 */
	@Override
	public boolean storeObjectIfPossible(Object o);
	/**
	 * Stores a list of objects to the database
	 * @param list List of objects
	 * @return True if it went ok
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public boolean storeListOfObjects(List list);
	/**
	 * A special case of storeListOfObjects, this will store the list but ignore duplicates.
	 * @param list List of objects
	 * @return the number of objects not stored.
	 */
	@Override
	@SuppressWarnings("rawtypes")
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
	 * Gets the entitymanager.
	 * @return OpenJPAEntityManager
	 */
	public OpenJPAEntityManager getEntityManager();
	
	/**
	 * Gets the last stockprice for all stockNames
	 * @return A list of stockPrices
	 */
	public List<StockPrices> getLatestStockPrices();
	
	/**
	 * Gets a list of PortfolioHistory assosiated with a given PortfolioEntity
	 * @param portfolioTable The portfolio table we wish to get history from
	 * @return Returns a list of portfolioHistorys.
	 */
	@Override
	public List<PortfolioHistory> getCurrentStocksHistory(PortfolioEntity portfolioTable);
	
	/**
	 * Gets a stockPrice from the database.
	 * @param st The stockName to search for
	 * @param date The date to search for
	 * @return Returns a StockPrice, if none is found returns null
	 */
	public StockPrices getPricesForStock( StockNames st, Date date );
	
	/**
	 * Stops the JPA system
	 */
	public void stopJPASystem();
	
	/**
	 * Gets the last StockPrice up to a given date.
	 * @param stockName The stockName to search for
	 * @param date The maximum date this StockPrice can have
	 * @return A stockPrice, if none is found null is returned
	 */
	public StockPrices getLastStock(StockNames stockName, Date date);
	
	/**
	 * Gets the stockPrice with the maximum date
	 * @return A stockPrice
	 */
	public StockPrices getLastStockPrice();
	
	public StockNames getStockNames(String key);
}
