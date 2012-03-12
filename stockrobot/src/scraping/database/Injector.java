package scraping.database;

public class Injector {
	
	/**
	 * Given a stock name and a price;
	 * 
	 * +If a table with the same name as name exists
	 * 		=> Insert current date and price
	 * +Otherwise
	 * 		=> Create a table named name and insert current date and price
	 * 
	 * @param name
	 * @param price
	 */
	public static void insertStockPrice( String name, float price ) {
		
		createStockTable( name );
	}
	
	/**
	 * Create a table with the following specification
	 * 
	 * [ name : Varchar(50) | date : Date | price : Float ]
	 * 
	 * @param name
	 */
	private static void createStockTable( String name ) {
		
		
	}
	
	/**
	 * Updates the market associated to a stock
	 * 
	 * +If a row with the same name as name exists
	 * 		=> Update the market
	 * +Otherwise
	 * 		=> Insert a new row with name and market
	 * 
	 * @param name
	 * @param market
	 */
	public static void updateMarket( String name, String market ) {
		
		
	}
}