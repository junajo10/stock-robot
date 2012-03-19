package database.jpa;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Daniel
 *
 */
@Entity
public class PortfolioTable {
	@Id @GeneratedValue
	private int portfolioId;
	
	@Column
	private String name;
	
	@Column
	private int algorithmId;
	
	@Column
	private long balance;
	
	@Column
	private boolean watchAllStocks;
	
	public PortfolioTable() {
		
	}
	public PortfolioTable(String name) {
		this.name = name;
		algorithmId = -1;
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
	public void setAlgorithmId(int algorithmId) {
		this.algorithmId = algorithmId;
	}
}
