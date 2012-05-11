package robot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import testhelpers.DatabaseCleaner;

import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;
import model.trader.TraderSimulator;

/**
 * 
 * @author Daniel
 */
public class TraderTest extends DatabaseCleaner{

	TraderSimulator trader = TraderSimulator.getInstance();
	
	@Test
	public void testBuying() {
		StockNames sn = new StockNames("TestStock", "Market");
		jpaHelper.storeObject(sn);
		
		PortfolioEntity portfolio = new PortfolioEntity("Portfolio");
		portfolio.invest(1000, true);
		jpaHelper.storeObject(portfolio);
		
		
		StockPrices stock = new StockPrices(sn, 1, 1, 500, 500, new Date(123333));
		jpaHelper.storeObject(stock);
		Assert.assertTrue(trader.buyStock(stock, 1, portfolio));
		
		Assert.assertFalse(trader.buyStock(stock, 2, portfolio));
	}
	@Test
	public void testSelling() {
		StockNames sn = new StockNames("TestStock2", "Market");
		jpaHelper.storeObject(sn);
		
		PortfolioEntity portfolio = new PortfolioEntity("Portfolio2");
		portfolio.invest(1000, true);
		jpaHelper.storeObject(portfolio);
		
		
		StockPrices stock = new StockPrices(sn, 1, 1, 500, 500, new Date(123333));
		jpaHelper.storeObject(stock);
		Assert.assertTrue(trader.buyStock(stock, 1, portfolio));
		
		List<PortfolioHistory> portfolioHistory = new ArrayList<PortfolioHistory>();
		portfolioHistory.addAll(portfolio.getHistory());
		
		Assert.assertTrue(trader.sellStock(portfolioHistory.get(0), portfolio));
		
		Assert.assertFalse(trader.sellStock(portfolioHistory.get(0), portfolio));
	}
}
