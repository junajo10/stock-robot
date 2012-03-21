package database.jpa.tables;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="StockPriceHistory")
public class StockPriceHistory {
	@OneToOne
	private AllStockNames stockName;
	
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
	
	
	public StockPriceHistory() {
		
	}
	public StockPriceHistory(AllStockNames stockName, int id, int volume, int latest, long buy, long sell, Date time) {
		this.stockName = stockName;
		this.volume = volume;
		this.latest = latest;
		this.buy = buy;
		this.sell = sell;
		this.time = time;
	}
}