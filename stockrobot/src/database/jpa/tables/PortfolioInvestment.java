package database.jpa.tables;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Daniel
 * 
 * A entity with information about money put in, or taken out of a portfolio.
 */
@Entity
@Table(name="PortfolioInvested")
public class PortfolioInvestment {
	@Id
	@GeneratedValue
	private int id;
	
	@OneToOne
	private PortfolioEntitys portfolio;
	
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
	public PortfolioInvestment(PortfolioEntitys portfolio, long amount, boolean invest) {
		this.amount = amount;
		this.portfolio = portfolio;
		
		if (invest) {
			this.invested = true;
		}
		else {
			this.invested = false;
		}
		portfolio.invest(amount, invest);
		
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
	 * Gives the id to this entity
	 * @return an int representing this entity
	 */
	public int getId() {
		return id;
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
	public PortfolioEntitys getPortfolio() {
		return portfolio;
	}
}
