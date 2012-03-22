package database.jpa;



import java.util.Date;
import java.util.List;
import java.util.Random;

import database.jpa.tables.AlgorithmsTable;
import database.jpa.tables.PortfolioTable;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;


public class MainBasicJPATest {
	public static void main(String[] args) {

		JPAHelper jpaHelper = new JPAHelper();
		
		jpaHelper.initJPASystem();
		
		
		List<AlgorithmsTable> algorithms = jpaHelper.getAllAlgorithms();
		
		if (algorithms.size() == 0) {
			jpaHelper.storeObject(new AlgorithmsTable("hej", "ho"));
			algorithms = jpaHelper.getAllAlgorithms();
		}
		
		for (AlgorithmsTable a : algorithms) {
			//a.setName(a.getName() + "1");
			//jpaHelper.updateObject(a);
			System.out.println(a);
			
		}
		
		
		List<PortfolioTable> portfolios = jpaHelper.getAllPortfolios();
		
		if (portfolios.size() == 0) {
			jpaHelper.storeObject(new PortfolioTable("portfolio 1"));
			portfolios = jpaHelper.getAllPortfolios();
		}
		
		for (PortfolioTable p : portfolios) {
			jpaHelper.investMoney(100, p);
			System.out.println(p);
		}
		StockNames stockName;
		if (jpaHelper.getAllStockNames().size() == 0) {
			stockName = new StockNames("apa", "bepa");
			jpaHelper.storeObject(stockName);
			
		}
		else {
			stockName = jpaHelper.getAllStockNames().get(0); 
		}
		Random r = new Random(System.currentTimeMillis());
		jpaHelper.storeObject(new StockPrices(stockName, r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), new Date(System.currentTimeMillis())));
		
		
		
		
		List<StockPrices> prices = jpaHelper.getAllStockPrices();
		
		for (StockPrices s : prices) {
			System.out.println(s);
		}
		
		jpaHelper.stopJPASystem();
		
	}

}
