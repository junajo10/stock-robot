package simulation;

import java.beans.PropertyChangeListener;

import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.StockPrices;
import trader.ITrader;

/**
 * @author Daniel
 *
 */
public class TraderSimulator implements ITrader{

	private static TraderSimulator instance = null;
	
	public static TraderSimulator getInstance() {
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
	public boolean buyStock(StockPrices s, long amount,
			PortfolioEntitys portfolio) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sellStock(StockPrices s, long amount,
			PortfolioEntitys portfolio) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getCourtagePrice(StockPrices s, long amount, boolean buying,
			PortfolioEntitys portfolio) {
		// TODO Auto-generated method stub
		return 0;
	}
}
