package database.jpa;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Daniel
 *
 */
@Entity
@Table(name="PortfolioTable")
public class PortfolioTable {
	@Id @GeneratedValue
	private int portfolioId;
	
	@Column(name="name", nullable=false, length=20, insertable=true)
	private String name;
	
	@OneToOne
	private AlgorithmsTable algorithm;
	
	@Column
	private long balance;
	
	@Column
	private boolean watchAllStocks;
	
	
	
	public PortfolioTable() {
		
	}
	public PortfolioTable(String name) {
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
	public void setAlgorithmId(AlgorithmsTable algorithm) {
		this.algorithm = algorithm;
	}
}
