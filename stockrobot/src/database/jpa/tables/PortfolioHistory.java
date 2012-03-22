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
	private int amount; 
	
	@ManyToOne
    @JoinColumn(name="portfolioId",referencedColumnName="PORTFOLIO_ID")
    private PortfolioEntitys portfolio;
	
	public PortfolioHistory() {
		
	}
	/**
	 * The constuctor for a PortfolioHistory entity.
	 * @param stockPrice
	 * @param buyDate
	 * @param soldDate
	 * @param amount
	 * @param portfolioTable
	 */
	public PortfolioHistory(StockPrices stockPrice, Date buyDate, Date soldDate, int amount, PortfolioEntitys portfolioTable) {
		this.stockPrice = stockPrice;
		this.buyDate = buyDate;
		this.soldDate = soldDate;
		this.amount = amount;
		this.portfolio = portfolioTable;
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
	public PortfolioEntitys getPortfolio() {
		return portfolio;
	}
	public StockPrices getSoldStockPrice(EntityManager em) {
		if (soldDate == null)
			return null;
		
		List<StockPrices> l = em.createQuery("SELECT sp FROM StockPrices sp WHERE sp.time = :tid AND sp.stockName = :namn").setParameter("tid", soldDate).setParameter("namn", stockPrice.getStockName()).getResultList();
		
		return l.get(0);
	}
}
