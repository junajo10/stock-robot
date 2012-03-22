package database.jpa.tables;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="StockPrices")
public final class StockPrices {
	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne
	@Column
	private StockNames stockName;
	
	@Column
	private int volume;
	
	@Column
	private int latest;
	
	@Column
	private long buy;
	
	@Column
	private long sell;
	
	@Column
	private Date time;
	
	public StockPrices() {
		
	}
	public StockPrices(StockNames stockName, int id, int volume, int latest, long buy, long sell, Date time) {
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
	public int getLatest() {
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
}