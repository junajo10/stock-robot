package database.jpa;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import scraping.model.ParserStock;


import database.jpa.tables.AlgorithmEntity;
import database.jpa.tables.PortfolioEntity;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import database.jpa.tables.StocksToWatch;


public class JPATest {
	static IJPAHelper jpaHelper;
	static Random rand = new Random(System.currentTimeMillis());
	@BeforeClass
	public static void beforeClass(){ //First of all
		jpaHelper = new JPAHelperSimulator();
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
		AlgorithmEntity algorithm = new AlgorithmEntity("AlgorithmName", "path");
		PortfolioEntity portfolio = new PortfolioEntity("Portfolio");
		portfolio.setAlgorithm(algorithm);
		jpaHelper.storeObject(algorithm);
		jpaHelper.storeObject(portfolio);		
	}
	@Test
	public void testNewPortfolioAndDelete() {
		PortfolioEntity testPortfolio = new PortfolioEntity("testPortfolio");
		jpaHelper.storeObject(testPortfolio);
		
		jpaHelper.remove(testPortfolio);
		
		for (PortfolioEntity p : jpaHelper.getAllPortfolios()) {
			System.out.println(jpaHelper.getAllPortfolios().size());
			if (p.getName().contentEquals("testPortfolio"))
				throw new IllegalArgumentException("Still in the system");
		}
	}
	@Test
	public void testStocksToWatch() {
		PortfolioEntity p = new PortfolioEntity("StockToWatchTest");
		jpaHelper.storeObject(p);
		StocksToWatch stw = new StocksToWatch(p, new StockNames("stockbeeingwatched", "testMarket"));
		jpaHelper.storeObject(stw);
	}
	
	@Test
	public void testAddStocks() {
		List<ParserStock> list = new ArrayList<ParserStock>(); 
		
		for (int i = 0; i < 100; i++) {
			ParserStock ps = new ParserStock("ParserStock" + i);
			ps.setDate(new Date(1231231));
			ps.setBuy(123);
			ps.setSell(123);
			ps.setLastClose(321);
			ps.setMarket("Market" + i%5);
			ps.setVolume(1233);
			
			list.add(ps);
		}
		// Should add 100 new stocks
		Assert.assertEquals(100, jpaHelper.addStocks(list));
		
		// Should not be able to add any new stocks
		Assert.assertEquals(0, jpaHelper.addStocks(list));
		
		list.clear();
		for (int i = 0; i < 100; i++) {
			ParserStock ps = new ParserStock("ParserStock" + i);
			ps.setDate(new Date(new Long("1231231231233")));
			ps.setBuy(123);
			ps.setSell(123);
			ps.setLastClose(321);
			ps.setMarket("Market" + i%5);
			ps.setVolume(1233);
			
			list.add(ps);
		}
		// Should add 100 new stocks
		Assert.assertEquals(100, jpaHelper.addStocks(list));
		
		// Should not be able to add any new stocks
		Assert.assertEquals(0, jpaHelper.addStocks(list));
		
		
		// Test if all the stocks in the list is in getLatestStockPrices
		for (ParserStock ps : list) {
			boolean found = false;
			for (StockPrices sp : jpaHelper.getLatestStockPrices()) {
				if (sp.getTime().equals(ps.getDate())) {
					if (sp.getStockName().getName().contentEquals(ps.getName())) {
						found = true;
						break;
					}
				}
			}
			if (!found) {
				Assert.fail();
			}
		}
		
		
		for (StockPrices sp : jpaHelper.getLatestStockPrices()) {
			//System.out.println(sp);
		}
	}
	
	@Test
	public void testPortfolioInvestment() {
		long amountToInvest = 10000;
		PortfolioEntity p = new PortfolioEntity("portfolioInvestment");
		jpaHelper.storeObject(p);
		PortfolioInvestment investment = new PortfolioInvestment(p, amountToInvest, true);
		jpaHelper.storeObject(investment);
	}
	
/**
junit.framework.AssertionFailedError: expected:<0> but was:<10000>
	at junit.framework.Assert.fail(Assert.java:47)
	at junit.framework.Assert.failNotEquals(Assert.java:283)
	at junit.framework.Assert.assertEquals(Assert.java:64)
	at junit.framework.Assert.assertEquals(Assert.java:130)
	at junit.framework.Assert.assertEquals(Assert.java:136)
	at database.jpa.JPATest.testPortfolioInvestment(JPATest.java:103)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:616)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
	at org.junit.runners.BlockJUnit4ClassRunner.runNotIgnored(BlockJUnit4ClassRunner.java:79)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:71)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:49)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:28)
	at org.junit.internal.runners.statements.RunAfters.evaluate(RunAfters.java:31)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:50)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:467)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:683)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:390)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:197)




 */
	
	/**
	 * Removes all entitys from the database
	 */
	@AfterClass
	public static void afterClass() {
		for (PortfolioInvestment investment : jpaHelper.getAllPortfolioInvestment()) {
			//System.out.println(investment);
			jpaHelper.remove(investment);
		}
		
		for (StocksToWatch stw : jpaHelper.getAllStocksToWatch()) {
			//System.out.println(stw);
			jpaHelper.remove(stw);
		}
		
		while (jpaHelper.getAllPortfolios().size() > 0) {
			PortfolioEntity p = jpaHelper.getAllPortfolios().get(0);
			if (p.getHistory() != null) {
				if (p.getHistory().iterator().hasNext()) {
					jpaHelper.remove(p.getHistory().iterator().next());
				}
			}
			
			jpaHelper.remove(p);
		}
	    for (AlgorithmEntity a : jpaHelper.getAllAlgorithms()) {
			jpaHelper.remove(a);
	    }
		
	    
	    for (StockPrices sp : jpaHelper.getAllStockPrices()) {
	    	//System.out.println(sp);
	    	jpaHelper.remove(sp);
	    }
		for (StockNames sn : jpaHelper.getAllStockNames()) {
			//System.out.println(sn);
	    	jpaHelper.remove(sn);
		}
	}
}