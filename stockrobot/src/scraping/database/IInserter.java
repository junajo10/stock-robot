package scraping.database;

import scraping.model.ParserStock;

public interface IInserter {
	
	/**
	 * Given an array with stock data formatted according to PriceDataRepresentation
	 * try to inject this data to the price database
	 * 
	 * Returns true if everything was successful, and false if an error occurred. 
	 * 
	 * @param s
	 * @return
	 */
	public boolean injectStockData( ParserStock[] s );
	
	/**
	 * Given an array with stock data formatted according to PriceDataRepresentation
	 * try to update the stock market if it doesn't match the content of s
	 * 
	 * Returns true if everything was successful, and false if an error occurred. 
	 * 
	 * @param s
	 * @return
	 */
	public boolean updateAllMarkets( ParserStock[] s );
}