package robot;

import static org.junit.Assert.*;

import generic.FinancialLongConverter;
import generic.Log;
import generic.Pair;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import algorithms.IAlgorithm;
import algorithms.loader.AlgorithmsLoader;
import database.jpa.JPAHelper;
import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;

import portfolio.IPortfolio;
import portfolio.IPortfolioHandler;
import portfolio.Portfolio;
import portfolio.PortfolioHandler;
import portfolio.PortfolioSetupGUI;
import trader.ITrader;
import trader.TraderSimulator;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: RobotSchedulerTest.java
 * Description:
 * RobotSchedulerTest runs different tests on RobotScheduler to verify that it's
 * Functioning correctly and new modifications doesn't break the class.
 */
public class RobotSchedulerTest implements IRobot_Algorithms{

	/**
	 * Test the regular start, stop, pause and unpause for
	 * empty scheduler.
	 */
	@Test
	public void emptyRunTest() {
				
		Log.instance().setFilter(Log.TAG.DEBUG, true);
		Log.instance().setFilter(Log.TAG.VERY_VERBOSE, true);
		
		Log.instance().log(Log.TAG.NORMAL, "Starting emptyRunTest");
		
		TestPortfolioHandler pHandler = new TestPortfolioHandler(null);
		RobotScheduler schedueler = new RobotScheduler(pHandler);
		schedueler.setUpdateFrequency(RobotScheduler.MILLI_SECOND*10);
		schedueler.setPauseLength(RobotScheduler.MILLI_SECOND);
		Thread tSched = new Thread(schedueler);
		assertFalse(schedueler.stop());
		assertFalse(schedueler.pause());
		assertFalse(schedueler.unpause());
		tSched.start();
		try {
			Thread.sleep(RobotScheduler.MILLI_SECOND*50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		schedueler.stop();
		try {
			Thread.sleep(RobotScheduler.MILLI_SECOND*50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertFalse(tSched.isAlive());
	}
	
	/**
	 * Test the regular start, stop, pause and unpause for
	 * scheduler running one algorithm.
	 */
	@Test
	public void singleRunTest() {
				
		Log.instance().setFilter(Log.TAG.DEBUG, true);
		Log.instance().setFilter(Log.TAG.VERY_VERBOSE, true);
		
		Log.instance().log(Log.TAG.NORMAL, "Starting singleRunTest");
		
		JUnitAlgorithm algorithm = new JUnitAlgorithm(RobotScheduler.MILLI_SECOND);
		IPortfolio testPortfolio = new TestPortfolio(1,"test1",algorithm);
		
		List<IPortfolio> portfolios = new LinkedList<IPortfolio>();
		portfolios.add(testPortfolio);
		
		TestPortfolioHandler pHandler = new TestPortfolioHandler(portfolios);
		RobotScheduler schedueler = new RobotScheduler(pHandler);
		schedueler.setUpdateFrequency(RobotScheduler.MILLI_SECOND*10);
		schedueler.setPauseLength(RobotScheduler.MILLI_SECOND);
		Thread tSched = new Thread(schedueler);
		tSched.start();
		try {
			Thread.sleep(RobotScheduler.MILLI_SECOND*50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue(algorithm.getUpdatedNrTimes() > 2 && algorithm.getUpdatedNrTimes() < 8);
		schedueler.stop();
		try {
			Thread.sleep(RobotScheduler.MILLI_SECOND*50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertFalse(tSched.isAlive());
	}


	@Override
	public boolean reportToUser(String message) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Scaled of portfolio 
	 */
	private class TestPortfolio implements IPortfolio{

		private int portfolioId;
		private IAlgorithm algorithm;
		private String name;
		
		public TestPortfolio(int portfolioId, String name, IAlgorithm algorithm){
			this.algorithm = algorithm;
			this.name = name;
			this.portfolioId = portfolioId;
		}
		
		@Override
		public List<StockNames> getAvalibleStocks() {
			return null;
		}

		@Override
		public List<StockPrices> getCurrentStocks() {
			return null;
		}

		@Override
		public List<Pair<StockPrices, StockPrices>> getHistoryStocks() {
			return null;
		}

		@Override
		public IAlgorithm getAlgorithm() {
			return algorithm;
		}

		@Override
		public boolean setAlgorithm(AlgorithmEntitys algorithm) {
			
			return false;
		}
		
		public boolean setAlgorithm(IAlgorithm algorithm) {
			this.algorithm = algorithm;
			
			return true;
		}

		@Override
		public long getInvestedAmount() {
			return 0;
		}

		@Override
		public long getUnusedAmount() {
			return 0;
		}

		@Override
		public boolean investAmount(long n) {
			return false;
		}

		@Override
		public boolean setStocksToWatch(List<StockNames> stocks) {
			return false;
		}

		@Override
		public void stopBuying(boolean flag) {
			
		}

		@Override
		public void stopSelling(boolean flag) {
		
		}

		@Override
		public boolean isStopBuyingFlagSet() {
			return false;
		}

		@Override
		public boolean isStopSellingFlagSet() {
			return false;
		}
		@Override
		public String getName() {
			return name;
		}

		@Override
		public int getPortfolioId() {
			return portfolioId;
		}
		
		public String toString() {
			return "TestPortfolio " + name + "\n";
		}
		@Override
		public PortfolioEntitys getPortfolioTable() {
			return null;
		}
		@Override
		public AlgorithmEntitys getAlgorithmTable() {
			return null;
		}
	}
	

	private class TestPortfolioHandler implements IPortfolioHandler{

		private List<IPortfolio> listOfPortfolios = new ArrayList<IPortfolio>();
		
		public TestPortfolioHandler(List<IPortfolio> portfolios) {
					
			if(portfolios != null)
				listOfPortfolios.addAll(portfolios);
		}
		@Override
		public IPortfolio createNewPortfolio(String name) {
			PortfolioEntitys pt = new PortfolioEntitys(name);
			return new Portfolio(pt);
		}

		@Override
		public List<IPortfolio> getPortfolios() {
			return listOfPortfolios;
		}

		@Override
		public boolean removePortfolio(IPortfolio portfolio) {
		
			return false;
		}
	
		@Override
		public boolean setupPortfolio(IPortfolio portfolio) {
			
			return false;
		}
		@Override
		public void addAddObserver(PropertyChangeListener listener) {
		}
		@Override
		public void removeObserver(PropertyChangeListener listener) {
		}
	}
}
