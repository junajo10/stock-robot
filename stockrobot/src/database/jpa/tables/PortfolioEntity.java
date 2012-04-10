package database.jpa.tables;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import database.jpa.IJPAHelper;
import database.jpa.JPAHelper;

/**
 *  A class representing a Portfolio Entity
 *  
 *  @author Daniel
 */
@Entity
@Table(name="PortfolioEntity")
public class PortfolioEntity {
	@Id
	@GeneratedValue
	@Column(name = "PORTFOLIO_ID", nullable = false)
	private int portfolioId;
	
	@Column
	private String name;
	
	@Column
	private Long balance;
	
	@Column
	private boolean watchAllStocks;
	
	@Column
	private boolean stopBuying;
	
	@Column
	private boolean stopSelling;
	
	@ElementCollection
    @CollectionTable(name = "history")
    private Set<PortfolioHistory> history = new HashSet<PortfolioHistory>();
        

	@ElementCollection
    @CollectionTable(name = "investments")
    private Set<PortfolioInvestment> investments = new HashSet<PortfolioInvestment>();
    
	
	@ElementCollection
    @CollectionTable(name = "stocksToWatch")
	private Set<StocksToWatch> stocksToWatch = new HashSet<StocksToWatch>();
	
	@Embedded
	private AlgorithmSettings algorithmSettings;
	
	public PortfolioEntity() {
		
	}
	/**
	 * Constructor for a portfolio entity
	 * @param name The name of the new portfolio
	 */
	public PortfolioEntity(String name) {
		this.name = name;
		algorithmSettings = new AlgorithmSettings();
		
		balance = (long)0;
		watchAllStocks = false;
		algorithmSettings = null;
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
	 * Will clear all settings and give the default settings of the given algorithm
	 * @param algorithm
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithmSettings = new AlgorithmSettings(algorithm);

	}
	/**
	 * Will return the history of this portfolio.
	 * @return A collection of PortfolioHistory that are coupled with this portfolio
	 */
	public Set<PortfolioHistory> getHistory() {
		return history;
	}
	
	public void addPortfolioHistory(PortfolioHistory ph) {
		this.history.add(ph);
	}
	/**
	 * Invests/removes money
	 * @param amount Amount to add/remove
	 * @param invest If true it will invest, If false will remove
	 */
	public void invest(long amount, boolean invest) {
		this.investments.add(new PortfolioInvestment(this,amount, invest));
		this.balance += amount;
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
		List<StockNames> stocks = new ArrayList<StockNames>();
		for (StocksToWatch stw : stocksToWatch) {
			stocks.add(stw.getStockName());
		}
		return stocks;
	}
	/**
	 * Helper method that the trader will use to remove money from the portfolio
	 * @param amount amount to remove from the portfolio
	 */
	public void bougthFor(long amount) {
		balance -= amount;
		JPAHelper.getInstance().updateObject(this);
	}
	/**
	 * Helper method that the trader will use to remove money from the portfolio
	 * @param amount amount to remove from the portfolio
	 */
	public void bougthFor(long amount, IJPAHelper jpaHelper) {
		balance -= amount;
		jpaHelper.updateObject(this);
	}
	public void soldFor(long amount) {
		balance += amount;
		JPAHelper.getInstance().updateObject(this);
	}
	public void soldFor(long amount, IJPAHelper jpaHelper) {
		balance += amount;
		jpaHelper.updateObject(this);
	}
	public PortfolioHistory getSpecificPortfolioHistory(StockPrices s, long amount) {
		for (PortfolioHistory ph : history) {
			if (ph.getBuyDate().equals(s.getTime()) && ph.getAmount() == amount)
				return ph;
		}
		return null;
	}
	public long getTotalInvestedAmount() {
		long result = 0;
		
		for (PortfolioInvestment pi : investments) {
			if (pi.didInvest())
				result += pi.getAmount();
			else
				result -= pi.getAmount();
		}
		return result;
	}
	public boolean addStockToWatch(StockNames stockName) {
		stocksToWatch.add(new StocksToWatch(stockName));
		return true;
	}
	public boolean removeStockToWatch(StockNames stockName) {
		return stocksToWatch.remove(stockName);
	}
	public AlgorithmSettings getAlgortihmSettings() {
		return algorithmSettings;
	}
}
