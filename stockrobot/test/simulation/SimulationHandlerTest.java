package simulation;

import model.simulation.SimulationHandler;

import org.junit.Before;
import org.junit.Test;

import testhelpers.DatabaseCleaner;

/**
 * 
 * @author ???
 *
 */
public class SimulationHandlerTest extends DatabaseCleaner {
	
	private static SimulationHandler simulationHandler = null;
	
	@Before
	public void before() { //First of all, create a simulationHandler
		
		simulationHandler = new SimulationHandler();
	}
	
	@Test
	public void test() {
		simulationHandler.clearTestDatabase();
		simulationHandler.simulateAlgorithm("TestAlgorithm1", 100, null, null);
		simulationHandler.clearTestDatabase();
	}
}
