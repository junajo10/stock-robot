package database.jpa;



import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.PortfolioEntitys;
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

		JPAHelper jpaHelper = JPAHelper.getInstance();
		
		jpaHelper.initJPASystem();

		AlgorithmEntitys testAlgorithm = null;
		
		List<AlgorithmEntitys> algorithms = jpaHelper.getAllAlgorithms();
		
		if (algorithms.size() == 0) {
			testAlgorithm = new AlgorithmEntitys("hej", "algorithms.TestAlgorithm");
			jpaHelper.storeObject(testAlgorithm);
			algorithms = jpaHelper.getAllAlgorithms();
		}
		
		for (AlgorithmEntitys a : algorithms) {
			//a.setName(a.getName() + "1");
			//jpaHelper.updateObject(a);
			System.out.println(a);
			
		}
		
		
		List<PortfolioEntitys> portfolios = jpaHelper.getAllPortfolios();
		
		if (portfolios.size() == 0) {
			jpaHelper.storeObject(new PortfolioEntitys("portfolio 1"));
			portfolios = jpaHelper.getAllPortfolios();
			
			if (testAlgorithm != null)
				portfolios.get(0).setAlgorithm(testAlgorithm);
				
			jpaHelper.updateObject(portfolios.get(0));
		}
		
		for (PortfolioEntitys p : portfolios) {
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
		
		PortfolioHistory ph;
		if (r.nextBoolean())
			ph = new PortfolioHistory(sp, new Date(System.currentTimeMillis()), null, 10, portfolios.get(0));
		else
			ph = new PortfolioHistory(sp, new Date(System.currentTimeMillis()-10000), new Date(System.currentTimeMillis()), 10, portfolios.get(0));
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
		
		
		System.out.println(jpaHelper.getOldStocks((jpaHelper.getAllPortfolios().get(0))).size());
		
		
		
		System.out.println(jpaHelper.getTotalInvestedAmount(jpaHelper.getAllPortfolios().get(0)));
		
		
		System.out.println(jpaHelper.getStockInfo(10).size() + " " + jpaHelper.getStockInfo(10).get(0).getRight().size());
		
		for (StockPrices sp2 : jpaHelper.getStockInfo(10).get(0).getRight()) {
			System.out.println(sp2);
		}
		jpaHelper.stopJPASystem();
		
	}

}
