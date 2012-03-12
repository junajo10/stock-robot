package stock;

import java.util.Date;

import generic.Pair;

public interface IStock {
	int getStockId();
	
	Pair<Date, Integer>[] get10LatestBuyPoints();
	Pair<Date, Integer>[] get100LatestBuyPoints();
	Pair<Date, Integer>[] getNLatestBuyPoints(int n);
	
}
