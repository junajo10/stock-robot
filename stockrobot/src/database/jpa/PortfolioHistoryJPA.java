package database.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author Daniel
 *
 */
public class PortfolioHistoryJPA {

	@Id
	private int id;
	
	@Column
	private Date buyDate;
	
	@Column
	private Date soldDate;
	
	@Column
	private int amount;
	
	
	public PortfolioHistoryJPA() {
		
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
}
