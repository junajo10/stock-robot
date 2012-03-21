package database.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="StocksToWatch")
public class StocksToWatch {
	@Column @OneToOne
	private PortfolioTable portfolio;
	
	@Column
	private StockNames stockName;

	
	public StocksToWatch() {
		
	}
	public StocksToWatch(PortfolioTable portfolio, StockNames stockName) {
		this.portfolio = portfolio;
		this.stockName = stockName;
	}
	public PortfolioTable getPortfolio() {
		return portfolio;
	}
	public StockNames getStockName() {
		return stockName;
	}
}
