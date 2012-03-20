package database.jpa.tables;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Daniel
 *
 */
@Entity
@Table(name="PortfolioHistory")
public class PortfolioHistory {

	@Id
	private int id;
	
	@Column
	private Date buyDate;
	
	@Column
	private Date soldDate;
	
	@Column
	private int amount;
	
	
	public PortfolioHistory() {
		
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
