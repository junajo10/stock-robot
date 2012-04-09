package trader;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import generic.FinancialLongConverter;
import generic.Log;
import gui.mvc.Constants;
import database.jpa.JPAHelper;
import database.jpa.tables.PortfolioEntity;
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
	public boolean buyStock(StockPrices s, long amount, PortfolioEntity portfolio) {
		if (amount > 0 && amount * s.getSell() < portfolio.getBalance()) {
			
			portfolio.bougthFor( amount * s.getSell() );
			Log.instance().log(Log.TAG.VERBOSE, "Buying " + amount + " of stock: " + s.getStockName().getName() + " for total price of: " + FinancialLongConverter.toDouble(amount * s.getSell()));
			
			portfolio.addPortfolioHistory(new PortfolioHistory(s, s.getTime(), null, amount, portfolio));
			JPAHelper.getInstance().updateObject(portfolio);
			
			propertyChangeSuport.firePropertyChange(Constants.EVENT_TYPE.BUY_STOCK, null, portfolio);
		}
		return true;
	}

	@Override
	public boolean sellStock(StockPrices s, long amount, PortfolioEntity portfolio) {
		StockPrices latest = JPAHelper.getInstance().getLatestStockPrice(s);
		portfolio.soldFor( latest.getBuy()*amount );
		PortfolioHistory ph = portfolio.getSpecificPortfolioHistory(s, amount);
		
		if (ph != null && ph.getSoldDate() == null) {
			ph.setSoldDate(latest.getTime());
			JPAHelper.getInstance().updateObject(ph);
			
			Log.instance().log(Log.TAG.VERBOSE, "Selling " + amount + " of stock: " + s.getStockName().getName() + " for total price of: " + FinancialLongConverter.toDouble(amount * s.getBuy()));
			
			propertyChangeSuport.firePropertyChange(Constants.EVENT_TYPE.SELL_STOCK, null, portfolio);
		} 
		else
			return false;
		return true;
	}

	@Override
	public long getCourtagePrice(StockPrices s, long amount, boolean buying, PortfolioEntity portfolio) {
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
