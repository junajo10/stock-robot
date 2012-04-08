package simulation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import portfolio.IPortfolio;
import robot.IRobot_Algorithms;
import trader.ITrader;
import algorithms.IAlgorithm;
import database.jpa.IJPAHelper;
import database.jpa.JPAHelper;
import database.jpa.JPAHelperSimulator;
import database.jpa.tables.AlgorithmEntity;
import database.jpa.tables.PortfolioEntity;
import database.jpa.tables.PortfolioHistory;
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

	AlgorithmEntity algorithmToSimulate;
	IAlgorithm algorithm;
	
	IJPAHelper jpaSimHelper = new JPAHelperSimulator();
	
	IJPAHelper jpaHelper = JPAHelper.getInstance();
	
	PortfolioEntity testPortfolio;
	PortfolioSimulator portfolio = null;
	IRobot_Algorithms robotSim = new RobotSimulator(jpaSimHelper);
	ITrader trader;
	
	@SuppressWarnings("rawtypes")
	private void initSimulation(AlgorithmEntity algorithmToSimulate) {
		this.algorithmToSimulate = algorithmToSimulate;
		jpaSimHelper.storeObject(algorithmToSimulate);
		
		trader = new TraderSimulator2(jpaSimHelper);
		PortfolioEntity portfolioEntity = new PortfolioEntity("Simulated Portfolio");
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
	public void simulateAlgorithm(AlgorithmEntity algorithmToSimulate) {
		initSimulation(algorithmToSimulate);
		
		PortfolioEntity port =  portfolio.getPortfolioTable();
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
		long max = jpaHelper.getStockPricesReverseOrdered(100).size();
		
		System.out.println(max);
		
		List<StockPrices> stockPrices = new ArrayList<StockPrices>();
		for (StockPrices p : jpaHelper.getStockPricesReverseOrdered(100)) {
			//System.out.println(p);
			curr ++;
			
			if (curr%100 == 0)
				System.out.println(((double)curr/(double)max)*100 + "% done");
			
			if (p.getTime().equals(lastSeenTime) || lastSeenTime == null) {
				StockPrices sp = new StockPrices(nameStockNameMap.get(p.getStockName().getName()), 
						p.getVolume(), p.getLatest(), p.getBuy(), p.getSell(), new Date(p.getTime().getTime()));
				stockPrices.add(sp);
				
				lastSeenTime = p.getTime();
			}
			else {
				lastSeenTime = p.getTime();
				
				StockPrices sp = new StockPrices(nameStockNameMap.get(p.getStockName().getName()), 
						p.getVolume(), p.getLatest(), p.getBuy(), p.getSell(), new Date(p.getTime().getTime()));
				
				stockPrices.add(sp);
				
				jpaSimHelper.storeListOfObjects(stockPrices);
				
				updateAlgorithm();
			}
		}
		
		System.out.println("Current balance: " + portfolio.getPortfolioTable().getBalance());
		
		System.out.println("Selling all current stocks");
		
		for (PortfolioHistory ph : portfolio.getPortfolioTable().getHistory()) {
			if (ph.getSoldDate() == null) {
				System.out.println("Selling " + ph.getAmount() + " of " + ph.getStockPrice().getStockName().getName());
				trader.sellStock(ph.getStockPrice(), ph.getAmount(), portfolio.getPortfolioTable());
			}
		}
		
		System.out.println("Balance: " + portfolio.getPortfolioTable().getBalance());
		
		//clearTestDatabase();
		
	}
	public void clearTestDatabase() {
		for (StocksToWatch stw : jpaSimHelper.getAllStocksToWatch()) {
			jpaSimHelper.remove(stw);
		}
		
		while (jpaSimHelper.getAllPortfolios().size() > 0) {
			PortfolioEntity p = jpaSimHelper.getAllPortfolios().get(0);
			
			jpaSimHelper.remove(p);
		}
	    for (AlgorithmEntity a : jpaSimHelper.getAllAlgorithms()) {
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
	private void end() {
		jpaHelper.getEntityManager().close();
		jpaSimHelper.getEntityManager().close();
	}
	public static void main(String args[]) {
		SimulationHandler sim = new SimulationHandler();
		sim.clearTestDatabase();
		sim.simulateAlgorithm(new AlgorithmEntity("Algorithm1", "algorithms.TestAlgorithm"));
		//sim.clearTestDatabase();
	}
}
