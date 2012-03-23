package database.jpa.tables;
import java.util.Collection;
import java.util.List;

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
 * A class representing a PortfolioEntity
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
        
	
	@OneToMany  
	List<StockNames> stocksToWatch;
	public PortfolioEntitys() {
		
	}
	/**
	 * Constructor for a portfolio entity
	 * @param name The name of the new portfolio
	 */
	public PortfolioEntitys(String name) {
		this.name = name;
		algorithm = null;
		balance = 0;
		watchAllStocks = false;
	}
	/**
	 * Returns the name of the portfolio
	 * @return The name of the portfolio
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return The id of this portfolio
	 */
	public int getPortfolioId() {
		return portfolioId;
	}
	/**
	 * @return Will return the current balance of the portfolio
	 */
	public long getBalance() {
		return balance;
	}
	/**
	 * If watch allStocks is set this will return true
	 * @return True if allStocks is set.
	 */
	public boolean watchAllStocks() {
		return watchAllStocks;
	}
	/**
	 * Sets a new name for a portfolio
	 * @param name The new name of the portfolio
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Sets a new algorithm to a given portfolio
	 * @param algorithm
	 */
	public void setAlgorithm(AlgorithmEntitys algorithm) {
		this.algorithm = algorithm;
	}
	/**
	 * Will return the history of this portfolio.
	 * @return A collection of PortfolioHistory that are coupled with this portfolio
	 */
	public Collection<PortfolioHistory> getHistory() {
		return history;
	}
	/**
	 * Investes/removes money
	 * @param amount Amount to add/remove
	 * @param invest If true it will invest, If false will remove
	 */
	public void invest(long amount, boolean invest) {
		this.balance += amount;
	}
	/**
	 * will return a AlgorithmEntity for this portfolio
	 * @return A algorithmEntity
	 */
	public AlgorithmEntitys getAlgorithm() {
		return algorithm;
	}
	/**
	 * @return True if stopBuying flag is set
	 */
	public boolean isStopBuying() {
		return stopBuying;
	}
	/**
	 * Sets the stop buying flag to a given value
	 * @param stopBuying value to set stopBuying flag to
	 */
	public void setStopBuying(boolean stopBuying) {
		this.stopBuying = stopBuying;
	}
	/**
	 * @return True if stopSelling flag is set
	 */
	public boolean isStopSelling() {
		return stopSelling;
	}
	/**
	 * Sets the stopSelling flag to a given value
	 * @param stopSelling value to set stopSelling flag to
	 */
	public void setStopSelling(boolean stopSelling) {
		this.stopSelling = stopSelling;
	}
	/**
	 * A helper method to easy get the text representation.
	 */
	public String toString() {
		return name + " | " + balance;
	}
	/**
	 * Will return a list of StockNames this portfolio is set to watch
	 * @return a list of StockNames this portfolio is set to watch
	 */
	public List<StockNames> getStocksToWatch() {
		return stocksToWatch;
	}
	/**
	 * Helper method that the trader will use to remove money from the portfolio
	 * @param amount amount to remove from the portfolio
	 */
	public void bougthFor(long amount) {
		balance -= amount;
		JPAHelper.getInstance().updateObject(this);
	}
	public void soldFor(long amount) {
		balance -= amount;
		JPAHelper.getInstance().updateObject(this);
	}
}
