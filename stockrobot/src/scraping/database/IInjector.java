package scraping.database;

public interface IInjector {
	
	/**
	 * Given an array with stock data formatted according to PriceDataRepresentation
	 * try to inject this data to the price database
	 * 
	 * Returns true if everything was successful, and false if an error occurred. 
	 * 
	 * @param s
	 * @return
	 */
	public boolean injectStockData( PriceDataRepresentation[] s );
}