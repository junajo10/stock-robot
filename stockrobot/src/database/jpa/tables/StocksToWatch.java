package database.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Daniel
 *
 * A representation of the StocksToWatch entity.
 * This entity will hold what stocks are beeing watched by a given portfolio
 */
@Entity
@Table(name="StocksToWatch")
public class StocksToWatch {
	@Column @OneToOne
	private PortfolioEntitys portfolio;
	
	@Column
	private StockNames stockName;

	
	public StocksToWatch() {
		
	}
	public StocksToWatch(PortfolioEntitys portfolio, StockNames stockName) {
		this.portfolio = portfolio;
		this.stockName = stockName;
	}
	public PortfolioEntitys getPortfolio() {
		return portfolio;
	}
	public StockNames getStockName() {
		return stockName;
	}
}
