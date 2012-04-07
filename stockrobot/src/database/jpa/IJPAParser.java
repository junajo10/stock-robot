package database.jpa;

import java.util.List;
import java.util.Map;

import database.jpa.tables.StockPrices;

import scraping.model.ParserStock;

/**
 * The interface the parser will use.
 * 
 * @author Daniel
 */
public interface IJPAParser {
	/**
	 * Inserts stocks in to the database.
	 * @param stocks The stocks to be added to the database.
	 * @return returns the number of stocks that was added to the database
	 */
	int addStocks(List<ParserStock> stocks);
	
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
	public boolean storeListOfObjects(List list);
	/**
	 * A special case of storeListOfObjects, this will store the list but ignore duplicates.
	 * @param list List of objects
	 * @return the number of objects not stored.
	 */
	public int storeListOfObjectsDuplicates(List list);
}
