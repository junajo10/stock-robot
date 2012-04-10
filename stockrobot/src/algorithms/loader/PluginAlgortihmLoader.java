package algorithms.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import database.jpa.tables.PortfolioEntity;

import portfolio.IPortfolio;
import robot.IRobot_Algorithms;
import trader.ITrader;

import algorithms.IAlgorithm;

/**
 * This is the AlgortihmLoader.
 * 
 * @author Daniel
 */
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
	public IAlgorithm getAlgorithm(IRobot_Algorithms robot, IPortfolio portfolio) {
		System.out.println("sfga");
		PortfolioEntity portfolioEntity = portfolio.getPortfolioTable();
		
		// Create a new algorithm from a template
		System.out.println("pentity: " + portfolioEntity);
		System.out.println("pentity al: " + portfolioEntity.getAlgortihmSettings().getAlgorithmName());
		System.out.println(algorithms);
		if (algorithms.containsKey(portfolioEntity.getAlgortihmSettings().getAlgorithmName())) {
			IAlgorithm algorithm = algorithms.get(portfolioEntity.getAlgortihmSettings().getAlgorithmName()).createInstance(robot, portfolio, robot.getTrader());
			
			portfolioEntity.getAlgortihmSettings().initiate(algorithm);
			// update algorithmsettings initiated can change
			
			
			
			return algorithm;
		}
		else {
			System.out.println("blablabla");
			return null;
		}
	}
	
	public IAlgorithm getAlgorithm(IRobot_Algorithms robot, IPortfolio portfolio, ITrader trader) {
		System.out.println("sfga");
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
	public List<String> algortihmsAvailable() {
		List<String> a = new ArrayList<String>();
		a.addAll(algorithms.keySet());
		return a;
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
