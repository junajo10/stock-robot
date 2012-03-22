package database.jpa.tables;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="StockNames")
public class StockNames {
	@Id 
	@GeneratedValue
	private int id;
	
	@Column(name="name", nullable=false, length=40, insertable=true)
	private String name;
	
	@Column(name="market", nullable=false, length=10, insertable=true)
	private String market;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<StockPrices> stockPrices;
	
	public StockNames() {
		
	}
	public StockNames(String name, String market) {
		this.name = name;
		this.market = market;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getMarket() {
		return market;
	}
	public List<StockPrices> getStockPrices() {
		return stockPrices;
	}
	public String toString() {
		return name + " | " + market;
	}
}