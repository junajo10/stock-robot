package simulation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import portfolio.IPortfolio;
import portfolio.Portfolio;
import robot.IRobot_Algorithms;
import trader.ITrader;
import trader.TraderSimulator;
import algorithms.IAlgorithm;
import algorithms.loader.AlgorithmsLoader;
import database.jpa.JPAHelper;
import database.jpa.JPAHelperForSimulator;
import database.jpa.JPAHelperForUnderstanding;
import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioEntitys;

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
	JPAHelper jpaHelper = JPAHelper.getInstance();
	JPAHelperForSimulator jpaSimHelper = new JPAHelperForSimulator();
	PortfolioEntitys testPortfolio;
	IPortfolio portfolio = null;
	IRobot_Algorithms robotSim = new RobotSimulator();
	
	public SimulationHandler(AlgorithmEntitys algorithmToSimulate) {
		this.algorithmToSimulate = algorithmToSimulate;
		
		PortfolioEntitys portfolioEntity = new PortfolioEntitys("Simulated Portfolio");
		portfolioEntity.setAlgorithm(algorithmToSimulate);
		jpaSimHelper.storeObject(portfolioEntity);
		
		portfolio = new Portfolio(portfolioEntity);
		
		try {
			Class<?> c = Class.forName(algorithmToSimulate.getPath());
			Constructor con = c.getConstructor(new Class[] {IRobot_Algorithms.class, IPortfolio.class, ITrader.class });
			algorithm = (IAlgorithm) con.newInstance(new Object[] {robotSim, portfolio, TraderSimulator.getInstance()});
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (ClassNotFoundException e) {
		} catch (NoSuchMethodException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}
	public void simulateAlgorithm() {
		
	}
}
