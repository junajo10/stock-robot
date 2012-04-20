package model.database.jpa.tables;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;

/**
 * @author Daniel
 *
 * A class representing PortfolioHistory entity
 */
@Embeddable
public class PortfolioHistory {
	@ManyToOne(cascade=CascadeType.PERSIST)
	private StockPrices stockPrice;
	
	@Column
	private Date buyDate;
	
	@Column
	private Date soldDate;
	
	@Column
	private long amount; 
	
	@ManyToOne(cascade=CascadeType.ALL)
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
		
		@SuppressWarnings("unchecked")
		List<StockPrices> l = em.createQuery("SELECT sp FROM StockPrices sp WHERE sp.time = :tid AND sp.stockName = :namn").setParameter("tid", soldDate).setParameter("namn", stockPrice.getStockName()).getResultList();
		
		if (l.size()>0)
			return l.get(0);
		return null;
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
