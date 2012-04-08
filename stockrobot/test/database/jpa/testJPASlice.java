package database.jpa;


import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import database.jpa.tables.AlgorithmEntity;
import database.jpa.tables.PortfolioEntity;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import database.jpa.tables.StocksToWatch;

/**
 * @author Daniel
 *
 * <property name="openjpa.RuntimeUnenhancedClasses" value="supported"/>
 */
public class testJPASlice {
	static IJPAHelper jpaHelper;
	static Random rand = new Random(System.currentTimeMillis());
	@BeforeClass
	public static void beforeClass(){ //First of all
		jpaHelper = new JPAHelperSimulator();
	}
	
	
	@Test
	public void testApa() {
		// Stored in portfolio database
		AlgorithmEntity a = new AlgorithmEntity("", "");
		jpaHelper.storeObject(a);
		
		// Stored in stock database
		StockNames sn = new StockNames("apa", "bepa");
		jpaHelper.storeObject(sn);
	}
	
	/**
	 * Removes all entitys from the database
	*/
	@AfterClass
	public static void afterClass() {
		
		for (StocksToWatch stw : jpaHelper.getAllStocksToWatch()) {
			jpaHelper.remove(stw);
		}
		
		while (jpaHelper.getAllPortfolios().size() > 0) {
			PortfolioEntity p = jpaHelper.getAllPortfolios().get(0);
			
			jpaHelper.remove(p);
		}
	    for (AlgorithmEntity a : jpaHelper.getAllAlgorithms()) {
			jpaHelper.remove(a);
	    }
		
	    
	    for (StockPrices sp : jpaHelper.getAllStockPrices()) {
	    	jpaHelper.remove(sp);
	    }
		for (StockNames sn : jpaHelper.getAllStockNames()) {
	    	jpaHelper.remove(sn);
		}
	}
	
}