package simulation;

import java.util.Date;

import junit.framework.Assert;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;
import model.simulation.SimulationHandler;

import org.junit.Before;
import org.junit.Test;

/**
 * This will test the simulationHandler.
 * 
 * @author Daniel
 */
public class SimulationHandlerTest {
	
	private static SimulationHandler simulationHandler = new SimulationHandler();
	private static IJPAHelper jpaHelper = JPAHelper.getInstance();
	
	/**
	 * Before each test we clean the main database.
	 * And insert a couple of simulated stocks with increasing value => simulation will result in a positive outcome.
	 */
	@Before
	public void before() {
		cleanDatabase();
		StockNames sn = new StockNames("Test stock", "Market", true);
		
		jpaHelper.storeObject(sn);
		
		for (int i = 0; i < 100; i++) {
			jpaHelper.storeObject(new StockPrices(sn, 10, 10, 10*i, 10*i, new Date((long)i*(long)10000)));
		}
	}
	/**
	 * Tests algorithm 1
	 */
	@Test
	public void testAlgo1() {
		simulationHandler.clearTestDatabase();
		Assert.assertTrue(simulationHandler.simulateAlgorithm("TestAlgorithm1", 100, null, null) > 0);
		simulationHandler.clearTestDatabase();
	}
	/**
	 * Tests algorithm 2
	 */
	@Test
	public void testAlgo2() {
		simulationHandler.clearTestDatabase();
		Assert.assertTrue(simulationHandler.simulateAlgorithm("TestAlgorithm2", 100, null, null) > 0);
		simulationHandler.clearTestDatabase();
	}
	
	
	private static void cleanDatabase() {
		while( jpaHelper.getAllPortfolios().size() > 0 ) {
			
			PortfolioEntity p = jpaHelper.getAllPortfolios().get( 0 );
			jpaHelper.remove( p );
		}
		
	    for( StockPrices sp : jpaHelper.getAllStockPrices() ) {
	    	
	    	jpaHelper.remove( sp );
	    }
	    
		for( StockNames sn : jpaHelper.getAllStockNames() ) {
			
	    	jpaHelper.remove( sn );
		}
	}
}
