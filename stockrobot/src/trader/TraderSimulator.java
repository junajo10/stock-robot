package trader;

import database.jpa.JPAHelper;
import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.StockPrices;

/**
 * @author Daniel
 *
 * A very simple TraderSimulator.
 * It will always say that all trades went ok!
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
		StockPrices latest = JPAHelper.getInstance().getLatestStockPrice(s);
		portfolio.soldFor(s.getBuy()*amount);
		PortfolioHistory ph = JPAHelper.getInstance().getSpecificPortfolioHistory(s, portfolio);
		ph.setSoldDate(latest.getTime());
		JPAHelper.getInstance().updateObject(ph);
		
		System.out.println("Selling: " + amount + " of " + s + " for: " + s.getBuy()*amount);
		
		return true;
	}

	@Override
	public long getCourtagePrice(StockPrices s, long amount, boolean buying, PortfolioEntitys portfolio) {
		return (long) (s.getBuy()*amount*0.1);
	}

}
