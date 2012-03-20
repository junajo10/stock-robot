package database.jpa.tables;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Daniel
 *
 */
@Entity
@Table(name="PortfolioTable")
public class PortfolioTable {
	@Id @GeneratedValue
	@Column(name = "PORTFOLIO_ID", nullable = false)
	private int portfolioId;
	
	@Column(name="name", nullable=false, length=20, insertable=true)
	private String name;
	
	@ManyToOne
	private AlgorithmsTable algorithm;
	
	@Column
	private long balance;
	
	@Column
	private boolean watchAllStocks;
	
	@OneToMany(mappedBy="portfoliotable",targetEntity=PortfolioHistory.class, fetch=FetchType.EAGER)
    private Collection<PortfolioHistory> history;    
	
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
	public void setAlgorithm(AlgorithmsTable algorithm) {
		this.algorithm = algorithm;
	}
	public Collection<PortfolioHistory> getHistory() {
		return history;
	}
	public void invest(EntityManager em, long amount, boolean invest) {
		em.persist(new PortfolioInvestment(this, amount, invest));
	}
}
