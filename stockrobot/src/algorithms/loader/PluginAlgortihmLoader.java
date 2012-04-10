package algorithms.loader;

import java.util.HashMap;
import java.util.Map;

import database.jpa.tables.PortfolioEntity;

import portfolio.IPortfolio;
import robot.IRobot_Algorithms;
import trader.ITrader;

import algorithms.IAlgorithm;

public class PluginAlgortihmLoader {
	
	private static PluginAlgortihmLoader instance = null;
	private Map<String, IAlgorithm> algorithms = new HashMap<String, IAlgorithm>();
	
	private PluginAlgortihmLoader() {
		// Load all algorithms
		for (IAlgorithm a : PluginLoader.loadAlgorithms()) {
			if (algorithms.containsKey(a.getName()))
				System.out.println("ERROR duplicate names");
			else
				algorithms.put(a.getName(), a);
		}
		
		for (IAlgorithm a : algorithms.values()) {
			System.out.println(a.getName());
		}
	}
	
	public IAlgorithm getAlgorithm(IRobot_Algorithms robot, IPortfolio portfolio, ITrader trader) {
		PortfolioEntity portfolioEntity = portfolio.getPortfolioTable();
		
		// Create a new algorithm from a template
		if (algorithms.containsKey(portfolioEntity.getAlgortihmSettings().getAlgorithmName())) {
			IAlgorithm algorithm = algorithms.get(portfolioEntity.getAlgortihmSettings().getAlgorithmName()).createInstance(robot, portfolio, trader);
			
			portfolioEntity.getAlgortihmSettings().initiate(algorithm);
			// update algorithmsettings initiated can change
			
			
			return algorithm;
		}
		else {
			System.out.println("blablabla");
			return null;
		}
	}
	
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
