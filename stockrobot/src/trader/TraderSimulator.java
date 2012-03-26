package trader;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import generic.FinancialLongConverter;
import generic.Log;
import gui.mvc.Constants;
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
	private PropertyChangeSupport propertyChangeSuport = new PropertyChangeSupport(this);
	
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
		
		if (amount > 0 && amount * FinancialLongConverter.toDouble(s.getSell()) < portfolio.getBalance()) {
			
			portfolio.bougthFor(
					FinancialLongConverter.fromDouble(
							amount * FinancialLongConverter.toDouble(s.getSell())
					)
			);
			Log.instance().log(Log.TAG.VERY_VERBOSE, "total price: " + amount * FinancialLongConverter.toDouble(s.getSell()));
			
			JPAHelper.getInstance().storeObject(new PortfolioHistory(s, s.getTime(), null, amount, portfolio));
			
			propertyChangeSuport.firePropertyChange(Constants.EVENT_TYPE.BUY_STOCK, null, portfolio);
		}
		return true;
	}

	@Override
	public boolean sellStock(StockPrices s, long amount, PortfolioEntitys portfolio) {
		StockPrices latest = JPAHelper.getInstance().getLatestStockPrice(s);
		portfolio.soldFor(
				
			FinancialLongConverter.fromDouble(
					FinancialLongConverter.toDouble(s.getBuy())*amount
			)
				
		);
		PortfolioHistory ph = JPAHelper.getInstance().getSpecificPortfolioHistory(s, portfolio);
		ph.setSoldDate(latest.getTime());
		JPAHelper.getInstance().updateObject(ph);
		
		System.out.println("Selling: " + amount + " of " + s + " for: " + s.getBuy()*amount);
		
		propertyChangeSuport.firePropertyChange(Constants.EVENT_TYPE.SELL_STOCK, null, portfolio);
		
		return true;
	}

	@Override
	public long getCourtagePrice(StockPrices s, long amount, boolean buying, PortfolioEntitys portfolio) {
		return (long) (s.getSell()*amount*0.1);
	}
	@Override
	public void addAddObserver(PropertyChangeListener listener) {

		propertyChangeSuport.addPropertyChangeListener(listener);
	}
	@Override
	public void removeObserver(PropertyChangeListener listener) {
		
		propertyChangeSuport.removePropertyChangeListener(listener);

	}

}
