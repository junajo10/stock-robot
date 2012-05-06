package model.robot;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.portfolio.IPortfolioHandler;
import model.portfolio.PortfolioHandler;
import model.trader.ITrader;
import model.trader.TraderSimulator;

/**
 * The main model for astro, this will start everything necessary.
 * 
 * @author Daniel
 */
public class AstroModel implements IRobot_Algorithms{
	IJPAHelper jpaHelper = JPAHelper.getInstance();
	ITrader trader = TraderSimulator.getInstance();
	
	IPortfolioHandler portfolioHandler = PortfolioHandler.getInstance(this);
	
	RobotScheduler robotScheduler = new RobotScheduler(portfolioHandler);
	
	@Override
	public IJPAHelper getJPAHelper() {
		return jpaHelper;
	}

	@Override
	public ITrader getTrader() {
		return trader;
	}
	
	public void cleanup() {
		jpaHelper.close();
	}

}
