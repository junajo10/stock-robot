package portfolio;

import generic.Pair;

import java.util.List;

import algorithms.IAlgorithm;
import algorithms.loader.AlgorithmsLoader;

import database.jpa.JPAHelper;
import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;


/**
 * @author Daniel
 *
 * An object of this class will hold one portfolio.
 * When a object of this class is loaded, it will load the algorithm coupled with it.
 */
public class Portfolio implements IPortfolio {
	private int portfolioId;
	private PortfolioEntitys portfolioTable;
	private JPAHelper jpaHelper;
	private IAlgorithm algorithm;
	
	/**
	 * Start up an existing portfolio
	 * @param portfolioTable The table with this portfolio
	 */
	public Portfolio(PortfolioEntitys portfolioTable) {
		this.portfolioTable = portfolioTable;
		jpaHelper = JPAHelper.getInstance();
		algorithm = AlgorithmsLoader.getInstance(null).loadAlgorithm(this);
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
	public boolean setAlgorithm(AlgorithmEntitys algorithm) {
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
		return "Name: " + getName() + "Algorithm: " + algorithm.getName()  + "\n";
	}
	@Override
	public PortfolioEntitys getPortfolioTable() {
		return portfolioTable;
	}
	@Override
	public AlgorithmEntitys getAlgorithmTable() {
		return jpaHelper.getAlgorithmTable(portfolioTable);
	}
}
