package robot;

import static org.junit.Assert.*;

import generic.Log;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;
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

public class RobotSchedulerTest implements IRobot_Algorithms{

	@Test
	public void emptyRunTest() {
				
		Log.instance().setFilter(Log.TAG.VERY_VERBOSE, true);
		Log.instance().setFilter(Log.TAG.DEBUG, true);
		
		TestPortfolioHandler pHandler = new TestPortfolioHandler(null);
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
