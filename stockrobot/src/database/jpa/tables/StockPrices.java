package database.jpa.tables;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.openjpa.persistence.jdbc.Index;


/**
 * @author Daniel
 *
 * A class representation of the StockPrice entity.
 * This is the entity that holds the information about what the selling/buying price was at a given time.
 */
@Entity
@Table(name="StockPrices", uniqueConstraints=@UniqueConstraint(columnNames={"time", "stockName"}))
public class StockPrices {
	@Id
	@GeneratedValue
	private long id;
	
	@Index
	@OneToOne
	@Column(name="stockName")
	private StockNames stockName;
	
	@Column
	private int volume;
	
	@Column
	private long latest;
	
	@Column
	private long buy;
	
	@Column
	private long sell;
	
	@Index
	@Column(name="time")
	private Date time;
	
	public StockPrices() {
		
	}
	public StockPrices(StockNames stockName, int volume, long latest, long buy, long sell, Date time) {
		this.stockName = stockName;
		this.volume = volume;
		this.latest = latest;
		this.buy = buy;
		this.sell = sell;
		this.time = time;
	}
	public StockNames getStockName() {
		return stockName;
	}
	public int getVolume() {
		return volume;
	}
	public long getLatest() {
		return latest;
	}
	public long getBuy() {
		return buy;
	}
	public long getSell() {
		return sell;
	}
	public Date getTime() {
		return time;
	}
	public String toString() {
		return stockName.getName() + " | Buy: " + buy + " | Sell: " + sell + " | Market: " + stockName.getMarket() + " | Time: " + time;
	}
}