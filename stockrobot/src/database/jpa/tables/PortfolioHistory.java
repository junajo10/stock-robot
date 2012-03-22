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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Daniel
 *
 */
@Entity




/*
@NamedQueries({
@NamedQuery(name="getBoughtAndSold",query="SELECT apa,bepa FROM PortfolioHistory ph  " + 
"JOIN StockPrices AS apa ON PortfolioHistory.stockPrice = StockPrices.id " +
"JOIN StockPrices AS bepa ON PortfolioHistory.soldDate = StockPrices.time AND StockPrices.name = apa.name   " +  
"WHERE PortfolioHistory.id = :id AND PortfolioSoldDate != null")
})
*/
/*
SELECT apa,bepa FROM PortfolioHistory ph 
JOIN StockPrices AS apa ON PortfolioHistory.stockPrice = StockPrices.id
JOIN StockPrices AS bepa ON PortfolioHistory.soldDate = StockPrices.time AND StockPrices.name = apa.name  
 
WHERE PortfolioHistory.id = :id AND PortfolioSoldDate != null
 */


@Table(name="PortfolioHistory")
public class PortfolioHistory {

	@Id
	@GeneratedValue
	@Column(name = "PORTFOLIO_HISTORY_ID", nullable = false)
	private int id;
	
	//@Column(name = "stockPrice")
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
    private PortfolioTable portfolio;
	
	public PortfolioHistory() {
		
	}
	public PortfolioHistory(StockPrices stockPrice, Date buyDate, Date soldDate, int amount, PortfolioTable portfolioTable) {
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
	public PortfolioTable getPortfolio() {
		return portfolio;
	}
	public StockPrices getSoldStockPrice(EntityManager em) {
		if (soldDate == null)
			return null;
		
		List<StockPrices> l = em.createQuery("SELECT sp FROM StockPrices sp WHERE sp.time = :tid AND sp.stockName = :namn").setParameter("tid", soldDate).setParameter("namn", stockPrice.getStockName()).getResultList();
		
		return l.get(0);
	}
}
