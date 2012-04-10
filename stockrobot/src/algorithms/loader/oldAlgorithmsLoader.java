package algorithms.loader;

import generic.Log;
import generic.Log.TAG;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import algorithms.IAlgorithm;
import portfolio.IPortfolio;
import robot.IRobot_Algorithms;
import trader.ITrader;
import trader.TraderSimulator;

/**
 * This class will be in charge of loading algorithms
 * @author Daniel
 */

public class oldAlgorithmsLoader {

	IRobot_Algorithms robot;
//	private static AlgorithmsLoader instance = null;
	/**
	 * Loads up the algorithmLoaderSystem
	 * @param robot Interface to the framework
	 */
	private oldAlgorithmsLoader(IRobot_Algorithms robot) {
		this.robot = robot;
	}
	@SuppressWarnings("rawtypes")
	private IAlgorithm loadAlgorithm(String algortihmPath, IPortfolio portfolio) {
		try {
			Class<?> c = Class.forName(algortihmPath);

			Constructor con = c.getConstructor(new Class[] {IRobot_Algorithms.class, IPortfolio.class, ITrader.class });
			
			IAlgorithm algorithm = (IAlgorithm) con.newInstance(new Object[] {robot, portfolio, TraderSimulator.getInstance()});
			
			Log.instance().log(TAG.VERBOSE, algorithm.getName() + " is started.");
			
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
	/**
	 * Loads the algorithm in a given portfolio, it will also initiate its settings. 
	 * @param portfolio 
	 * @return
	 */
	/*public IAlgorithm loadAlgorithm(IPortfolio portfolio) {
		if (portfolio == null) {
			Log.instance().log(TAG.ERROR, "portfolio is null");
			return null;
		}
		
		AlgorithmEntity algorithmTable = portfolio.getAlgorithmTable();
		IAlgorithm algorithm = null;
		if (algorithmTable != null && algorithmTable.getPath() != null)
			algorithm = loadAlgorithm(algorithmTable.getPath(), portfolio);
		
		if (algorithm != null) {
			boolean firstTime = algorithmTable.initiate(algorithm);
			
			if (!firstTime) {
				algorithm.giveDoubleSettings(algorithmTable.getCurrentDoubleSettings());
				algorithm.giveLongSettings(algorithmTable.getCurrentLongSettings());
			}
		}
		return algorithm;
	}
	public static AlgorithmsLoader getInstance(IRobot_Algorithms robot) {
		if(instance == null) {
			synchronized (AlgorithmsLoader.class) {
				if (instance == null)
					instance = new AlgorithmsLoader(robot);
			}
		}
		return instance;
	}
	*/
}
