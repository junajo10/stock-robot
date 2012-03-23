package database.jpa;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import portfolio.Portfolio;

import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import database.jpa.tables.StocksToWatch;


public class JPAHelperTest {
	static JPAHelper jpaHelper;
	static Random rand = new Random(System.currentTimeMillis());
	@BeforeClass
	public static void beforeClass(){ //First of all
		jpaHelper = new JPAHelper("testdb");
	}
	@Test(expected=Exception.class)
	public void testDuplicateEntry() {
		StockPrices sp = new StockPrices(new StockNames("Stock1", "marketA"), 100, 100, 100, 100, new Date(System.currentTimeMillis()));
		jpaHelper.storeObject(sp);
		jpaHelper.storeObject(sp);
	}
	@Test
	public void testDuplicateSafeEntry() {
		StockNames stockName = new StockNames("Stock2"+rand.nextFloat(), "marketB");
		StockPrices sp = new StockPrices(stockName, 100, 100, 100, 100, new Date(123231));
		System.out.println(sp);
		jpaHelper.storeObjectIfPossible(stockName);
		jpaHelper.storeObjectIfPossible(sp);
		jpaHelper.storeObjectIfPossible(sp);
	}
	@Test
	public void testNewPortfolioAndDelete() {
		PortfolioEntitys testPortfolio = new PortfolioEntitys("testPortfolio");
		jpaHelper.storeObject(testPortfolio);
		
		jpaHelper.remove(testPortfolio);
		for (PortfolioEntitys p : jpaHelper.getAllPortfolios()) {
			if (p.getName().contentEquals("testPortfolio"))
				throw new IllegalArgumentException("Still in the system");
		}
	}
	@AfterClass
	public static void afterClass() {
		// TODO: PortfolioInvestment
		// TODO: StocksToWatch
		
		for (PortfolioEntitys p : jpaHelper.getAllPortfolios()) {
			for (PortfolioHistory ph : p.getHistory()) {
	    		jpaHelper.remove(ph);
	    	}
	    	jpaHelper.remove(p);
	    }
	    for (AlgorithmEntitys a : jpaHelper.getAllAlgorithms())
			jpaHelper.remove(a);
		
	    
	    for (StockPrices sp : jpaHelper.getAllStockPrices())
	    	jpaHelper.remove(sp);
		for (StockNames sn : jpaHelper.getAllStockNames())
	    	jpaHelper.remove(sn);
	}
}