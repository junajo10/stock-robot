package simulation;


import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelperSimulator;
import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;
import model.simulation.SimulationHandler;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class SimulationHandlerTest {
	private static SimulationHandler simulationHandler = null;
	private static IJPAHelper jpaHelper = null;
	@BeforeClass
	public static void beforeClass(){ //First of all
		jpaHelper = new JPAHelperSimulator();
		simulationHandler = new SimulationHandler();
	}
	@Test
	public void test() {
		simulationHandler.clearTestDatabase();
		simulationHandler.simulateAlgorithm("TestAlgorithm1", 100, null, null);
		simulationHandler.clearTestDatabase();
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
}
