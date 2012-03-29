package database.jpa;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.jpa.tables.AlgorithmEntity;
import database.jpa.tables.AlgorithmSetting;
import database.jpa.tables.AlgorithmSettings;
import database.jpa.tables.PortfolioEntity;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import database.jpa.tables.StocksToWatch;

public class testJPAPortfolioSettings {
	static IJPAHelper jpaHelper;
	static Random rand = new Random(System.currentTimeMillis());
	@BeforeClass
	public static void beforeClass(){ //First of all
		jpaHelper = new JPAHelperSimulator();
	}
	
	@Test
	public void testSettings() {
		PortfolioEntity p = new PortfolioEntity("apa");
		jpaHelper.storeObject(p);
		
		AlgorithmEntity a = new AlgorithmEntity("test", "algorithms.TestAlgorithm");
		jpaHelper.storeObject(a);
		
		p.setAlgorithm(a);
		jpaHelper.updateObject(p);
		
		AlgorithmSettings as = new AlgorithmSettings(p, a);
		jpaHelper.storeObject(as);
	
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
	}

}
