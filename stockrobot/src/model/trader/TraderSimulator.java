package model.trader;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

import utils.global.FinancialLongConverter;
import utils.global.Log;
import utils.global.Log.TAG;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.StockPrices;

/**
 * @author Daniel
 *
 * A very simple TraderSimulator.
 * It will always say that all trades went ok!
 */
public final class TraderSimulator implements ITrader{
	private static TraderSimulator instance = null;
	private PropertyChangeSupport propertyChangeSuport = new PropertyChangeSupport(this);
	private IJPAHelper jpaHelper = JPAHelper.getInstance();
	
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
			Log.log(Log.TAG.VERBOSE, "Buying " + amount + " of stock: " + s.getStockName().getName() + " for total price of: " + FinancialLongConverter.toDouble(amount * s.getSell()));
			
			portfolio.addPortfolioHistory(new PortfolioHistory(s, s.getTime(), amount, portfolio));
			jpaHelper.updateObject(portfolio);
			
			propertyChangeSuport.firePropertyChange(BUY_STOCK, null, portfolio);
			return true;
		}
		return false;
	}

	@Override
	public boolean sellStock(StockPrices s, long amount, PortfolioEntity portfolio) {
		StockPrices latest = jpaHelper.getLatestStockPrice(s);
		portfolio.soldFor( latest.getBuy()*amount );
		PortfolioHistory ph = portfolio.getSpecificPortfolioHistory(s, amount);
		
		if (ph != null && ph.getSoldDate() == null) {
			ph.setStockSoldPrice(latest);
			ph.setSoldDate(new Date(System.currentTimeMillis()));
			
			jpaHelper.updateObject(ph);
			
			Log.log(Log.TAG.VERBOSE, "Selling " + amount + " of stock: " + s.getStockName().getName() + " for total price of: " + FinancialLongConverter.toDouble(amount * s.getBuy()));
			
			propertyChangeSuport.firePropertyChange(SELL_STOCK, null, portfolio);
		} 
		else
			return false;
		return true;
	}
	@Override
	public boolean sellStock(PortfolioHistory ph, PortfolioEntity portfolio) {
		if (ph.getSoldDate() != null) {
			Log.log(TAG.ERROR, "Couldent sell stock: " + ph + " it already is sold");
			return false;
		}
		Log.log(TAG.VERBOSE, "Selling " + ph.getAmount() + " of " + ph.getStockPrice().getStockName().getName() + " for: " + FinancialLongConverter.toDouble(ph.getStockPrice().getBuy()*ph.getAmount()));
		StockPrices latest = jpaHelper.getLatestStockPrice(ph.getStockPrice());
		portfolio.soldFor(ph.getStockPrice().getBuy()*ph.getAmount(), jpaHelper);
		ph.setSoldDate(latest.getTime());
		jpaHelper.updateObject(portfolio);
		
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
