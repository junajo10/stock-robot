package trader;

import database.jpa.JPAHelper;
import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.StockPrices;

/**
 * @author Daniel
 *
 * A very simple TraderSimulator that currently only supports buying.
 */
public class TraderSimulator implements ITrader{
	private static TraderSimulator instance = null;
	
	private TraderSimulator() {
		
	}
	public static TraderSimulator getInstance() {
		if (instance == null) {
			instance = new TraderSimulator();
		}
		return instance;
	}
	@Override
	public boolean buyStock(StockPrices s, long amount, PortfolioEntitys portfolio) {
		if (amount > 0 && amount * s.getBuy() < portfolio.getBalance()) {
			System.out.println("BUY: " + amount + " of " + s);
			portfolio.bougthFor(amount * s.getBuy());
			JPAHelper.getInstance().storeObject(new PortfolioHistory(s, s.getTime(), null, amount, portfolio));
			
		}
		return true;
	}

	@Override
	public boolean sellStock(StockPrices s, long amount, PortfolioEntitys portfolio) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean getCourtagePrice(StockPrices s, long amount, boolean buying, PortfolioEntitys portfolio) {
		// TODO Auto-generated method stub
		return true;
	}

}