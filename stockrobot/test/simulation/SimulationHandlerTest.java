package simulation;

import java.util.Date;

import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;
import model.simulation.SimulationHandler;

import org.junit.Before;
import org.junit.Test;

import testhelpers.DatabaseCleaner;

/**
 * 
 * @author Daniel
 */
public class SimulationHandlerTest extends DatabaseCleaner {
	
	private static SimulationHandler simulationHandler = null;
	
	@Before
	public void before() { //First of all, create a simulationHandler
		
		simulationHandler = new SimulationHandler();
		StockNames sn = new StockNames("Test stock", "Market");
		
		jpaHelper.storeObject(sn);
		
		for (int i = 0; i < 100; i++) {
			jpaHelper.storeObject(new StockPrices(sn, 10, 10, 10*i, 10*i, new Date((long)i*(long)10000)));
		}
	}
	@Test
	public void test() {
		simulationHandler.clearTestDatabase();
		simulationHandler.simulateAlgorithm("TestAlgorithm1", 100, null, null);
		simulationHandler.clearTestDatabase();
	}
}
