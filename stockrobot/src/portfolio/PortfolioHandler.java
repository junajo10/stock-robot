package portfolio;

import gui.IObservable;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import database.jpa.IJPAHelper;
import database.jpa.JPAHelper;
import database.jpa.tables.PortfolioEntity;
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
	private IJPAHelper jpaHelper;
	private PropertyChangeSupport propertyChangeSuport = new PropertyChangeSupport(this);
	
	private PortfolioHandler() {
		jpaHelper = JPAHelper.getInstance();
		
		List<PortfolioEntity> portfolioTables = jpaHelper.getAllPortfolios();
		
		System.out.println(portfolioTables);
		for (PortfolioEntity pt : portfolioTables) {
			listOfPortfolios.add(new Portfolio(pt));
		}
		
	}
	@Override
	public IPortfolio createNewPortfolio(String name) {
		PortfolioEntity pt = new PortfolioEntity(name);
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
			synchronized (PortfolioHandler.class) {
				if (instance == null)
					instance = new PortfolioHandler();
			}
		}
		return instance;
	}
	@Override
	public void addAddObserver(PropertyChangeListener listener) {
		propertyChangeSuport.addPropertyChangeListener(listener);
	}
	@Override
	public void removeObserver(PropertyChangeListener listener) {
		propertyChangeSuport.addPropertyChangeListener(listener);
	}
}
