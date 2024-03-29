package model.portfolio;

import java.util.List;

import utils.global.FinancialLongConverter;
import utils.global.Log;
import utils.global.Pair;
import utils.global.Log.TAG;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;

/**
 * An object of this class will hold one portfolio.
 * When a object of this class is loaded, it will load the algorithm coupled with it.
 * 
 * @author Daniel
 */
public class Portfolio implements IPortfolio {
	final transient private PortfolioEntity portfolioTable;
	final transient private IJPAHelper jpaHelper;
	private IAlgorithm algorithm;
	
	/**
	 * Start up an existing portfolio
	 * @param portfolioTable The table with this portfolio
	 */
	public Portfolio(final PortfolioEntity portfolioTable, final IAlgorithm algorithm) {
		jpaHelper = JPAHelper.getInstance();
		this.portfolioTable = portfolioTable;
		this.algorithm = algorithm;

		Log.log(TAG.VERY_VERBOSE, "Portfolio " + portfolioTable.getName() + " is loaded");
	}
	public Portfolio(final PortfolioEntity portfolioTable) {
		// Portfolio without algorithm yet.
		this.portfolioTable = portfolioTable;
		jpaHelper = JPAHelper.getInstance();
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
	public boolean setAlgorithm(final IAlgorithm algorithm) {
		this.algorithm = algorithm;
		return true;
	}
	
	@Override
	public IAlgorithm getAlgorithm() {	
		return this.algorithm;
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
	public boolean investAmount(final long amount) {
		return jpaHelper.investMoney(amount, portfolioTable);
	}

	@Override
	public void stopBuying(final boolean flag) {
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
		if (algorithm != null)
			return "Name: " + getName() + "Algorithm: " + algorithm.getName() + " Balance: " + FinancialLongConverter.toStringTwoDecimalPoints(getUnusedAmount());
		else
			return "Name: " + getName() + " Balance: " + FinancialLongConverter.toStringTwoDecimalPoints(getUnusedAmount());
	}
	@Override
	public PortfolioEntity getPortfolioTable() {
		return portfolioTable;
	}
	@Override
	public boolean updateAlgorithm() {
		boolean state = false;
		if (this.algorithm != null) {
			algorithm.update();
			state = true;
		}
		return state;
	}
	@Override
	public long getCurrentWorth() {
		long currentWorth = getUnusedAmount();
		
		for (int i = 0; i < portfolioTable.getHistory().size(); i++) {
			final PortfolioHistory portfolioHistory = portfolioTable.getHistory().get(i);
			
			if (portfolioHistory.getSoldDate() == null) {
				final StockPrices latest = jpaHelper.getLatestStockPrice(portfolioHistory.getStockPrice());
				currentWorth += latest.getSell()*portfolioHistory.getAmount();
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