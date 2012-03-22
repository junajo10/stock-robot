package database.jpa;



import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import database.jpa.tables.AlgorithmsTable;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.PortfolioTable;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;

/**
 * @author Daniel
 *
 *This is manily a test class for learning jpa.
 *However it will also initzialise a working state for the JPA System.
 */
public class MainBasicJPATest {
	public static void main(String[] args) {

		JPAHelper jpaHelper = new JPAHelper();
		
		jpaHelper.initJPASystem();

		AlgorithmsTable testAlgorithm = null;
		
		List<AlgorithmsTable> algorithms = jpaHelper.getAllAlgorithms();
		
		if (algorithms.size() == 0) {
			testAlgorithm = new AlgorithmsTable("hej", "algorithms.TestAlgorithm");
			jpaHelper.storeObject(testAlgorithm);
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
			
			if (testAlgorithm != null)
				portfolios.get(0).setAlgorithm(testAlgorithm);
				
			jpaHelper.updateObject(portfolios.get(0));
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
		StockPrices sp = new StockPrices(stockName, r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), new Date(System.currentTimeMillis()));
		jpaHelper.storeObject(sp);
		
		
		PortfolioHistory ph = new PortfolioHistory(sp, new Date(System.currentTimeMillis()), null, 10, portfolios.get(0));
		jpaHelper.storeObject(ph);
		
		//---- Duplicate test
		List<StockPrices> duplicateTest = new LinkedList<StockPrices>();
		Date d = new Date(System.currentTimeMillis()+1000);
		duplicateTest.add(new StockPrices(stockName, r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), d));
		duplicateTest.add(new StockPrices(stockName, r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), d));
		duplicateTest.add(new StockPrices(stockName, r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), d));
		
		System.out.println("#duplicates: " + jpaHelper.storeListOfObjectsDuplicates(duplicateTest));
		//-------------------
		
		List<StockPrices> prices = jpaHelper.getAllStockPrices();
		for (StockPrices s : prices) {
			System.out.println(s);
		}
		
		
		
		List bla = jpaHelper.getCurrentStocks(jpaHelper.getAllPortfolios().get(0));
		System.out.println(bla.size());
		
		jpaHelper.stopJPASystem();
		
	}

}
