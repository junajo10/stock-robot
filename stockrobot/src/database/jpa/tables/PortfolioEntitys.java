package database.jpa.tables;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import database.jpa.JPAHelper;

/**
 * @author Daniel
 *
 */
@Entity
@Table(name="PortfolioEntitys")
public class PortfolioEntitys {
	@Id 
	@GeneratedValue
	@Column(name = "PORTFOLIO_ID", nullable = false)
	private int portfolioId;
	
	@Column(name="name", nullable=false, length=20, insertable=true)
	private String name;
	
	@ManyToOne
	@Column(name = "algorithm")
	private AlgorithmEntitys algorithm;
	
	@Column
	private long balance;
	
	@Column
	private boolean watchAllStocks;
	
	@Column
	private boolean stopBuying;
	
	@Column
	private boolean stopSelling;
	
	
	@OneToMany(mappedBy="portfolio",targetEntity=PortfolioHistory.class, fetch=FetchType.EAGER)
    private Collection<PortfolioHistory> history;
        
	/*@OneToMany  
	Set<PortfolioHistory> history;  
	*/
	
	@OneToMany  
	List<StockNames> stocksToWatch;
	public PortfolioEntitys() {
		
	}
	public PortfolioEntitys(String name) {
		this.name = name;
		algorithm = null;
		balance = 0;
		watchAllStocks = false;
	}
	
	public String getName() {
		return name;
	}
	public int getPortfolioId() {
		return portfolioId;
	}
	public long getBalance() {
		return balance;
	}
	public boolean watchAllStocks() {
		return watchAllStocks;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAlgorithm(AlgorithmEntitys algorithm) {
		this.algorithm = algorithm;
	}
	public Collection<PortfolioHistory> getHistory() {
		return history;
	}
	public void invest(long amount, boolean invest) {
		this.balance += amount;
	}
	public AlgorithmEntitys getAlgorithm() {
		return algorithm;
	}
	public boolean isStopBuying() {
		return stopBuying;
	}
	public void setStopBuying(boolean stopBuying) {
		this.stopBuying = stopBuying;
	}
	public boolean isStopSelling() {
		return stopSelling;
	}
	public void setStopSelling(boolean stopSelling) {
		this.stopSelling = stopSelling;
	}
	public String toString() {
		return name + " | " + balance;
	}
	public List<StockNames> getStocksToWatch() {
		return stocksToWatch;
	}
	public void bougthFor(long l) {
		balance -= l;
		JPAHelper.getInstance().updateObject(this);
	}
}
