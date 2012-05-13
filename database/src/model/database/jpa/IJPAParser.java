package model.database.jpa;

import java.util.List;
import java.util.Map;

import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;


/**
 * The interface the parser will use.
 * 
 * @author Daniel
 */
public interface IJPAParser {
	/**
	 * Will give a list of all the different StockNames
	 * @return A list of stockNames
	 */
	public List<StockNames> getAllStockNames();
	
	/**
	 * Gets the latest map with the stockname as the key and the lastest price as value.
	 * @return a map with the stockname as the key and the lastest price as value.
	 */
	public Map<String, StockPrices> getLatestMap();
	
	/**
	 * Stores a list of objects to the database
	 * @param list List of objects
	 * @return True if it went ok
	 */
	@SuppressWarnings("rawtypes")
	public boolean storeListOfObjects(List list);
	/**
	 * A special case of storeListOfObjects, this will store the list but ignore duplicates.
	 * @param list List of objects
	 * @return the number of objects not stored.
	 */
	@SuppressWarnings("rawtypes")
	public int storeListOfObjectsDuplicates(List list);
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
	
	/**
	 * Stores an object in the database, if for some reason this wont work it will just rollback.
	 * @param o Object to store
	 * @return Returns true if store was done, false if database couldent store this
	 */
	public boolean storeObjectIfPossible(Object o);
}
