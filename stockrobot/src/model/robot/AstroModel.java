package model.robot;

import java.net.UnknownHostException;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;
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
public class AstroModel implements IRobot_Algorithms{
	IJPAHelper jpaHelper = JPAHelper.getInstance();
	ITrader trader = TraderSimulator.getInstance();
	
	IPortfolioHandler portfolioHandler;
	
	RobotScheduler robotScheduler;
	
	/**
	 * This starts robotScheduler without connection to the parser,
	 * which means that it will run algortihms at fixed intervals.
	 */
	public AstroModel() {
		jpaHelper = JPAHelper.getInstance();

		portfolioHandler = PortfolioHandler.getInstance(this);
		robotScheduler = new RobotScheduler(portfolioHandler);
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
		
		//TODO: Removed when portfolioWizard works, this is just added to create a default portfolio with some money
		synchronized (this) {
			if (jpaHelper.getAllPortfolios().size() == 0) {
				PortfolioEntity p = new PortfolioEntity("APA");
				p.setAlgorithm("TestAlgorithm1");
				p.invest(1231213212, true);
				jpaHelper.storeObject(p);
			}
		}
		
		
		portfolioHandler = PortfolioHandler.getInstance(this);
		robotScheduler = new RobotScheduler(portfolioHandler, host, port);
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
}
