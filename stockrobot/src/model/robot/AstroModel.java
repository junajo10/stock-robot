package model.robot;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.UnknownHostException;

import utils.global.Log;

import model.IModel;
import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.portfolio.IPortfolioHandler;
import model.portfolio.IRobot_Algorithms;
import model.portfolio.PortfolioHandler;
import model.trader.ITrader;
import model.trader.TraderSimulator;

/**
 * The main model for astro, this will start everything necessary to run ASTRo, 
 * including robotScheduler which is in charge of running algorithms.
 * 
 * @author Daniel
 */
public class AstroModel implements IRobot_Algorithms, IModel{
	private IJPAHelper jpaHelper = JPAHelper.getInstance();
	private ITrader trader = TraderSimulator.getInstance();
	
	private IPortfolioHandler portfolioHandler;
	
	private RobotScheduler robotScheduler;
	private PropertyChangeSupport observers = new PropertyChangeSupport(this);
	
	/**
	 * This starts robotScheduler without connection to the parser,
	 * which means that it will run algortihms at fixed intervals.
	 */
	public AstroModel() {
		jpaHelper = JPAHelper.getInstance();
		portfolioHandler = PortfolioHandler.getInstance(this);
		robotScheduler = new RobotScheduler(portfolioHandler);
		Log.instance().addObservers(observers);
	}
	/**
	 * This starts robotScheduler with a connection to the parser,
	 * so each time the parser outputs on to the socket will trigger the algorithms
	 * @param host Host of the parser
	 * @param port Port of the parser
	 * @throws UnknownHostException If the parser cant be reached UnknownHostException will be thrown
	 */
	public AstroModel(String host, int port) throws Exception {
		jpaHelper = JPAHelper.getInstance();
		portfolioHandler = PortfolioHandler.getInstance(this);
		robotScheduler = new RobotScheduler(portfolioHandler, host, port);
		Log.instance().addObservers(observers);
	}
	@Override
	public IJPAHelper getJPAHelper() {
		return jpaHelper;
	}

	@Override
	public ITrader getTrader() {
		return trader;
	}
	
	public void cleanup() {
		robotScheduler.doWork();
		robotScheduler.cleanup();
		
		robotScheduler = null;
		portfolioHandler = null;
	}

	public void startScheduler() {
		Thread t = new Thread(robotScheduler);
		t.start();
	}
	@Override
	public void addObserver(PropertyChangeListener listener) {
			observers.addPropertyChangeListener(listener);
	}
	@Override
	public void removeObserver(PropertyChangeListener listener) {
		observers.removePropertyChangeListener(listener);
	}
}
