package database.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToOne;


public class StocksToWatch {
	@Id
	private int id;
	@Column @OneToOne
	private PortfolioTable portfolio;
	
	@Column
	private int stockId;
	
	public int getId() {
		return id;
	}
	public PortfolioTable getPortfolio() {
		return portfolio;
	}
	public int getStockId() {
		return stockId;
	}
}
