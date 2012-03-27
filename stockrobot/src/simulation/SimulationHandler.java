package simulation;

import algorithms.IAlgorithm;
import algorithms.loader.AlgorithmsLoader;
import database.jpa.tables.AlgorithmEntitys;

/**
 * @author Daniel
 *
 * This will be the handler for a simulation of an algorithm.
 */
public class SimulationHandler {

	AlgorithmEntitys algorithmToSimulate;
	IAlgorithm algorithm;
	
	public SimulationHandler(AlgorithmEntitys algorithmToSimulate) {
		this.algorithm = AlgorithmsLoader.getInstance(TraderSimulator.getInstance());
	}
}
