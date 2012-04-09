package portfolio;

import generic.FinancialLongConverter;
import generic.Log;
import generic.Log.TAG;
import generic.Pair;

import java.util.List;

import algorithms.IAlgorithm;
import algorithms.loader.AlgorithmsLoader;

import database.jpa.IJPAHelper;
import database.jpa.JPAHelper;
import database.jpa.tables.AlgorithmEntity;
import database.jpa.tables.PortfolioEntity;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;


/**
 * An object of this class will hold one portfolio.
 * When a object of this class is loaded, it will load the algorithm coupled with it.
 * 
 * @author Daniel
 */
public class Portfolio implements IPortfolio {
	private int portfolioId;
	private PortfolioEntity portfolioTable;
	private IJPAHelper jpaHelper;
	private IAlgorithm algorithm;
	
	/**
	 * Start up an existing portfolio
	 * @param portfolioTable The table with this portfolio
	 */
	public Portfolio(PortfolioEntity portfolioTable) {
		this.portfolioTable = portfolioTable;
		jpaHelper = JPAHelper.getInstance();
		if (portfolioTable.getAlgorithm() != null)
			algorithm = AlgorithmsLoader.getInstance(null).loadAlgorithm(this);
		else
			Log.instance().log(TAG.ERROR, "No algorithm set yet for " + getName());
		
		Log.instance().log(TAG.VERY_VERBOSE, "Portfolio " + getName() + " is loaded");
	}
	public Portfolio(PortfolioEntity portfolioTable, IJPAHelper jpaHelper) {
		this.portfolioTable = portfolioTable;
		this.jpaHelper = jpaHelper;
		if (portfolioTable.getAlgorithm() != null)
			algorithm = AlgorithmsLoader.getInstance(null).loadAlgorithm(this);
		else
			Log.instance().log(TAG.ERROR, "No algorithm set yet for " + getName());
		
		Log.instance().log(TAG.VERY_VERBOSE, "Portfolio " + getName() + " is loaded");
	}
	@Override
	public List<StockNames> getAvalibleStocks() {
		return jpaHelper.getStockNames(portfolioTable);
	}

	@Override
	public List<StockPrices> getCurrentStocks() {
		return jpaHelper.getCurrentStocks(portfolioTable);
	}

	@Override
	public List<Pair<StockPrices, StockPrices>> getHistoryStocks() {
		return jpaHelper.getOldStocks(portfolioTable);
	}

	@Override
	public IAlgorithm getAlgorithm() {
		return algorithm;
	}

	@Override
	public boolean setAlgorithm(AlgorithmEntity algorithm) {
		portfolioTable.setAlgorithm(algorithm);
		jpaHelper.updateObject(portfolioTable);
		
		return true;
	}

	@Override
	public long getInvestedAmount() {
		return jpaHelper.getTotalInvestedAmount(portfolioTable);
	}

	@Override
	public long getUnusedAmount() {
		return portfolioTable.getBalance();
	}

	@Override
	public boolean investAmount(long n) {
		return jpaHelper.investMoney(n, portfolioTable);
	}

	@Override
	public boolean setStocksToWatch(List<StockNames> stocks) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stopBuying(boolean flag) {
		if (flag != portfolioTable.isStopBuying()) {
			portfolioTable.setStopBuying(flag);
			jpaHelper.updateObject(portfolioTable);
		}
	}

	@Override
	public void stopSelling(boolean flag) {
		if (flag != portfolioTable.isStopSelling()) {
			portfolioTable.setStopSelling(flag);
			jpaHelper.updateObject(portfolioTable);
		}
	}

	@Override
	public boolean isStopBuyingFlagSet() {
		return portfolioTable.isStopBuying();
	}

	@Override
	public boolean isStopSellingFlagSet() {
		return portfolioTable.isStopSelling();
	}
	@Override
	public String getName() {
		return portfolioTable.getName();
	}

	@Override
	public int getPortfolioId() {
		return portfolioId;
	}
	
	public String toString() {
		return "Name: " + getName() + "Algorithm: " + algorithm.getName() + " Balance: " + FinancialLongConverter.toStringTwoDecimalPoints(getUnusedAmount());
	}
	@Override
	public PortfolioEntity getPortfolioTable() {
		return portfolioTable;
	}
	@Override
	public AlgorithmEntity getAlgorithmTable() {
		return jpaHelper.getAlgorithmTable(portfolioTable);
	}
}
