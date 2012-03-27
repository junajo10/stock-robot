package simulation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import portfolio.IPortfolio;
import robot.IRobot_Algorithms;
import trader.ITrader;
import trader.TraderSimulator;
import algorithms.IAlgorithm;
import database.jpa.JPAHelper;
import database.jpa.JPAHelperForSimulator;
import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import database.jpa.tables.StocksToWatch;

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
	JPAHelper jpaHelper = JPAHelper.getInstance("astro");
	JPAHelperForSimulator jpaSimHelper = new JPAHelperForSimulator();
	PortfolioEntitys testPortfolio;
	IPortfolio portfolio = null;
	IRobot_Algorithms robotSim = new RobotSimulator();
	
	@SuppressWarnings("rawtypes")
	private void initSimulation(AlgorithmEntitys algorithmToSimulate) {
		this.algorithmToSimulate = algorithmToSimulate;
		
		PortfolioEntitys portfolioEntity = new PortfolioEntitys("Simulated Portfolio");
		portfolioEntity.setAlgorithm(algorithmToSimulate);
		jpaSimHelper.storeObject(portfolioEntity);
		
		portfolio = new PortfolioSimulator(portfolioEntity, jpaSimHelper);
		
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

		//portfolio.investAmount(new Long("100000000000"));
		
		portfolio.getPortfolioTable().invest(new Long("100000000000"), true);
		
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
		
		System.out.println(portfolio.getPortfolioTable().getBalance());
		
		clearTestDatabase();
		
	}
	public void clearTestDatabase() {
		for (PortfolioInvestment investment : jpaSimHelper.getAllPortfolioInvestment()) {
			jpaSimHelper.remove(investment);
		}
		
		for (StocksToWatch stw : jpaSimHelper.getAllStocksToWatch()) {
			jpaSimHelper.remove(stw);
		}
		
		while (jpaSimHelper.getAllPortfolios().size() > 0) {
			PortfolioEntitys p = jpaSimHelper.getAllPortfolios().get(0);
			if (p.getHistory() != null) {
				if (p.getHistory().iterator().hasNext()) {
					jpaSimHelper.remove(p.getHistory().iterator().next());
				}
			}
			
			jpaSimHelper.remove(p);
		}
	    for (AlgorithmEntitys a : jpaSimHelper.getAllAlgorithms()) {
	    	jpaSimHelper.remove(a);
	    }
		
	    
	    for (StockPrices sp : jpaSimHelper.getAllStockPrices()) {
	    	jpaSimHelper.remove(sp);
	    }
		for (StockNames sn : jpaSimHelper.getAllStockNames()) {
			jpaSimHelper.remove(sn);
		}
	}
	private void updateAlgorithm() {
		portfolio.getAlgorithm().update();
	}
	/*
	 * Exception in thread "main" <openjpa-2.2.0-r422266:1244990 nonfatal user error> 
	 * org.apache.openjpa.persistence.InvalidStateException: 
	 * Primary key field database.jpa.tables.PortfolioEntitys.portfolioId of 
	 * database.jpa.tables.PortfolioEntitys@57945696 has non-default value. 
	 * The instance life cycle is in PNewState state and hence an existing non-default 
	 * value for the identity field is not permitted. You either need to remove the 
	 * @GeneratedValue annotation or modify the code to remove the initializer processing.
	 */
	
	public static void main(String args[]) {
		SimulationHandler sh = new SimulationHandler();
		
		sh.clearTestDatabase();
		
		sh.simulateAlgorithm(new AlgorithmEntitys("algorithm1", "algorithms.TestAlgorithm"));
	}
}
