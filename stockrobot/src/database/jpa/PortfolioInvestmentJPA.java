package database.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class PortfolioInvestmentJPA {
	@Id
	private int id;
	
	
	@Column
	private Date date;
	
	@Column
	private long amount;
	
	@Column
	private boolean invested;
	
	public PortfolioInvestmentJPA() {
		
	}
	public PortfolioInvestmentJPA(long amount, boolean invest) {
		this.amount = amount;
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
}
