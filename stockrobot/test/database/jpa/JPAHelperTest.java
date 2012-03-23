package database.jpa;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;


public class JPAHelperTest {
	static JPAHelper jpaHelper;
	@BeforeClass
	public static void beforeClass(){ //First of all
		jpaHelper = new JPAHelper("testdb");
	}
	@Test(expected=Exception.class)
	public void testDuplicateEntry() {
		StockPrices sp = new StockPrices(new StockNames("name", "market"), 100, 100, 100, 100, new Date(System.currentTimeMillis()));
		jpaHelper.storeObject(sp);
		jpaHelper.storeObject(sp);
	}
	@Test
	public void testDuplicateSafeEntry() {
		StockPrices sp = new StockPrices(new StockNames("sasdf", "maAet"), 100, 100, 100, 100, new Date(123231));
		jpaHelper.storeObjectIfPossible(sp);
		jpaHelper.storeObjectIfPossible(sp);
	}
	
	@Test
	public void test() {
		
	}

}