package simulation;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import database.jpa.IJPAHelper;
import database.jpa.JPAHelper;
import database.jpa.JPAHelperForSimulator;
import database.jpa.tables.AlgorithmEntitys;

public class SimulationHandlerTest {
	private static SimulationHandler simulationHandler = null;
	private static IJPAHelper jpaHelper = null;
	@BeforeClass
	public static void beforeClass(){ //First of all
		jpaHelper = new JPAHelperForSimulator();
		simulationHandler = new SimulationHandler();
	}
	@Test
	public void test() {
		simulationHandler.clearTestDatabase();
		simulationHandler.simulateAlgorithm(new AlgorithmEntitys("Algorithm1", "algorithms.TestAlgorithm"));
		simulationHandler.clearTestDatabase();
	}

}
