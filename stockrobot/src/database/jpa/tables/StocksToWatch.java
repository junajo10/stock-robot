package database.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * A representation of the StocksToWatch entity.
 * This entity will hold what stocks are being watched by a given portfolio
 * 
 * @author Daniel
 */
@Embeddable
public class StocksToWatch {
	@Column
	private StockNames stockName;
	
	public StocksToWatch() {
		
	}
	/**
	 * The default constructor, it takes a stock name that will be watched.
	 * @param stockName The stock to watch
	 */
	public StocksToWatch(StockNames stockName) {
		this.stockName = stockName;
	}
	/**
	 * Just returns is stock to watch
	 * @return returns a StockName
	 */
	public StockNames getStockName() {
		return stockName;
	}
}
