package portfolio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import model.database.jpa.IJPAHelper;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;
import model.portfolio.IPortfolio;
import model.portfolio.IPortfolioHandler;
import model.portfolio.IRobot_Algorithms;
import model.portfolio.PortfolioHandler;
import model.trader.ITrader;
import model.trader.TraderSimulator;
import testhelpers.DatabaseCleaner;

public class PortfolioTest extends DatabaseCleaner implements IRobot_Algorithms{

	IPortfolioHandler portfolioHandler;
	String portfolioName;
	
	IPortfolio portfolio;
	@Before
	public void setup() {
		portfolioHandler = PortfolioHandler.getInstance(this);
		portfolioName = "TestPortfolio";
		portfolio = portfolioHandler.createNewPortfolio(portfolioName);
	}
	
	@Test
	public void testPortfolio() {
		StockNames sn = new StockNames("StockA", "MarketA", true);
		jpaHelper.storeObject(sn);
		
		StockPrices stock = new StockPrices(sn, 1, 1, 1, 1, new Date(1231233));
		jpaHelper.storeObject(stock);
		
		portfolio.investAmount(10000);
		
		Assert.assertEquals(10000, portfolio.getUnusedAmount());
		
		TraderSimulator ts = TraderSimulator.getInstance();
		
		ts.buyStock(stock, 1, portfolio.getPortfolioTable());
		
		Assert.assertTrue(10000 != portfolio.getUnusedAmount());
		
		Assert.assertTrue(portfolio.getCurrentStocks().size() == 1);
		
		Assert.assertTrue(portfolio.getAvalibleStocks().size()>0);
		
		Assert.assertTrue(portfolio.getName().contentEquals(portfolioName));
		
		Assert.assertNotSame(portfolio.getInvestedAmount(), portfolio.getUnusedAmount());
		
		Assert.assertTrue(portfolio.getHistoryStocks().size() == 0);
		
		List<PortfolioHistory> portfolioHistory = new ArrayList<PortfolioHistory>();
		portfolioHistory.addAll(portfolio.getPortfolioTable().getHistory());

		ts.sellStock(portfolioHistory.get(0), portfolio.getPortfolioTable());
		Assert.assertTrue(portfolio.getHistoryStocks().size() == 1);
		
	}
	@Override
	public IJPAHelper getJPAHelper() {
		return jpaHelper;
	}

	@Override
	public ITrader getTrader() { //NOPMD
		
		return null;
	}
}