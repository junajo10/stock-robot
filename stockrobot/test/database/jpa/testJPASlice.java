package database.jpa;


import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioEntitys;
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
		jpaHelper = new JPAHelperForSimulator();
	}
	
	
	@Test
	public void testApa() {
		// Stored in portfolio database
		AlgorithmEntitys a = new AlgorithmEntitys("", "");
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
		for (PortfolioInvestment investment : jpaHelper.getAllPortfolioInvestment()) {
			jpaHelper.remove(investment);
		}
		
		for (StocksToWatch stw : jpaHelper.getAllStocksToWatch()) {
			jpaHelper.remove(stw);
		}
		
		while (jpaHelper.getAllPortfolios().size() > 0) {
			PortfolioEntitys p = jpaHelper.getAllPortfolios().get(0);
			if (p.getHistory() != null) {
				if (p.getHistory().iterator().hasNext()) {
					jpaHelper.remove(p.getHistory().iterator().next());
				}
			}
			
			jpaHelper.remove(p);
		}
	    for (AlgorithmEntitys a : jpaHelper.getAllAlgorithms()) {
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