package portfolio;

import generic.Log;
import generic.Log.TAG;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import robot.IRobot_Algorithms;

import algorithms.IAlgorithm;
import algorithms.loader.PluginAlgortihmLoader;

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
	private PluginAlgortihmLoader algorithmLoader = PluginAlgortihmLoader.getInstance();
	private IRobot_Algorithms robot;
	
	
	private PortfolioHandler(IRobot_Algorithms robot) {
		this.robot = robot;
		jpaHelper = JPAHelper.getInstance();
		List<PortfolioEntity> portfolioTables = jpaHelper.getAllPortfolios();
		
		Log.instance().log(TAG.VERY_VERBOSE, "Starting to create portfolios");
		for (PortfolioEntity pt : portfolioTables) {
			IPortfolio p = createExistingPortfolio(pt);
			listOfPortfolios.add(p);
			Log.instance().log(TAG.VERY_VERBOSE, "Portfolio created: " + p.getName());
		}
		Log.instance().log(TAG.VERY_VERBOSE, "Done creating portfolios");
	}
	private IPortfolio createExistingPortfolio(PortfolioEntity pt) {
		Portfolio p = new Portfolio(pt);
		
		if (pt.getAlgortihmSettings().getAlgorithmName() != null) {
			IAlgorithm algorithm = algorithmLoader.getAlgorithm(robot, p);
			
			if (algorithm != null) {
				p.setAlgorithm(algorithm);
				listOfPortfolios.add(p);
				Log.instance().log(TAG.VERY_VERBOSE, p.getName() + " algorithm set to: " + algorithm.getName());
			}
			else {
				Log.instance().log(TAG.ERROR, p.getName() + " couldent set algorithm to: " + pt.getAlgortihmSettings().getAlgorithmName() + " == null");
			}
		}
		else {
			Log.instance().log(TAG.VERY_VERBOSE, p.getName() + " dosent have a algorithm set yet.");
		}
		return p;
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
	public static IPortfolioHandler getInstance(IRobot_Algorithms robot) {
		if(instance == null) {
			synchronized (PortfolioHandler.class) {
				if (instance == null)
					instance = new PortfolioHandler(robot);
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
