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
 * This is mainly a test class for learning JPA.
 * However it will also initialize  a working state for the JPA System.
 */
public class MainBasicJPATest {
	public static void main(String[] args) {

		JPAHelper jpaHelper = JPAHelper.getInstance();
		
		
		List<PortfolioEntitys> portfolios = jpaHelper.getAllPortfolios();
		
		if (portfolios.size() == 0) {
			PortfolioEntitys portfolio = new PortfolioEntitys("portfolio 1");
			jpaHelper.storeObject(portfolio);
			portfolio.setAlgorithm(new AlgorithmEntitys("algorithm1", "algorithms.TestAlgorithm"));
			jpaHelper.updateObject(portfolio);

			
			portfolio = new PortfolioEntitys("portfolio 2");
			jpaHelper.storeObject(portfolio);
			portfolio.setAlgorithm(new AlgorithmEntitys("algorithm2", "algorithms.TestAlgorithm2"));
			jpaHelper.updateObject(portfolio);
			
			portfolios = jpaHelper.getAllPortfolios();
		}
		
		for (PortfolioEntitys p : portfolios) {
			jpaHelper.investMoney(10000000, p);
			System.out.println(p);
		}
		List<StockNames> stockNames = jpaHelper.getAllStockNames();
		if (stockNames.size() == 0) {
			StockNames stockName = new StockNames("Stock1", "MarketA");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock2", "MarketB");
			jpaHelper.storeObject(stockName);
			
			
			stockName = new StockNames("Stock3", "MarketB");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock4", "MarketA");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock5", "MarketB");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock6", "MarketB");
			jpaHelper.storeObject(stockName);
			
			stockNames = jpaHelper.getAllStockNames(); 
		}
		Random r = new Random(System.currentTimeMillis());
		
		// create one stockPrice for each stockName
		for (StockNames stockName : stockNames) {
			StockPrices sp = new StockPrices(stockName, r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), new Date(System.currentTimeMillis()));
			jpaHelper.storeObject(sp);
		}
		
		StockPrices aStock = jpaHelper.getAllStockPrices().get(0);
		
		PortfolioHistory ph;
		if (r.nextBoolean())
			ph = new PortfolioHistory(aStock, new Date(System.currentTimeMillis()), null, 10, portfolios.get(0));
		else
			ph = new PortfolioHistory(aStock, new Date(System.currentTimeMillis()-10000), new Date(System.currentTimeMillis()), 10, portfolios.get(0));
		jpaHelper.storeObject(ph);
		
		//---- Duplicate test
		List<StockPrices> duplicateTest = new LinkedList<StockPrices>();
		Date d = new Date(System.currentTimeMillis()+1000);
		
		duplicateTest.add(new StockPrices(aStock.getStockName(), r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), d));
		duplicateTest.add(new StockPrices(aStock.getStockName(), r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), d));
		duplicateTest.add(new StockPrices(aStock.getStockName(), r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), r.nextInt(1000), d));
		
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
		
		
		
		//-------- Test get latest StockPrice from any stockPrice
		StockPrices old = jpaHelper.getAllStockPrices().get(0);
		StockPrices newestPrice = jpaHelper.getLatestStockPrice(jpaHelper.getAllStockPrices().get(0));
		System.out.println("Given stockPrice: " + old);
		System.out.println("Latest stockPrice: " + newestPrice);
		//------
		
		
		
		jpaHelper.stopJPASystem();
		
	}

}
