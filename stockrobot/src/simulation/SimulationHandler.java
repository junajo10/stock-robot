package simulation;

import generic.Log;
import generic.Log.TAG;
import generic.Pair;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import portfolio.IPortfolio;
import robot.IRobot_Algorithms;
import trader.ITrader;
import algorithms.IAlgorithm;
import database.jpa.IJPAHelper;
import database.jpa.JPAHelper;
import database.jpa.JPAHelperSimulator;
import database.jpa.tables.AlgorithmEntity;
import database.jpa.tables.AlgorithmSettingDouble;
import database.jpa.tables.AlgorithmSettingLong;
import database.jpa.tables.PortfolioEntity;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;

/**
 * This will be the handler for a simulation of an algorithm.
 * 
 * It will work by creating a separate database for testing and simply 
 * inject all prices from the original stock price tables.
 * 
 * The result will then be given back to the user, but for now sysout will do.
 * 
 * @author Daniel
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
		algorithmToSimulate.initiate(algorithm);
	}
	/**
	 * Tests a given algorithm with real data copied from the original database.
	 * @param algorithmToSimulate Algorithm to simulate
	 * @param howManyStocksBack How many stocks back in time should be copied.
	 * @return Returns the % difference
	 */
	public double simulateAlgorithm(AlgorithmEntity algorithmToSimulate, int howManyStocksBack, List<Pair<String, Long>> longSettings, List<Pair<String, Double>> doubleSettings) {
		initSimulation(algorithmToSimulate);
		
		if (longSettings != null)
			algorithm.giveLongSettings(longSettings);
		if (doubleSettings != null)
			algorithm.giveDoubleSettings(doubleSettings);
		
		long startingBalance = new Long("100000000000");
		
		PortfolioEntity port =  portfolio.getPortfolioTable();
		port.invest(startingBalance, true);
		
		jpaSimHelper.updateObject(port);
		
		Map<String, StockNames> nameStockNameMap = new HashMap<String, StockNames>();
		
		for (StockNames ns : jpaHelper.getAllStockNames()) {
			StockNames n = new StockNames(ns.getName(), ns.getMarket());
			jpaSimHelper.storeObject(n);
			nameStockNameMap.put(n.getName(), n);
		}
		
		Date lastSeenTime = null;
		long curr = 0;
		long max = jpaHelper.getStockPricesReverseOrdered(howManyStocksBack).size();
		
		Log.instance().log(TAG.VERY_VERBOSE, max + " stocks will be tested.");
		
		List<StockPrices> stockPrices = new ArrayList<StockPrices>();
		for (StockPrices p : jpaHelper.getStockPricesReverseOrdered(howManyStocksBack)) {
			//System.out.println(p);
			curr ++;
			
			if (curr%50 == 0)
				Log.instance().log(TAG.NORMAL, "Simulation " + ((double)curr/(double)max)*100 + "% done");
			
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
		
		Log.instance().log(TAG.VERBOSE, "Simulation before selling of stocks: Current balance: " + portfolio.getPortfolioTable().getBalance());
		
		for (PortfolioHistory ph : portfolio.getPortfolioTable().getHistory()) {
			if (ph.getSoldDate() == null) {
				Log.instance().log(TAG.VERY_VERBOSE, "Simulation: Selling " + ph.getAmount() + " of " + ph.getStockPrice().getStockName().getName());
				trader.sellStock(ph.getStockPrice(), ph.getAmount(), portfolio.getPortfolioTable());
			}
		}

		Log.instance().log(TAG.VERBOSE, "Simulation balance: " + portfolio.getPortfolioTable().getBalance());

		return ((double)portfolio.getPortfolioTable().getBalance()/(double)startingBalance)*100;
	}
	public void clearTestDatabase() {
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
		Log.instance().setFilter(TAG.VERY_VERBOSE, true);
		SimulationHandler sim = new SimulationHandler();
		sim.clearTestDatabase();
		
		List<Pair<String, Long>> longSettings = new LinkedList<Pair<String,Long>>();
		longSettings.add(new Pair<String, Long>("buy", (long)4));
		longSettings.add(new Pair<String, Long>("sell", (long)4));
		
		double diff = sim.simulateAlgorithm(new AlgorithmEntity("Algorithm1", "algorithms.TestAlgorithm"), 300, longSettings, null);
		sim.clearTestDatabase();
		Log.instance().log(TAG.NORMAL, "Simulation done, change in balance: " + diff + "%");
	}
}
