package portfolio;

import java.util.ArrayList;
import java.util.List;

import database.jpa.JPAHelper;
import database.jpa.tables.PortfolioEntitys;
/**
 * @author Daniel
 *
 * Will manage all portfolios.
 * It will start by creating all the portfolio objects.
 * Each portfolio will then create a algorithm instance.
 */
public class PortfolioHandler implements IPortfolioHandler{

	private static PortfolioHandler instance = null;
	private List<IPortfolio> listOfPortfolios = new ArrayList<IPortfolio>();
	private JPAHelper jpaHelper;
	
	private PortfolioHandler() {
		jpaHelper = JPAHelper.getInstance();
		jpaHelper.initJPASystem();
		
		List<PortfolioEntitys> portfolioTables = JPAHelper.getInstance().getAllPortfolios();
		
		for (PortfolioEntitys pt : portfolioTables) {
			listOfPortfolios.add(new Portfolio(pt));
		}
		
	}
	@Override
	public IPortfolio createNewPortfolio(String name) {
		PortfolioEntitys pt = new PortfolioEntitys(name);
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
		PortfolioSetupGUI setupGUI = new PortfolioSetupGUI(portfolio);
		
		return false;
	}
}
