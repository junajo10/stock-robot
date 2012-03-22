package database.jpa.tables;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Daniel
 *
 */
@Entity
@Table(name="PortfolioHistory")
public class PortfolioHistory {

	@Id
	@GeneratedValue
	@Column(name = "PORTFOLIO_HISTORY_ID", nullable = false)
	private int id;
	
	@Column
	private StockPrices stockPrice;
	
	@Column
	private Date buyDate;
	
	@Column
	private Date soldDate;
	
	@Column
	private int amount; 
	
	@ManyToOne(optional=false)
    @JoinColumn(name="portfolioId",referencedColumnName="PORTFOLIO_ID")
    private PortfolioTable portfolio;
	
	public PortfolioHistory() {
		
	}
	public PortfolioHistory(StockPrices stockPrice, Date buyDate, Date soldDate, int amount) {
		this.stockPrice = stockPrice;
		this.buyDate = buyDate;
		this.soldDate = soldDate;
		this.amount = amount;
	}
	public StockPrices getStockPrice() {
		return stockPrice;
	}
	public int getId() {
		return id;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public Date getSoldDate() {
		return soldDate;
	}
	public int getAmount() {
		return amount;
	}
	public boolean stillInPortFolio() {
		return soldDate == null;
	}
	public PortfolioTable getPortfolio() {
		return portfolio;
	}
}
