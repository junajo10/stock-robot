package stock;

import generic.Pair;

import java.sql.Timestamp;
import java.util.Date;

public class Stock implements IStock {

	private final int stockId;
	private final Timestamp time;
	
	public Stock(int stockId, Timestamp time) {
		this.stockId = stockId;
		this.time = time;
	}
	@Override
	public int getStockId() {
		return stockId;
	}

	@Override
	public Pair<Date, Integer>[] get10LatestBuyPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<Date, Integer>[] get100LatestBuyPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<Date, Integer>[] getNLatestBuyPoints(int n) {
		// TODO Auto-generated method stub
		return null;
	}


}
