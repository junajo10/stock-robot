package database.jpa;


import java.util.Date;
import java.util.Random;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import database.jpa.tables.StocksToWatch;


public class JPATest {
	static IJPAHelper jpaHelper;
	static Random rand = new Random(System.currentTimeMillis());
	@BeforeClass
	public static void beforeClass(){ //First of all
		jpaHelper = JPAHelper.getInstance("testdb");
	}
	@Test(expected=Exception.class)
	public void testDuplicateEntry() {
		StockPrices sp = new StockPrices(new StockNames("Stock1", "marketA"), 100, 100, 100, 100, new Date(System.currentTimeMillis()));
		jpaHelper.storeObject(sp);
		jpaHelper.storeObject(sp);
	}
	@Test
	public void testDuplicateSafeEntry() {
		StockNames stockName = new StockNames("Stock2" + rand.nextFloat(), "marketB");
		StockPrices sp = new StockPrices(stockName, 100, 100, 100, 100, new Date(123231));
		System.out.println(sp);
		jpaHelper.storeObjectIfPossible(stockName);
		jpaHelper.storeObjectIfPossible(sp);
		jpaHelper.storeObjectIfPossible(sp);
	}
	@Test
	public void testNewPortfolio() {
		AlgorithmEntitys algorithm = new AlgorithmEntitys("AlgorithmName", "path");
		PortfolioEntitys portfolio = new PortfolioEntitys("Portfolio");
		portfolio.setAlgorithm(algorithm);
		jpaHelper.storeObject(algorithm);
		jpaHelper.storeObject(portfolio);		
	}
	@Test
	public void testNewPortfolioAndDelete() {
		PortfolioEntitys testPortfolio = new PortfolioEntitys("testPortfolio");
		jpaHelper.storeObject(testPortfolio);
		
		jpaHelper.remove(testPortfolio);
		
		for (PortfolioEntitys p : jpaHelper.getAllPortfolios()) {
			System.out.println(jpaHelper.getAllPortfolios().size());
			if (p.getName().contentEquals("testPortfolio"))
				throw new IllegalArgumentException("Still in the system");
		}
	}
	@Test
	public void testStocksToWatch() {
		PortfolioEntitys p = new PortfolioEntitys("StockToWatchTest");
		jpaHelper.storeObject(p);
		StocksToWatch stw = new StocksToWatch(p, new StockNames("stockbeeingwatched", "testMarket"));
		jpaHelper.storeObject(stw);
	}
	
	@Test
	public void testPortfolioInvestment() {
		long amountToInvest = 10000;
		PortfolioEntitys p = new PortfolioEntitys("portfolioInvestment");
		jpaHelper.storeObject(p);
		PortfolioInvestment investment = new PortfolioInvestment(p, amountToInvest, true);
		jpaHelper.storeObject(investment);
		
		Assert.assertEquals(p.getBalance(), amountToInvest);
	}
	
	/**
	 * Removes all entitys from the database
	 */
	@AfterClass
	public static void afterClass() {
		for (PortfolioInvestment investment : jpaHelper.getAllPortfolioInvestment()) {
			System.out.println(investment);
			jpaHelper.remove(investment);
		}
		
		for (StocksToWatch stw : jpaHelper.getAllStocksToWatch()) {
			System.out.println(stw);
			jpaHelper.remove(stw);
		}
		
		while (jpaHelper.getAllPortfolios().size() > 0) {
			PortfolioEntitys p = jpaHelper.getAllPortfolios().get(0);
			if (p.getHistory() != null) {
				if (p.getHistory().iterator().hasNext()) {
					jpaHelper.remove(p.getHistory().iterator().next());
				}
			}
			
			jpaHelper.remove(p);
		}
	    for (AlgorithmEntitys a : jpaHelper.getAllAlgorithms()) {
			jpaHelper.remove(a);
	    }
		
	    
	    for (StockPrices sp : jpaHelper.getAllStockPrices()) {
	    	System.out.println(sp);
	    	jpaHelper.remove(sp);
	    }
		for (StockNames sn : jpaHelper.getAllStockNames()) {
			System.out.println(sn);
	    	jpaHelper.remove(sn);
		}
	}
}