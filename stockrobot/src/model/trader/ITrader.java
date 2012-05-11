package model.trader;

import utils.observer.IObservable;
import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.StockPrices;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: ITrader.java
 * Description:
 * Interface for buying and selling stock.
 * This will be used by the algorithms to for 
 * buying new stock or selling old one.
 */
public interface ITrader extends IObservable{

	//Stocks
	public static final String BUY_STOCK 			= "buyStock";
	public static final String SELL_STOCK 			= "sellStock";	
	
	boolean buyStock(StockPrices s, long amount, PortfolioEntity portfolio);
	
	long getCourtagePrice(StockPrices s, long amount, boolean buying, PortfolioEntity portfolio);

	boolean sellStock(PortfolioHistory ph, PortfolioEntity portfolio);
}
