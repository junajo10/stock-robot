package algorithms;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import generic.Log;
import generic.Pair;
import generic.Log.TAG;
import database.jpa.IJPAAlgortihm;
import database.jpa.tables.AlgorithmSettingDouble;
import database.jpa.tables.AlgorithmSettingLong;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import portfolio.IPortfolio;
import robot.IRobot_Algorithms;
import trader.ITrader;

/**
 * A copy of TestAlgorithm
 * This is to really test that we can have several algorithms in the system.
 * 
 * @author daniel
 */
public class TestAlgorithm2 implements IAlgorithm{

	IRobot_Algorithms robot;
	IPortfolio portfolio;
	ITrader trader = null;
	IJPAAlgortihm jpaHelper = null;
	private long buySetting = 5;
	private long sellSetting = 5;


	public TestAlgorithm2(IRobot_Algorithms robot, IPortfolio portfolio, ITrader trader) {
		this.robot = robot;
		this.portfolio = portfolio;
		this.trader = trader;
		this.jpaHelper = robot.getJPAHelper();
		Log.instance().log(TAG.VERY_VERBOSE, "Inside TestAlgorithm2 constructor");
	}

	@Override
	public boolean update() {
		if (portfolio.getPortfolioTable().getBalance() < 1000000) {
			return false;
		}

		List<StockPrices> ownedStockes = jpaHelper.getCurrentStocks(portfolio.getPortfolioTable());

		for (StockPrices sp : ownedStockes ) {

			List<StockPrices> cs = jpaHelper.getNLatest(sp, (int)sellSetting);
			if (cs.size() == sellSetting) {
				long last = Long.MAX_VALUE;
				boolean sell = true;
				for (int i = (int)sellSetting-1; i >= 0; i--) {
					if (cs.get(i).getBuy() < last) {
						last = cs.get(i).getBuy();
					}
					else
						sell = false;
				}

				if (sell) {
					//Sell all
					List<PortfolioHistory> ph = jpaHelper.getPortfolioHistory(sp, portfolio.getPortfolioTable());
					for (PortfolioHistory pHistory : ph) {
						if (pHistory.getSoldDate() == null) {
							trader.sellStock(sp, pHistory.getAmount(), portfolio.getPortfolioTable());
							jpaHelper.updateObject(pHistory);
						}
					}
				}
			}
		}
		for (Pair<StockNames, List<StockPrices>> stockInfo: jpaHelper.getStockInfo((int)buySetting)) {
			boolean buy = true;
			long last = Long.MAX_VALUE;
			for (int i = 0; i < stockInfo.getRight().size(); i++) {
				if (stockInfo.getRight().get(i).getBuy() >= last)
					buy = false;
				last = stockInfo.getRight().get(i).getBuy();
			}
			//Buy!
			if (buy) {
				Log.instance().log( Log.TAG.VERY_VERBOSE, "Algo2: BUY!" );
				//Buy a couple of stock, if the stockprice is NOT zero (avoid divide by zero)
				long firstStockBuyPrice = stockInfo.getRight().get(0).getBuy();
				if( firstStockBuyPrice != 0 ) {
					
					long amount = (long) (portfolio.getPortfolioTable().getBalance()/10/firstStockBuyPrice); 
					
					buyStock( stockInfo.getRight().get(0), amount );
				}
			}
		}




		return true;
	}
	private void buyStock(StockPrices stockPrices, long amount) {
		trader.buyStock(stockPrices, amount, portfolio.getPortfolioTable());

	}

	@Override
	public String getName() {
		return "TestAlgoritm1";
	}
	@Override
	public String getDescription() {
		return "Test algorithm1\n\nBuys when a stock has gone up 3 times in a row\nSells when a stock has gone down 3 times in a row";
	}
	
	@Override
	public Set<AlgorithmSettingDouble> getDefaultDoubleSettings() {
		Set<AlgorithmSettingDouble> doubleSettings = new HashSet<AlgorithmSettingDouble>();
		return doubleSettings;
	}
	
	@Override
	public Set<AlgorithmSettingLong> getDefaultLongSettings() {
		Set<AlgorithmSettingLong> longSettings = new HashSet<AlgorithmSettingLong>();
		longSettings.add(new AlgorithmSettingLong("buy", 5, "Number of times a stock has to climb before buying", 1, 1, 100));
		longSettings.add(new AlgorithmSettingLong("sell", 5, "Number of times a stock has to drop before selling", 2, 1, 100));
		return longSettings;
	}

	@Override
	public boolean giveDoubleSettings(Set<AlgorithmSettingDouble> doubleSettings) {
		return false;
	}

	@Override
	public boolean giveLongSettings(Set<AlgorithmSettingLong> longSettings) {
		for (AlgorithmSettingLong asl : longSettings) {
			if (asl.getName().contains("buy")) {
				this.buySetting = asl.getValue();
			}
			else if (asl.getName().contains("sell")) {
				this.sellSetting = asl.getValue();
			}
			else 
				return false;
		}
		return true;
	}
}
