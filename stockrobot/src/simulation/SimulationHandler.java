package simulation;

import portfolio.IPortfolio;
import algorithms.IAlgorithm;
import algorithms.loader.AlgorithmsLoader;
import database.jpa.tables.AlgorithmEntitys;

/**
 * @author Daniel
 *
 * This will be the handler for a simulation of an algorithm.
 * 
 * It will work by creating a separate database for testing and simply 
 * inject all prices from the original stock price tables.
 * 
 * The result will then be given back to the user, but for now sysout will do.
 */
public class SimulationHandler {

	AlgorithmEntitys algorithmToSimulate;
	IAlgorithm algorithm;
	IPortfolio testPortfolio = null;
	
	public SimulationHandler(AlgorithmEntitys algorithmToSimulate) {
		this.algorithm = AlgorithmsLoader.getInstance(RobotSimulator.getInstance()).loadAlgorithm(testPortfolio);
	}
}
