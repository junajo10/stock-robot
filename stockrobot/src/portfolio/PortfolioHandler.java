package portfolio;

import java.util.ArrayList;
import java.util.List;

import database.jpa.JPAHelper;
import database.jpa.tables.PortfolioTable;
/**
 * @author Daniel
 *
 * Will manage all portfolios.
 * It will start by creating all the portfolio objects.
 */
public class PortfolioHandler implements IPortfolioHandler{

	private static PortfolioHandler instance = null;
	List<IPortfolio> listOfPortfolios = new ArrayList<IPortfolio>();
	JPAHelper jpaHelper;
	
	private PortfolioHandler() {
		jpaHelper = JPAHelper.getInstance();
		jpaHelper.initJPASystem();
		
		List<PortfolioTable> portfolioTables = JPAHelper.getInstance().getAllPortfolios();
		
		for (PortfolioTable pt : portfolioTables) {
			listOfPortfolios.add(new Portfolio(pt));
		}
		
	}
	@Override
	public IPortfolio createNewPortfolio(String name) {
		PortfolioTable pt = new PortfolioTable(name);
		jpaHelper.storeObject(pt);
		
		return new Portfolio(pt);
	}

	@Override
	public List<IPortfolio> getPortfolios() {
		return listOfPortfolios;
	}

	@Override
	public boolean removePortfolio(IPortfolio portfolio) {
		if (portfolio.getPortfolioTable().getBalance() == 0) {
			jpaHelper.remove(portfolio.getPortfolioTable());
		}
		return false;
	}
	public static IPortfolioHandler getInstance() {
		if(instance == null) {
			instance = new PortfolioHandler();
		}
		return instance;
	}
	@Override
	public boolean setupPortfolio(IPortfolio portfolio) {
		PortfolioSetupGUI setupGUI = new PortfolioSetupGUI(null, portfolio);
		
		return false;
	}
}
