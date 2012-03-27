package simulation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import portfolio.IPortfolio;
import portfolio.Portfolio;
import robot.IRobot_Algorithms;
import trader.ITrader;
import trader.TraderSimulator;
import algorithms.IAlgorithm;
import algorithms.loader.AlgorithmsLoader;
import database.jpa.JPAHelper;
import database.jpa.JPAHelperForSimulator;
import database.jpa.JPAHelperForUnderstanding;
import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;

/**
 * @author Daniel
 *
 * This will be the handler for a simulation of an algorithm.
 * 
 * It will work by creating a separate database for testing and simply 
 * inject all prices from the original stock price tables.
 * 
 * The result will then be given back to the user, but for now sysout will do.
 */
public class SimulationHandler {

	AlgorithmEntitys algorithmToSimulate;
	IAlgorithm algorithm;
	JPAHelper jpaHelper = JPAHelper.getInstance();
	JPAHelperForSimulator jpaSimHelper = new JPAHelperForSimulator();
	PortfolioEntitys testPortfolio;
	IPortfolio portfolio = null;
	IRobot_Algorithms robotSim = new RobotSimulator();
	
	private void initSimulation(AlgorithmEntitys algorithmToSimulate) {
		this.algorithmToSimulate = algorithmToSimulate;
		
		PortfolioEntitys portfolioEntity = new PortfolioEntitys("Simulated Portfolio");
		portfolioEntity.setAlgorithm(algorithmToSimulate);
		jpaSimHelper.storeObject(portfolioEntity);
		
		portfolio = new Portfolio(portfolioEntity);
		
		try {
			Class<?> c = Class.forName(algorithmToSimulate.getPath());
			Constructor con = c.getConstructor(new Class[] {IRobot_Algorithms.class, IPortfolio.class, ITrader.class });
			algorithm = (IAlgorithm) con.newInstance(new Object[] {robotSim, portfolio, TraderSimulator.getInstance()});
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (ClassNotFoundException e) {
		} catch (NoSuchMethodException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}
	public void simulateAlgorithm(AlgorithmEntitys algorithmToSimulate) {
		initSimulation(algorithmToSimulate);

		Map<String, StockNames> nameStockNameMap = new HashMap<String, StockNames>();
		
		for (StockNames ns : jpaHelper.getAllStockNames()) {
			nameStockNameMap.put(ns.getName(), new StockNames(ns.getName(), ns.getMarket()));
			jpaSimHelper.storeObject(new StockNames(ns.getName(), ns.getMarket()));
		}
		
		Date lastSeenTime = null;
		for (StockPrices p : jpaHelper.getAllStockPricesReverseOrdered()) {
			if (p.getTime().equals(lastSeenTime)) {
				jpaSimHelper.storeObject(new StockPrices(nameStockNameMap.get(p.getStockName().getName()), 
						p.getVolume(), p.getLatest(), p.getBuy(), p.getSell(), p.getTime()));
			}
			else {
				updateAlgorithm();
				lastSeenTime = p.getTime();
				jpaSimHelper.storeObject(new StockPrices(nameStockNameMap.get(p.getStockName().getName()), 
						p.getVolume(), p.getLatest(), p.getBuy(), p.getSell(), p.getTime()));
			}
		}
		
	}
	private void updateAlgorithm() {
		algorithm.update();
	}
}
