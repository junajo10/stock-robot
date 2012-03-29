package database.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Daniel
 *
 * A representation of the StocksToWatch entity.
 * This entity will hold what stocks are being watched by a given portfolio
 */
@Entity
@Table(name="StocksToWatch")
public class StocksToWatch {
	@Column @OneToOne
	private PortfolioEntity portfolio;
	
	@Column
	private StockNames stockName;

	
	public StocksToWatch() {
		
	}
	/**
	 * The default constructor, it takes a PortfolioEntity and a stock name that will be watched.
	 * @param portfolio The portfolio with a new stock to watch
	 * @param stockName The stock to watch
	 */
	public StocksToWatch(PortfolioEntity portfolio, StockNames stockName) {
		this.portfolio = portfolio;
		this.stockName = stockName;
	}
	/**
	 * Just returns its portfolio
	 * @return a PortfolioEntity
	 */
	public PortfolioEntity getPortfolio() {
		return portfolio;
	}
	/**
	 * Just returns is stock to watch
	 * @return returns a StockName
	 */
	public StockNames getStockName() {
		return stockName;
	}
}
