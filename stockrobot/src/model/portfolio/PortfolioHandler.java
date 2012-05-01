package model.portfolio;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import utils.global.Log;
import utils.global.Log.TAG;

import model.algorithms.loader.PluginAlgortihmLoader;
import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;
import model.robot.IRobot_Algorithms;



/**
 * @author Daniel
 *
 * Will manage all portfolios.
 * It will start by creating all the portfolio objects.
 * Each portfolio will then create a algorithm instance.
 */
public final class PortfolioHandler implements IPortfolioHandler{

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
			IAlgorithm algorithm = algorithmLoader.loadAlgorithm(pt.getAlgortihmSettings().getAlgorithmName());
			
			if (algorithm != null) {
				algorithm.setRobot(robot);
				algorithm.setPortfolio(p);
				
				if (!pt.getAlgortihmSettings().isInitiated()) {
					pt.getAlgortihmSettings().initiate(algorithm.getDefaultDoubleSettings(), algorithm.getDefaultLongSettings());
					jpaHelper.updateObject(pt);
				}
				algorithm.giveDoubleSettings(pt.getAlgortihmSettings().getCurrentDoubleSettings());
				algorithm.giveLongSettings(pt.getAlgortihmSettings().getCurrentLongSettings());
				
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
		
		IPortfolio p = new Portfolio(pt);
		
		listOfPortfolios.add(p);
		
		return p;
	}
	
	@Override
	public boolean setAlgorithm(IPortfolio p, String algorithmName) {
		IAlgorithm algorithm = algorithmLoader.loadAlgorithm(algorithmName);
		if (algorithm != null) {
			p.setAlgorithm(algorithm);
			
			algorithm.setRobot(robot);
			algorithm.setPortfolio(p);
			
			PortfolioEntity pe = p.getPortfolioTable();
			pe.setAlgorithm(algorithmName);
			
			pe.getAlgortihmSettings().initiate(algorithm.getDefaultDoubleSettings(), algorithm.getDefaultLongSettings());
			
			jpaHelper.updateObject(pe);
			
			return true;
		}
		return false;
	}
	
	public void updateAlgorithms() {
		for (IPortfolio p : listOfPortfolios) {
			p.updateAlgorithm();
		}
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
		synchronized (PortfolioHandler.class) {
			if (instance == null)
				instance = new PortfolioHandler(robot);
		}
		return instance;
	}
	public static IPortfolioHandler getInstance() {
		if (instance != null)
			return instance;
		return null;
	}
	@Override
	public void addAddObserver(PropertyChangeListener listener) {
		propertyChangeSuport.addPropertyChangeListener(listener);
	}
	@Override
	public void removeObserver(PropertyChangeListener listener) {
		propertyChangeSuport.addPropertyChangeListener(listener);
	}

	@Override
	public List<String> getAlgorithmNames() {
		return algorithmLoader.getAlgorithmNames();
	}
}
