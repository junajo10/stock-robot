package database.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="StocksToWatch")
public class StocksToWatch {
	@Column @OneToOne
	private PortfolioTable portfolio;
	
	@Column
	private int stockId;

	
	public PortfolioTable getPortfolio() {
		return portfolio;
	}
	public int getStockId() {
		return stockId;
	}
}
