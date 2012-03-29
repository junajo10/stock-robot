package database.jpa;



import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.jpa.tables.AlgorithmEntity;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.PortfolioEntity;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import database.jpa.tables.StocksToWatch;

/**
 * @author Daniel
 *
 * This is mainly a test class for learning JPA.
*/
public class MainBasicJPATest {
	private static JPAHelperSimulator jpaHelper;

	@BeforeClass
	public static void beforeClass(){ //First of all
		jpaHelper = new JPAHelperSimulator();
	}
	
	@Test
	public void mainTest() {

		IJPAHelper jpaHelper = JPAHelper.getInstance();
		
		
		List<PortfolioEntity> portfolios = jpaHelper.getAllPortfolios();
		
		if (portfolios.size() == 0) {
			PortfolioEntity portfolio = new PortfolioEntity("portfolio 1");
			jpaHelper.storeObject(portfolio);
			portfolio.setAlgorithm(new AlgorithmEntity("algorithm1", "algorithms.TestAlgorithm"));
			jpaHelper.updateObject(portfolio);

			
			PortfolioEntity portfolio2 = new PortfolioEntity("portfolio 2");
			jpaHelper.storeObject(portfolio2);
			
			portfolio2.setAlgorithm(new AlgorithmEntity("algorithm2", "algorithms.TestAlgorithm2"));
			jpaHelper.updateObject(portfolio2);
			
			portfolios = jpaHelper.getAllPortfolios();
		}
		
		for (PortfolioEntity p : portfolios) {
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
			
			stockName = new StockNames("Stock7", "MarketB");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock8", "MarketB");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock9", "MarketB");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock10", "MarketB");
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
		System.out.println("Current nr of stocks: " + bla.size());
		
		jpaHelper.getAllPortfolios().get(0);
		
		System.out.println(jpaHelper.getOldStocks(jpaHelper.getAllPortfolios().get(0)).size());
		
		
		
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
		
		
		
		System.out.println();
		for (PortfolioHistory phistory :  jpaHelper.getPortfolioHistory(jpaHelper.getAllPortfolios().get(0))) {
			System.out.println(phistory);
			
		}
		System.out.println();
		
		StockPrices stock = new StockPrices(jpaHelper.getAllStockNames().get(0), 123, 123, 123, 123, new Date(123));
		jpaHelper.storeObject(stock);
		
		jpaHelper.storeObject(new PortfolioHistory(stock, new Date(123), new Date(25231434), 77, jpaHelper.getAllPortfolios().get(0)));
		
		PortfolioHistory pHistory = jpaHelper.getSpecificPortfolioHistory(stock, jpaHelper.getAllPortfolios().get(0), 77);
		System.out.println(pHistory);
		List<StockPrices> ble = jpaHelper.getCurrentStocks(jpaHelper.getAllPortfolios().get(0));
		for (StockPrices sp : ble) {
			System.out.println(sp.getTime());
		}
		
		
		jpaHelper.remove(pHistory);
		jpaHelper.remove(stock);
		
		
		
	}

	/**
	 * Removes all entitys from the database
	 */
	@AfterClass
	public static void afterClass() {
		for (PortfolioInvestment investment : jpaHelper.getAllPortfolioInvestment()) {
			System.out.println(investment);
			jpaHelper.remove(investment);
		}
		
		for (StocksToWatch stw : jpaHelper.getAllStocksToWatch()) {
			System.out.println(stw);
			jpaHelper.remove(stw);
		}
		
		while (jpaHelper.getAllPortfolios().size() > 0) {
			PortfolioEntity p = jpaHelper.getAllPortfolios().get(0);
			if (p.getHistory() != null) {
				if (p.getHistory().iterator().hasNext()) {
					jpaHelper.remove(p.getHistory().iterator().next());
				}
			}
			
			jpaHelper.remove(p);
		}
	    for (AlgorithmEntity a : jpaHelper.getAllAlgorithms()) {
			jpaHelper.remove(a);
	    }
		
	    
	    for (StockPrices sp : jpaHelper.getAllStockPrices()) {
	    	System.out.println(sp);
	    	jpaHelper.remove(sp);
	    }
		for (StockNames sn : jpaHelper.getAllStockNames()) {
			System.out.println(sn);
	    	jpaHelper.remove(sn);
		}
		
		jpaHelper.stopJPASystem();
	}
	
}
