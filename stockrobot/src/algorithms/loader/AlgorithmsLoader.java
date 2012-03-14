package algorithms.loader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import algorithms.IAlgorithm;
import database.IDatabase;
import portfolio.IPortfolio;
import robot.IRobot_Algorithms;

/**
 * @author daniel
 *
 * This class will be in charge of loading algorithms
 */
public class AlgorithmsLoader {

	IRobot_Algorithms robot;
	IDatabase database;
	List<IAlgorithm> listOfAlgorthms = new LinkedList<IAlgorithm>();
	
	public AlgorithmsLoader(IRobot_Algorithms robot, IDatabase database) {
		this.robot = robot;
		this.database = database;
		
		
		loadAlgorithm("algorithms.TestAlgorithm");
		
		// TODO: If algorithm wants a database and none exists create it
	}
	@SuppressWarnings("rawtypes")
	private IAlgorithm loadAlgorithm(String algortihm) {
		try {
			Class<?> c = Class.forName(algortihm);

			Constructor con = c.getConstructor(new Class[] {IRobot_Algorithms.class, IPortfolio.class });
			
			return (IAlgorithm) con.newInstance(new Object[] {null, null});
			
			
			
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
			System.out.println("sdg");
			return null;
		}
		
		int algorithmId = portfolio.getAlgorithmId();
		
		if (algorithmId > 0)
			return loadAlgorithm("algorithms.TestAlgorithm");
		
		
		return null;
	}
	public AlgorithmSettings getSettings(IAlgorithm algorithm) {
		
		return new AlgorithmSettings(algorithm);
	}
	public List<IAlgorithm> getAlgorithms() {
		return listOfAlgorthms;
	}
}
