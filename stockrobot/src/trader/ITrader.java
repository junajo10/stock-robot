package trader;

import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.StockPrices;

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
public interface ITrader {

	//TODO Add buy and sell methods.  
	
	
	boolean buyStock(StockPrices s, long amount, PortfolioEntitys portfolio);
	
	boolean sellStock(StockPrices s, long amount, PortfolioEntitys portfolio);
	
	boolean getCourtagePrice(StockPrices s, long amount, boolean buying, PortfolioEntitys portfolio);
}
