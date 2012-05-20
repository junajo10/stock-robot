package model.database.jpa.tables;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Daniel
 *
 * A entity class for StockNames.
 */
@Entity
@Table(name="StockNames", uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
public class StockNames {
	@Id 
	@GeneratedValue
	private int id;
	
	@Column(name="name", nullable=false, length=100, insertable=true)
	private String name;
	
	@Column(name="market", nullable=false, length=10, insertable=true)
	private String market;
	
	@OneToMany(targetEntity=StockPrices.class, mappedBy="stockName", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<StockPrices> stockPrices;
	
	@Column
	private boolean simulatedStock;
	
	public StockNames() {
		
	}
	/**
	 * Main constructor for an entity of StockNames
	 * @param name the name of this stock
	 * @param market the market this stock belongs to
	 * @param simulatedStock Set to true if this stock is simulated
	 */
	public StockNames(String name, String market, boolean simulatedStock) {
		this.name = name;
		this.market = market;
		this.simulatedStock = true;
	}
	/**
	 * Will return the id representing this entity
	 * @return An id representing this entity
	 */
	public int getId() {
		return id;
	}
	/**
	 * Will give the name of this stock
	 * @return The name of the stock
	 */
	public String getName() {
		return name;
	}
	/**
	 * Will return the name of the market this stock belongs to
	 * @return The name of the market
	 */
	public String getMarket() {
		return market;
	}
	/**
	 * Will give all the stock entities that belongs to this entity
	 * @return
	 */
	public List<StockPrices> getStockPrices() {
		return stockPrices;
	}
	/**
	 * A simple debug message.
	 */
	public String toString() {
		return name + " | " + market;
	}
	/**
	 * Updates the market this belongs to
	 * @param _market The new representation of the market this belongs to
	 */
	public void setMarket( String _market ) {
		market = _market;
	}
	public void addStockPrice(StockPrices stockPrice) {
		if (stockPrices == null)
			stockPrices = new ArrayList<StockPrices>();
		stockPrices.add(stockPrice);
		
	}
	public boolean isSimulated() {
		return simulatedStock;
	}
}