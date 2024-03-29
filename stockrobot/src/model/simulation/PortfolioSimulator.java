package model.simulation;

import java.util.List;

import utils.global.FinancialLongConverter;
import utils.global.Pair;

import model.database.jpa.IJPAHelper;
import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;
import model.portfolio.IAlgorithm;
import model.portfolio.IPortfolio;

/**
 * @author Daniel
 *
 * An object of this class will hold one portfolio.
 * When a object of this class is loaded, it will load the algorithm coupled with it.
 */
public class PortfolioSimulator implements IPortfolio {
	private PortfolioEntity portfolioTable;
	private IJPAHelper jpaHelper;
	private IAlgorithm algorithm;
	
	/**
	 * Start up an existing portfolio
	 * @param portfolioTable The table with this portfolio
	 */
	public PortfolioSimulator(PortfolioEntity portfolioTable, IJPAHelper jpaHelper) {
		this.portfolioTable = portfolioTable;
		this.jpaHelper = jpaHelper;
	}
	
	@Override
	public boolean setAlgorithm(IAlgorithm algorithm) {
		this.algorithm = algorithm;
		return false;
	}
	
	@Override
	public IAlgorithm getAlgorithm() {
		
		return this.algorithm;
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
	public String toString() {
		return "Name: " + getName() + "Algorithm: " + algorithm.getName() + " Balance: " + FinancialLongConverter.toStringTwoDecimalPoints(getUnusedAmount()) + "\n";
	}
	
	@Override
	public PortfolioEntity getPortfolioTable() {
		return portfolioTable;
	}
	
	@Override
	public boolean updateAlgorithm() {
		if (algorithm == null)
			return false;
		algorithm.update();
		return true;
	}

	@Override
	public long getCurrentWorth() {
		long currentWorth = 0;
		
		for (PortfolioHistory ph : portfolioTable.getHistory()) {
			if (ph.getSoldDate() == null) {
				StockPrices latest = jpaHelper.getLatestStockPrice(ph.getStockPrice());
				currentWorth += latest.getSell()*ph.getAmount();
			}
		}
		
		return currentWorth;
	}

	@Override
	public void updateSettings() {
		if (algorithm != null) {
			algorithm.giveDoubleSettings(portfolioTable.getAlgortihmSettings().getCurrentDoubleSettings());
			algorithm.giveLongSettings(portfolioTable.getAlgortihmSettings().getCurrentLongSettings());
		}
	}
}