package algorithms;


import java.util.List;

import generic.Log;
import generic.Pair;
import database.jpa.IJPAAlgortihm;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import portfolio.IPortfolio;
import robot.IRobot_Algorithms;
import trader.ITrader;

/**
 * @author daniel
 *
 * A simple test algorithm based on a algorithm Kristian has come up with
 */
public class TestAlgorithm implements IAlgorithm{
	
	IRobot_Algorithms robot;
	IPortfolio portfolio;
	ITrader trader = null;
	IJPAAlgortihm jpaHelper = null;
	
	
	public TestAlgorithm(IRobot_Algorithms robot, IPortfolio portfolio, ITrader trader) {
		this.robot = robot;
		this.portfolio = portfolio;
		this.trader = trader;
		this.jpaHelper = robot.getJPAHelper();
		
		System.out.println("Inside TestAlgorithm constructor");
	}
	
	@Override
	public boolean update() {
		
		Log.instance().log( Log.TAG.VERY_VERBOSE, "Algo1: UPDATE!" );
		
		if (portfolio.getPortfolioTable().getBalance() < 1000000) {
			return false;
		}
		
		List<StockPrices> ownedStockes = jpaHelper.getCurrentStocks(portfolio.getPortfolioTable());
		for (StockPrices sp : ownedStockes ) {
			
			Log.instance().log( Log.TAG.VERY_VERBOSE, "Algo1: Checking ownedStocks!" );
			
			List<StockPrices> cs = jpaHelper.getNLatest(sp, 3);
			if (cs.size() == 3) {
				long last = Long.MAX_VALUE;
				boolean sell = true;
				for (int i = 2; i >= 0; i--) {
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
		for (Pair<StockNames, List<StockPrices>> stockInfo: jpaHelper.getStockInfo(3)) {
			boolean buy = true;
			long last = Long.MAX_VALUE;
			for (int i = 0; i < stockInfo.getRight().size(); i++) {
				
				if (stockInfo.getRight().get(i).getBuy() >= last)
					buy = false;
				last = stockInfo.getRight().get(i).getBuy();
			}
			
			//Buy!
			if (buy) {
				Log.instance().log( Log.TAG.VERY_VERBOSE, "Algo1: BUY!" );
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
	public boolean giveSetting(int id, int value) {
		return false;
	}
	@Override
	public boolean giveSetting(int id, String value) {

		return false;
	}
	@Override
	public boolean giveSetting(int id, double value) {

		return false;
	}
	@Override
	public int getNumberOfSettings() {

		return 1;
	}
	@Override
	public String getSettingText(int id) {
		switch (id) {
		case 0:
			return "Buy stock after it has climbed X times:";
		default:
			System.out.println("garrr");
		}
			
		return null;
	}
	@Override
	public String getSettingType(int id) {
		switch (id) {
		case 0:
			return "int";
		}
		return null;
	}
	@Override
	public String getSettingRange(int id) {
		switch (id) {
		case 0:
			return "1-5000";
		}
		return null;
	}
	@Override
	public String getSettingDefault(int id) {
		switch (id) {
		case 0:
			return "3";
		}
		return null;
	}
	@Override
	public int getCurrentIntSetting(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getCurrentStringSetting(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double getCurrentDoubleSetting(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	


}
