package database.jpa;

import static org.junit.Assert.*;

import java.util.Random;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import portfolio.Portfolio;

import robot.IRobot_Algorithms;

import algorithms.IAlgorithm;
import database.jpa.tables.AlgorithmSettings;
import database.jpa.tables.PortfolioEntity;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import database.jpa.tables.StocksToWatch;

public class testJPAPortfolioSettings implements IRobot_Algorithms{
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
		
		
		Portfolio portfolio = new Portfolio(p, jpaHelper);
		
		AlgorithmEntity a = new AlgorithmEntity("test", "algorithms.TestAlgorithm");
		jpaHelper.storeObject(a);
		
		p.setAlgorithm(a);
		portfolio.setAlgorithm(a);
		
		jpaHelper.updateObject(p);
		
		AlgorithmsLoader al = AlgorithmsLoader.getInstance(this);
		IAlgorithm algorithm = al.loadAlgorithm(portfolio);
				
		jpaHelper.updateObject(a);
		System.out.println(a.getDoubleSettings().size() + a.getLongSettings().size());
		
		Assert.assertTrue(a.getDoubleSettings().size() + a.getLongSettings().size() > 0);
	}

	/**
	 * Removes all entitys from the database
	 */
	@AfterClass
	public static void afterClass() {
		while (jpaHelper.getAllPortfolios().size() > 0) {
			PortfolioEntity p = jpaHelper.getAllPortfolios().get(0);
			jpaHelper.remove(p);
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

	@Override
	public boolean reportToUser(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IJPAHelper getJPAHelper() {
		return jpaHelper;
	}

}
