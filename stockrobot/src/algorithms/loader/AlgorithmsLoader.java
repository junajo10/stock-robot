package algorithms.loader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import algorithms.IAlgorithm;
import database.jpa.tables.AlgorithmEntitys;
import portfolio.IPortfolio;
import robot.IRobot_Algorithms;

/**
 * @author Daniel
 *
 * This class will be in charge of loading algorithms
 */

public class AlgorithmsLoader {

	IRobot_Algorithms robot;
	private static AlgorithmsLoader instance = null;
	/**
	 * Loads up the algorithmLoaderSystem
	 * @param robot Interface to the framework
	 */
	private AlgorithmsLoader(IRobot_Algorithms robot) {
		this.robot = robot;
	}
	@SuppressWarnings("rawtypes")
	private IAlgorithm loadAlgorithm(String algortihmPath, IPortfolio portfolio) {
		try {
			Class<?> c = Class.forName(algortihmPath);

			Constructor con = c.getConstructor(new Class[] {IRobot_Algorithms.class, IPortfolio.class });
			
			IAlgorithm algorithm = (IAlgorithm) con.newInstance(new Object[] {robot, portfolio});
			
			System.out.println(algorithm.getName() + " is started.");
			
			return algorithm;
			
			//TODO: Report error to GUI
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
		
		return null;
		
	}
	public IAlgorithm loadAlgorithm(IPortfolio portfolio) {
		if (portfolio == null) {
			//TODO: Report error
			return null;
		}
		
		AlgorithmEntitys algorithmTable = portfolio.getAlgorithmTable();
		
		if (algorithmTable != null && algorithmTable.getPath() != null)
			return loadAlgorithm(algorithmTable.getPath(), portfolio);
		
		return null;
	}
	public AlgorithmSettings getSettings(IAlgorithm algorithm) {
		return new AlgorithmSettings(algorithm);
	}
	public static AlgorithmsLoader getInstance(IRobot_Algorithms robot) {
		if(instance == null) {
			instance = new AlgorithmsLoader(robot);
		}
		return instance;
	}
}
