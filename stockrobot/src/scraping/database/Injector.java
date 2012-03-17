package scraping.database;

import parser.ParserStock;

public class Injector implements IInjector {
	
	public static void main( String[] args ) {
		
		System.out.println( "Injector!!!!" );
		
		Injector i = new Injector();
		
		ParserStock s1 = new ParserStock("STOCK1");
		ParserStock s2 = new ParserStock("STOCK2");
		ParserStock s3 = new ParserStock("STOCK3");
		
		ParserStock[] apa = { s1, s2, s3 };
		
		i.injectStockData( apa );
	}
	
	/**
	 * Receive 
	 */
	@Override
	public boolean injectStockData( ParserStock[] stocks ) {
		
		System.out.println( "injectStockData" );
		
		
		
		//First off, get a list of all currently registered stocks
		
		for( ParserStock s : stocks ) {
			
			System.out.println( "Injector: Price data Representation, Hoho!" );
		}
		
		return true;
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