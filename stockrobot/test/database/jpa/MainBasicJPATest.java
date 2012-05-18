package database.jpa;

import java.util.Date;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;

import org.junit.Test;

import testhelpers.DatabaseCleaner;

/**
 * @author Daniel
 *
 * This is mainly a test class for learning JPA.
*/
public class MainBasicJPATest extends DatabaseCleaner {
	
	@Test 
	public void addNewPortfolios() {
		PortfolioEntity portfolio = new PortfolioEntity("portfolio 1");
		portfolio.setAlgorithm("TestAlgorithm1");
		jpaHelper.storeObject(portfolio);
		
		PortfolioEntity portfolio2 = new PortfolioEntity("portfolio 2");
		portfolio.setAlgorithm("TestAlgorithm2");
		jpaHelper.storeObject(portfolio2);
		
		Assert.assertEquals(2, jpaHelper.getAllPortfolios().size());
	}
	@Test
	public void mainTest() {
		
	//__ These two blocks of code are stolen from above, just to provide portfolios 
	//	 to test now that everything is wiped from the DB between tests
		PortfolioEntity portfolio = new PortfolioEntity("portfolio 1");
		portfolio.setAlgorithm("TestAlgorithm1");
		jpaHelper.storeObject(portfolio);
		
		PortfolioEntity portfolio2 = new PortfolioEntity("portfolio 2");
		portfolio.setAlgorithm("TestAlgorithm2");
		jpaHelper.storeObject(portfolio2);
	//
		
		for (PortfolioEntity p : jpaHelper.getAllPortfolios()) {
			jpaHelper.investMoney(10000000, p);
		}
		
		List<StockNames> stockNames = jpaHelper.getAllStockNames();
		
		if (stockNames.size() == 0) {
			StockNames stockName = new StockNames("Stock1", "MarketA", true);
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock2", "MarketB", true);
			jpaHelper.storeObject(stockName);
			
			
			stockName = new StockNames("Stock3", "MarketB", true);
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock4", "MarketA", true);
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock5", "MarketB", true);
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock6", "MarketB", true);
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock7", "MarketB", true);
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock8", "MarketB", true);
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock9", "MarketB", true);
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock10", "MarketB", true);
			jpaHelper.storeObject(stockName);
			
			stockNames = jpaHelper.getAllStockNames(); 
		}
		
		Random r = new Random(System.currentTimeMillis());
		
		// create one stockPrice for each stockName
		for (StockNames stockName : stockNames) {
			
			for (int i = 1; i <= 10; i++) {
				StockPrices sp = new StockPrices(stockName, r.nextInt(1000*i), r.nextInt(1000*i), r.nextInt(1000*i), r.nextInt(1000*i), new Date(System.currentTimeMillis()+1000*i));
				jpaHelper.storeObject(sp);
			}
		}
		
		StockPrices aStock = jpaHelper.getAllStockPrices().get(0);
		
		PortfolioEntity p = jpaHelper.getAllPortfolios().get(0);
		
		if (r.nextBoolean())
			p.addPortfolioHistory(new PortfolioHistory(aStock, new Date(System.currentTimeMillis()), 10, p));
		else
			p.addPortfolioHistory(new PortfolioHistory(aStock, new Date(System.currentTimeMillis()-10000), 10, p));
		jpaHelper.updateObject(p);
		
		Assert.assertTrue(jpaHelper.getAllStockPrices().size() > 0);

		
		Assert.assertTrue(jpaHelper.getCurrentStocks(jpaHelper.getAllPortfolios().get(0)).size() > 0);
		
		Assert.assertTrue(jpaHelper.getAllPortfolios().size() > 0);
		
		
		//-------- Test get latest StockPrice from any stockPrice
		StockPrices old = jpaHelper.getAllStockPrices().get(0);
		StockPrices newestPrice = jpaHelper.getLatestStockPrice(jpaHelper.getAllStockPrices().get(0));
		
		Assert.assertNotNull(old);
		Assert.assertNotNull(newestPrice);
		
		Assert.assertNotSame(old, newestPrice);
		//------
		
		
		StockPrices stock = new StockPrices(jpaHelper.getAllStockNames().get(0), 123, 123, 123, 123, new Date(1233000));
		jpaHelper.storeObject(stock);
		
		p.addPortfolioHistory(new PortfolioHistory(stock, new Date(123), 77, p));
		jpaHelper.updateObject(p);
		
		Assert.assertNotNull(p.getSpecificPortfolioHistory(stock, 77));
		
		Assert.assertTrue(jpaHelper.getCurrentStocks(jpaHelper.getAllPortfolios().get(0)).size() > 0);
	}
}