package database.jpa.tables;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * @author Daniel
 * 
 * A entity with information about money put in, or taken out of a portfolio.
 */
@Embeddable
public class PortfolioInvestment {
	@ManyToOne(cascade = CascadeType.ALL)
	private PortfolioEntity portfolio;
	
	@Column
	private Date date;
	
	@Column
	private long amount;
	
	@Column
	private boolean invested;
	
	public PortfolioInvestment() {
		
	}
	/**
	 * Constructor for PortfolioInvestment
	 * @param portfolio The portfolio this belongs to
	 * @param amount The amount put in / taken out of the portfolio
	 * @param invest If true it will invest money, If false will remove money
	 */
	public PortfolioInvestment(PortfolioEntity portfolio, long amount, boolean invest) {
		this.amount = amount;
		this.portfolio = portfolio;
		
		if (invest) {
			this.invested = true;
		}
		else {
			this.invested = false;
		}
		
		this.date = new Date(System.currentTimeMillis());
	}
	/**
	 * Returns the date when this investment/removal was made
	 * @return the date when this investment/removal was made
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Will give how much was invested/removed
	 * @return amount that was invested/removed
	 */
	public long getAmount() {
		return amount;
	}
	/**
	 * Returns if it was an investment
	 * @return True if investment, False if removal
	 */
	public boolean didInvest() {
		return invested;
	}
	/**
	 * Negation of didInvest
	 * @return negation of didInvest
	 */
	public boolean didRemoveMoney() {
		return !invested;
	}
	/**
	 * Will give back the portfolio that this transaction belongs to
	 * @return A portfolio that this belongs to
	 */
	public PortfolioEntity getPortfolio() {
		return portfolio;
	}
}
