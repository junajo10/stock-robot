package portfolio;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelperSimulator;
import model.portfolio.IPortfolio;
import model.portfolio.IPortfolioHandler;
import model.portfolio.PortfolioHandler;
import model.robot.IRobot_Algorithms;
import model.trader.ITrader;

public class PortfolioHandlerTest implements IRobot_Algorithms {

	private static IPortfolioHandler portfolioHandler;
	
	@Test
	public void createAndRemovePortfolio() {
		portfolioHandler = PortfolioHandler.getInstance(this);
		
		String portfolioName = "Gunnar";
		portfolioHandler.createNewPortfolio(portfolioName);
		
		List<IPortfolio> portfolios = portfolioHandler.getPortfolios();
		IPortfolio createdPortfolio = null;
		boolean found = false;
		for (IPortfolio p : portfolios) {
			if (p.getName().contentEquals(portfolioName)) {
				createdPortfolio = p;
				found = true;
			}
		}
		
		Assert.assertTrue(found);
		
		Assert.assertTrue(portfolioHandler.removePortfolio(createdPortfolio));
		
		portfolios = portfolioHandler.getPortfolios();
		found = false;
		for (IPortfolio p : portfolios) {
			if (p.getName().contentEquals(portfolioName)) {
				found = true;
			}
		}
		Assert.assertFalse(found);
	}

	@Override
	public boolean reportToUser(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IJPAHelper getJPAHelper() {
		return new JPAHelperSimulator();
	}

	@Override
	public ITrader getTrader() {
		// TODO Auto-generated method stub
		return null;
	}
}
