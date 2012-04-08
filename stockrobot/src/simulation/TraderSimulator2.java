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
		if (s.getSell()*amount + getCourtagePrice(s, amount, true, portfolio) > portfolio.getBalance())
			return false;

		portfolio.bougthFor(s.getSell()*amount + getCourtagePrice(s, amount, true, portfolio), jpaHelper);

		portfolio.addPortfolioHistory(new PortfolioHistory(s, s.getTime(), null, amount, portfolio));
		jpaHelper.updateObject(portfolio);

		return true;
	}

	@Override
	public boolean sellStock(StockPrices s, long amount, PortfolioEntity portfolio) {
		StockPrices latest = jpaHelper.getLatestStockPrice(s);


		for (PortfolioHistory ph : portfolio.getHistory()) {
			if (ph.getSoldDate() == null) {
				if (ph.getStockPrice().getTime() == s.getTime()) {
					if (ph.getStockPrice().getStockName().getName() == s.getStockName().getName()) {
						if (ph.getAmount() == amount) {
							portfolio.soldFor(s.getBuy()*amount, jpaHelper);
							ph.setSoldDate(latest.getTime());
							jpaHelper.updateObject(portfolio);
							return true;
						}
					}
				}
			}
		}
		System.out.println("error");
		return false;
	}

	@Override
	public long getCourtagePrice(StockPrices s, long amount, boolean buying, PortfolioEntity portfolio) {
		return (long) (s.getSell()*amount*0.09);
	}
}
