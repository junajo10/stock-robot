package simulation;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import database.jpa.IJPAHelper;
import database.jpa.JPAHelperForSimulator;
import database.jpa.tables.AlgorithmEntitys;

public class SimulationHandlerTest {
	public static SimulationHandler simulationHandler = null;
	
	static IJPAHelper jpaHelper;
	static Random rand = new Random(System.currentTimeMillis());
	@BeforeClass
	public static void beforeClass(){ //First of all
		jpaHelper = new JPAHelperForSimulator();
		simulationHandler = new SimulationHandler();
	}
	@Test
	public void test() {
		simulationHandler.clearTestDatabase();
		
		simulationHandler.simulateAlgorithm(new AlgorithmEntitys("algorithm1", "algorithms.TestAlgorithm"));
		
		simulationHandler.clearTestDatabase();
	}

}
