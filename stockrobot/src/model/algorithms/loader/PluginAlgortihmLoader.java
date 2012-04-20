package model.algorithms.loader;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.global.Log;
import utils.global.Log.TAG;

import model.algorithms.IAlgorithm;
import model.database.jpa.tables.PortfolioEntity;
import model.portfolio.IPortfolio;
import model.robot.IRobot_Algorithms;




/**
 * This is the AlgortihmLoader.
 * It is based upon the calenderapp from workshop 3.
 * 
 * @author Daniel
 */
public final class PluginAlgortihmLoader {
	
	private static PluginAlgortihmLoader instance = null;
	private Map<String, IAlgorithm> algorithms;
	
	private PluginAlgortihmLoader() {
		int number = reloadAlgorithms();
		
		Log.instance().log(TAG.VERY_VERBOSE, "Found " + number + " algorithms.");
	}
	/**
	 * Reloads algorithms avalible to portfolios.
	 * @return the number of algorithms found.
	 */
	public int reloadAlgorithms() {
		int numberOfAlgorithms = 0;
		algorithms = new HashMap<String, IAlgorithm>();
		// Load all algorithms
		for (IAlgorithm a : PluginLoader.loadAlgorithms()) {
			if (algorithms.containsKey(a.getName())) 
				Log.instance().log(TAG.ERROR, "Duplicate names" + a.getName());
			else {
				algorithms.put(a.getName(), a);
				numberOfAlgorithms++;
			}
		}
		
		for (IAlgorithm a : algorithms.values()) {
			Log.instance().log(TAG.VERY_VERBOSE, "Found and loaded algorithm: " + a.getName());
		}
		return numberOfAlgorithms;
	}
	public IAlgorithm getAlgorithm(IRobot_Algorithms robot, IPortfolio portfolio) {
		PortfolioEntity portfolioEntity = portfolio.getPortfolioTable();
		
		if (algorithms.containsKey(portfolioEntity.getAlgortihmSettings().getAlgorithmName())) {
			// Create a new algorithm from a template
			IAlgorithm algorithm = algorithms.get(portfolioEntity.getAlgortihmSettings().getAlgorithmName()).createInstance(robot, portfolio, robot.getTrader());
			
			// Initiate the algorithm to the settings in this portfolio
			portfolioEntity.getAlgortihmSettings().initiate(algorithm);
			
			return algorithm;
		}
		else {
			Log.instance().log(TAG.ERROR, "Couldent find algorithm to portfolio: " + portfolio);
			return null;
		}
	}
	/**
	 * This method will return all the available algorithms names.
	 * @return a list of algorithm names
	 */
	public List<String> algortihmsAvailable() {
		List<String> a = new ArrayList<String>();
		a.addAll(algorithms.keySet());
		return a;
	}
	/**
	 * Gets an instance of PluginAlgorithmLoader
	 * @return an instance of PluginAlgorithmLoader
	 */
	public static PluginAlgortihmLoader getInstance() {
		if(instance == null) {
			synchronized (PluginAlgortihmLoader.class) {
				if (instance == null)
					instance = new PluginAlgortihmLoader();
			}
		}
		return instance;
	}
}
