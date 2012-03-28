package database.jpa;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;

/**
 * This class will contain unit tests, but is 
 * mostly for quickly testing how the JPAHelper class
 * works in different aspects.
 * 
 * @author kristian
 *
 */
public class JPAHelperForUnderstanding {
	
	/*
	@Test
	public void testGetPricesForStock() {
		
		JPAHelper jpaHelper = JPAHelper.getInstance();
		jpaHelper.initJPASystem();
		
		//Get a stock, one to check for 
		StockNames firstStock = jpaHelper.getAllStockNames().get(0);
		
		//Do we get something back from getPricesForStock?
		List<StockPrices> prices = jpaHelper.getPricesForStock( firstStock );
		
		for( StockPrices sp : prices ) {
		
			System.out.println( "Latest: " + sp.getLatest() + ", Date: " + sp.getTime() );
		}
	}
	*/
	
	public static void main( String[] args ) {
		
		JPAHelperForUnderstanding apa = new JPAHelperForUnderstanding();
		apa.testGetPricesForStockPeriod();
	}
	
	//@Test
	public void testGetPricesForStockPeriod() {
		
		IJPAHelper jpaHelper = JPAHelper.getInstance();
		
		//Get a stock, one to check for 
		StockNames firstStock = jpaHelper.getAllStockNames().get(0);
		
		Calendar startAt = new GregorianCalendar();
		startAt.set(2012, 02, 23, 15, 00, 10);
		
		System.out.println( "StartTime: " + startAt.getTime() );
		
		Calendar endAt = new GregorianCalendar();
		endAt.set(2012, 02, 23, 15, 00, 26);
		
		List<StockPrices> prices = jpaHelper.getPricesForStockPeriod( firstStock, startAt.getTime(), endAt.getTime() );
		
		System.out.println( "After prices!" );
		
		for( StockPrices sp : prices ) {
			
			System.out.println( "Latest: " + sp.getLatest() + ", Date: " + sp.getTime() + ", Time: " + sp.getTime() + ", Name: " + sp.getStockName() );
		}
	}
}