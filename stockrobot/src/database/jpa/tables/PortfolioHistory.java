package database.jpa.tables;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Daniel
 *
 * A class representing PortfolioHistory entity
 */
@Entity
@Table(name="PortfolioHistory")
public class PortfolioHistory {

	@Id
	@GeneratedValue
	@Column(name = "PORTFOLIO_HISTORY_ID", nullable = false)
	private int id;
	
	@ManyToOne
	private StockPrices stockPrice;
	
	@Column
	private Date buyDate;
	
	@Column
	private Date soldDate;
	
	@Column
	private long amount; 
	
	@ManyToOne
    @JoinColumn(name="portfolioId",referencedColumnName="PORTFOLIO_ID")
    private PortfolioEntity portfolio;
	
	public PortfolioHistory() {
		
	}
	/**
	 * The constructor for a PortfolioHistory entity.
	 * @param stockPrice
	 * @param buyDate
	 * @param soldDate
	 * @param amount
	 * @param portfolioTable
	 */
	public PortfolioHistory(StockPrices stockPrice, Date buyDate, Date soldDate, long amount, PortfolioEntity portfolioTable) {
		this.stockPrice = stockPrice;
		this.buyDate = buyDate;
		this.soldDate = soldDate;
		this.amount = amount;
		this.portfolio = portfolioTable;
	}
	/**
	 * @return The stockPrice at buying time
	 */
	public StockPrices getStockPrice() {
		return stockPrice;
	}
	/**
	 * Returns the stockPrice at selling time
	 * @param em 
	 * @return the stockPrice at selling time
	 */
	public StockPrices getSoldStockPrice(EntityManager em) {
		if (soldDate == null)
			return null;
		
		List<StockPrices> l = em.createQuery("SELECT sp FROM StockPrices sp WHERE sp.time = :tid AND sp.stockName = :namn").setParameter("tid", soldDate).setParameter("namn", stockPrice.getStockName()).getResultList();
		
		if (l.size()>0)
			return l.get(0);
		return null;
	}
	/**
	 * @return The id of this portfolioHistory
	 */
	public int getId() {
		return id;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public Date getSoldDate() {
		return soldDate;
	}
	/**
	 * @return The amount of stocks in this entity
	 */
	public long getAmount() {
		return amount;
	}
	public boolean stillInPortFolio() {
		return soldDate == null;
	}
	public PortfolioEntity getPortfolio() {
		return portfolio;
	}
	public void setSoldDate(Date soldDate) {
		this.soldDate = soldDate;
	}
	public String toString() {
		return "BuyDate: " + buyDate + "SoldDate: " + soldDate + " Stock: " + stockPrice;
	}
	
}
