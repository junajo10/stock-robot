package simulation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import portfolio.IPortfolio;
import robot.IRobot_Algorithms;
import trader.ITrader;
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
	
	JPAHelperForSimulator jpaSimHelper = new JPAHelperForSimulator();
	
	JPAHelper jpaHelper = JPAHelper.getInstance("astro");
	
	PortfolioEntitys testPortfolio;
	PortfolioSimulator portfolio = null;
	IRobot_Algorithms robotSim = new RobotSimulator();
	
	@SuppressWarnings("rawtypes")
	private void initSimulation(AlgorithmEntitys algorithmToSimulate) {
		this.algorithmToSimulate = algorithmToSimulate;
		jpaSimHelper.storeObject(algorithmToSimulate);
		
		ITrader trader = new TraderSimulator2(jpaSimHelper);
		PortfolioEntitys portfolioEntity = new PortfolioEntitys("Simulated Portfolio");
		portfolioEntity.setAlgorithm(algorithmToSimulate);
		jpaSimHelper.storeObject(portfolioEntity);
		
		portfolio = new PortfolioSimulator(portfolioEntity, jpaSimHelper);
		
		try {
			Class<?> c = Class.forName(algorithmToSimulate.getPath());
			Constructor con = c.getConstructor(new Class[] {IRobot_Algorithms.class, IPortfolio.class, ITrader.class });
			algorithm = (IAlgorithm) con.newInstance(new Object[] {robotSim, portfolio, trader});
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (ClassNotFoundException e) {
		} catch (NoSuchMethodException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		portfolio.setAlgorithm(algorithm);
		jpaSimHelper.updateObject(portfolioEntity);
	}
	public void simulateAlgorithm(AlgorithmEntitys algorithmToSimulate) {
		initSimulation(algorithmToSimulate);
		
		PortfolioEntitys port =  portfolio.getPortfolioTable();
		port.invest(new Long("100000000000"), true);
		
		jpaSimHelper.updateObject(port);
		
		Map<String, StockNames> nameStockNameMap = new HashMap<String, StockNames>();
		
		for (StockNames ns : jpaHelper.getAllStockNames()) {
			StockNames n = new StockNames(ns.getName(), ns.getMarket());
			jpaSimHelper.storeObject(n);
			nameStockNameMap.put(n.getName(), n);
		}
		
		Date lastSeenTime = null;
		long curr = 0;
		long max = jpaHelper.getAllStockPricesReverseOrdered().size();
		
		for (StockPrices p : jpaHelper.getAllStockPricesReverseOrdered()) {
			curr ++;
			if (curr > 10)
				break;
			if (p.getTime().equals(lastSeenTime)) {
				StockPrices sp = new StockPrices(nameStockNameMap.get(p.getStockName().getName()), 
						p.getVolume(), p.getLatest(), p.getBuy(), p.getSell(), new Date(p.getTime().getTime()));
				System.out.println(sp);
				jpaSimHelper.storeObject(sp);
			}
			else {
				if (curr%100 == 0)
					System.out.println(curr/max + "% done");
				if (lastSeenTime != null)
					updateAlgorithm();
				lastSeenTime = p.getTime();
				jpaSimHelper.storeObject(new StockPrices(nameStockNameMap.get(p.getStockName().getName()), 
						p.getVolume(), p.getLatest(), p.getBuy(), p.getSell(), p.getTime()));
			}
		}
		
		updateAlgorithm();
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
	
	public static void main(String args[]) {
		SimulationHandler sh = new SimulationHandler();
		
		sh.clearTestDatabase();
		
		sh.simulateAlgorithm(new AlgorithmEntitys("algorithm1", "algorithms.TestAlgorithm"));
		
		sh.end();
	}
	private void end() {
		jpaHelper.getEntityManager().close();
		jpaSimHelper.getEntityManager().close();
	}
}
