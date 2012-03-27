package simulation;

import java.beans.PropertyChangeListener;

import database.jpa.JPAHelper;
import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.StockPrices;
import trader.ITrader;

/**
 * @author Daniel
 *
 */
public class TraderSimulator implements ITrader{

	private static ITrader instance = null;
	
	public static ITrader getInstance() {
		if (instance == null) {
			synchronized (TraderSimulator.class) {
				if (instance == null)
					instance = new TraderSimulator();
			}
		}
		return instance;
	}
	@Override
	public void addAddObserver(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeObserver(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean buyStock(StockPrices s, long amount, PortfolioEntitys portfolio) {
		if (s.getSell()*amount + getCourtagePrice(s, amount, true, portfolio) > portfolio.getBalance())
			return false;
		
		portfolio.bougthFor(s.getSell()*amount + getCourtagePrice(s, amount, true, portfolio));
		JPAHelper.getInstance().storeObject(new PortfolioHistory(s, s.getTime(), null, amount, portfolio));
		return true;
	}

	@Override
	public boolean sellStock(StockPrices s, long amount, PortfolioEntitys portfolio) {
		StockPrices latest = JPAHelper.getInstance().getLatestStockPrice(s);
		portfolio.soldFor(s.getBuy()*amount);
		PortfolioHistory ph = JPAHelper.getInstance().getSpecificPortfolioHistory(s, portfolio);
		
		ph.setSoldDate(latest.getTime());
		
		JPAHelper.getInstance().updateObject(ph);
		
		return true;
	}

	@Override
	public long getCourtagePrice(StockPrices s, long amount, boolean buying, PortfolioEntitys portfolio) {
		return (long) (s.getSell()*amount*0.09);
	}
}
