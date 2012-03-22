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
	public PortfolioInvestment(PortfolioEntitys portfolio, long amount, boolean invest) {
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
	public Date getDate() {
		return date;
	}
	public int getId() {
		return id;
	}
	public long getAmount() {
		return amount;
	}
	public boolean didInvest() {
		return invested;
	}
	public boolean didRemoveMoney() {
		return !invested;
	}
	public PortfolioEntitys getPortfolio() {
		return portfolio;
	}
}
