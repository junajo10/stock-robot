package simulation;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import database.jpa.IJPAHelper;
import database.jpa.JPAHelper;
import database.jpa.JPAHelperSimulator;
import database.jpa.tables.AlgorithmEntity;

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
		simulationHandler.simulateAlgorithm(new AlgorithmEntity("Algorithm1", "algorithms.TestAlgorithm"), 100, null, null);
		simulationHandler.clearTestDatabase();
	}
}
