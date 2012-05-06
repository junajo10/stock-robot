package model.simulation;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.global.Log;
import utils.global.Pair;
import utils.global.Log.TAG;

import model.algorithms.loader.PluginAlgortihmLoader;
import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;
import model.portfolio.IAlgorithm;
import model.robot.IRobot_Algorithms;


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
public class SimulationHandler extends SimModel {
	PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private IJPAHelper jpaSimHelper;
	private IJPAHelper jpaHelper = JPAHelper.getInstance();
	
	private PortfolioSimulator portfolio = null;

	private IRobot_Algorithms robotSim = new RobotSimulator();
	
	private int progress = 0;
	private long worth = 0;
	private Map<String, Long> latestPieData = new HashMap<String, Long>();
	private int updatePieAt = 0;
	private int nextPieStep = 25;
	
	
	public SimulationHandler() {
		jpaSimHelper = robotSim.getJPAHelper();
		
		clearTestDatabase();
	}
	private void initSimulation(String algorithmToSimulate, List<Pair<String, Long>> longSettings, List<Pair<String, Double>> doubleSettings) {
		PortfolioEntity portfolioEntity = new PortfolioEntity("Simulated Portfolio");
		portfolioEntity.setAlgorithm(algorithmToSimulate);
		
		jpaSimHelper.storeObject(portfolioEntity);
		
		portfolio = new PortfolioSimulator(portfolioEntity, jpaSimHelper);
		
		PluginAlgortihmLoader algorithmLoader = PluginAlgortihmLoader.getInstance();
		
		IAlgorithm algorithm = algorithmLoader.loadAlgorithm(algorithmLoader.getAlgorithmNames().get(0));
		
		algorithm.setPortfolio(portfolio);
		algorithm.setRobot(robotSim);
		
		portfolio.setAlgorithm(algorithm);
		
		if (longSettings != null)
			algorithm.giveLongSettings(longSettings);
		if (doubleSettings != null)
			algorithm.giveDoubleSettings(doubleSettings);
	}
	/**
	 * Tests a given algorithm with real data copied from the original database.
	 * @param algorithmToSimulate Algorithm to simulate
	 * @param howManyStocksBack How many stocks back in time should be copied.
	 * @return Returns the % difference
	 */
	public double simulateAlgorithm(String algorithmToSimulate, int howManyStocksBack,
			List<Pair<String, Long>> longSettings, List<Pair<String, Double>> doubleSettings) {
		
		
		latestPieData.clear();
		
		initSimulation(algorithmToSimulate, longSettings, doubleSettings);
		
		long startingBalance = Long.valueOf("10000000000000");
		
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
			curr++;
			if (curr%5 == 0) {
				setProgress((int) (((double)curr/(double)max)*100));
			}
			
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
				
				setWorth(portfolio.getCurrentWorth());
				
			}
			if (curr>=max)
				break;
		}
		setProgress(100);
		Log.instance().log(TAG.VERBOSE, "Simulation before selling of stocks: Current balance: " + portfolio.getPortfolioTable().getBalance());
		
		for (PortfolioHistory ph : portfolio.getPortfolioTable().getHistory()) {
			if (ph.getSoldDate() == null) {
				fillPie(ph.getStockPrice().getStockName().getName(), ph.getAmount(), ph.getStockPrice().getBuy());
				
				Log.instance().log(TAG.VERY_VERBOSE, "Simulation: Selling " + ph.getAmount() + " of " + ph.getStockPrice().getStockName().getName());
				robotSim.getTrader().sellStock(ph.getStockPrice(), ph.getAmount(), portfolio.getPortfolioTable());
			}
		}

		
		setWorth(portfolio.getCurrentWorth());
		updatePieData();
		
		System.out.println("Simulation balance: " + portfolio.getPortfolioTable().getBalance());
		Log.instance().log(TAG.VERBOSE, "Simulation balance: " + portfolio.getPortfolioTable().getBalance());

		return ((double)portfolio.getPortfolioTable().getBalance()/(double)startingBalance)*100;
	}
	private void updatePieData() {
		for (PortfolioHistory ph : portfolio.getPortfolioTable().getHistory()) {
			if (ph.getSoldDate() == null) {
				fillPie(ph.getStockPrice().getStockName().getName(), ph.getAmount(), ph.getStockPrice().getBuy());
			}
		}
	}
	private void setWorth(long currentWorth) {
		firePropertyChange("Portfolio Worth", getInitialValue(), currentWorth);
		worth = currentWorth;
	}
	private void fillPie(String name, long amount, long buy) {
		if (latestPieData.containsKey(name)) {
			latestPieData.put(name, amount*buy + latestPieData.get(name));
		}
		else
			latestPieData.put(name, amount*buy);
		
		firePropertyChange("newPieData", null, latestPieData);
	}
	public Map<String, Long> getLatestPieData() {
		return latestPieData;
	}
	private void setProgress(int i) {
		if (i>=updatePieAt || i == 100) {
			
			updatePieData();
			updatePieAt = i+nextPieStep;
			
			System.out.println(i + " " + updatePieAt);
		}
		
		firePropertyChange("Progress", progress, i);
		progress = i;
	}
	public void clearTestDatabase() {
		while (jpaSimHelper.getAllPortfolios().size() > 0) {
			PortfolioEntity p = jpaSimHelper.getAllPortfolios().get(0);
			
			jpaSimHelper.remove(p);
		}
	    
	    for (StockPrices sp : jpaSimHelper.getAllStockPrices()) {
	    	jpaSimHelper.remove(sp);
	    }
		for (StockNames sn : jpaSimHelper.getAllStockNames()) {
			jpaSimHelper.remove(sn);
		}
	}
	private void updateAlgorithm() {
		portfolio.updateAlgorithm();
	}
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
	public void startSimulation() {
		Thread simulationThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				simulateAlgorithm(getAlgorithm(), getStocksBack(), null, null);
			}
		});
		simulationThread.start();
		
	}
}
