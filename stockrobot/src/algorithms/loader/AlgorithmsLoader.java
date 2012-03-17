package algorithms.loader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import algorithms.IAlgorithm;
import database.IDatabase;
import portfolio.IPortfolio;
import portfolio.IPortfolioHandler;
import portfolio.PortfolioHandler;
import robot.IRobot_Algorithms;

/**
 * @author Daniel
 *
 * This class will be in charge of loading algorithms
 */

//TODO: Implement with JDO when database system supports it 
public class AlgorithmsLoader {

	IRobot_Algorithms robot;
	IDatabase db;
	List<IAlgorithm> listOfAlgorthms = new LinkedList<IAlgorithm>();
	
	/**
	 * Loads up all algorithms in use by portfolios and stores them in listOfAlgorithms
	 * @param robot Interface to the framework
	 * @param database Interface to the database
	 */
	public AlgorithmsLoader(IRobot_Algorithms robot, IDatabase database) {
		this.robot = robot;
		this.db = database;
		
		IPortfolioHandler portfolioHandler = PortfolioHandler.getInstance();
		
		IPortfolio[] portfolios = portfolioHandler.getPortfolios();
		
		
		for (IPortfolio p : portfolios) {
			ResultSet result = db.askQuery("SELECT path FROM portfolios " +
					"JOIN algorithms ON id = algorithmId" +
					"WHERE portfolioId = " + p.getPortfolioId());
			
			try {
				if (result.next()) {
					String algorithmPath = result.getString("path");
					
					IAlgorithm newAlgorithm = loadAlgorithm(algorithmPath, p);
					
					if (newAlgorithm != null) {
						listOfAlgorthms.add(newAlgorithm);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@SuppressWarnings("rawtypes")
	private IAlgorithm loadAlgorithm(String algortihmPath, IPortfolio portfolio) {
		try {
			Class<?> c = Class.forName(algortihmPath);

			Constructor con = c.getConstructor(new Class[] {IRobot_Algorithms.class, IPortfolio.class });
			
			return (IAlgorithm) con.newInstance(new Object[] {robot, portfolio});
			
			
			
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
		
		int algorithmId = portfolio.getAlgorithmId();
		
		if (algorithmId > 0)
			return loadAlgorithm("algorithms.TestAlgorithm", portfolio);
		
		return null;
	}
	public AlgorithmSettings getSettings(IAlgorithm algorithm) {
		return new AlgorithmSettings(algorithm);
	}
	public List<IAlgorithm> getAlgorithms() {
		return listOfAlgorthms;
	}
}
