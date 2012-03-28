package simulation;

import java.beans.PropertyChangeListener;

import database.jpa.IJPAHelper;
import database.jpa.tables.PortfolioEntitys;
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
	public boolean buyStock(StockPrices s, long amount, PortfolioEntitys portfolio) {
		if (s.getSell()*amount + getCourtagePrice(s, amount, true, portfolio) > portfolio.getBalance())
			return false;
		
		portfolio.bougthFor(s.getSell()*amount + getCourtagePrice(s, amount, true, portfolio), jpaHelper);

		jpaHelper.storeObject(new PortfolioHistory(s, s.getTime(), null, amount, portfolio));
		return true;
	}

	@Override
	public boolean sellStock(StockPrices s, long amount, PortfolioEntitys portfolio) {
		StockPrices latest = jpaHelper.getLatestStockPrice(s);
		portfolio.soldFor(s.getBuy()*amount);
		PortfolioHistory ph = jpaHelper.getSpecificPortfolioHistory(s, portfolio);
		
		ph.setSoldDate(latest.getTime());
		
		jpaHelper.updateObject(ph);
		
		return true;
	}

	@Override
	public long getCourtagePrice(StockPrices s, long amount, boolean buying, PortfolioEntitys portfolio) {
		return (long) (s.getSell()*amount*0.09);
	}
}
