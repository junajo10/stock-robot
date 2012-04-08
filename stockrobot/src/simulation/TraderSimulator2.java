package simulation;

import java.beans.PropertyChangeListener;

import database.jpa.IJPAHelper;
import database.jpa.tables.PortfolioEntity;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.StockPrices;
import trader.ITrader;

/**
 * @author Daniel
 *
 */
public class TraderSimulator2 implements ITrader{

	private IJPAHelper jpaHelper;
	
	public TraderSimulator2(IJPAHelper jpaHelper) {
		this.jpaHelper = jpaHelper;
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
	public boolean buyStock(StockPrices s, long amount, PortfolioEntity portfolio) {
		//System.out.println("Buying stock: " + s + " amount:" + amount);
		if (s.getSell()*amount + getCourtagePrice(s, amount, true, portfolio) > portfolio.getBalance())
			return false;
		
		portfolio.bougthFor(s.getSell()*amount + getCourtagePrice(s, amount, true, portfolio), jpaHelper);

		jpaHelper.updateObject(portfolio);
		
		jpaHelper.storeObject(new PortfolioHistory(s, s.getTime(), null, amount, portfolio));
		return true;
	}

	@Override
	public boolean sellStock(StockPrices s, long amount, PortfolioEntity portfolio) {
		//System.out.println("Selling stock: " + s + " amount:" + amount);
		StockPrices latest = jpaHelper.getLatestStockPrice(s);
		portfolio.soldFor(s.getBuy()*amount, jpaHelper);
		PortfolioHistory ph = jpaHelper.getSpecificPortfolioHistory(s, portfolio, amount);
		
		ph.setSoldDate(latest.getTime());
		
		jpaHelper.updateObject(ph);
		
		jpaHelper.updateObject(portfolio);
		
		return true;
	}

	@Override
	public long getCourtagePrice(StockPrices s, long amount, boolean buying, PortfolioEntity portfolio) {
		return (long) (s.getSell()*amount*0.09);
	}
}
