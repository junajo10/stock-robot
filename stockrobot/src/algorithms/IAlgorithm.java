package algorithms;

import generic.Pair;

import java.util.List;
import java.util.Set;

import portfolio.IPortfolio;
import robot.IRobot_Algorithms;
import trader.ITrader;

import database.jpa.tables.AlgorithmSettingDouble;
import database.jpa.tables.AlgorithmSettingLong;

/**
 * Interface to algorithms
 * 
 * @author daniel
 */
public interface IAlgorithm {
	
	/**
	 * This is the function that the scheduler will call when new stockPrices are avalible
	 * @return Should return true when done
	 */
	boolean update();
	
	/**
	 * Returns the name of this algorithm
	 * @return The name
	 */
	String getName();
	
	/**
	 * Returns a description of the algorithm
	 * @return A string representing a description of the algorithm
	 */
	String getDescription();
	
	
	// Settings-------------------------------
	Set<AlgorithmSettingDouble> getDefaultDoubleSettings();
	Set<AlgorithmSettingLong> getDefaultLongSettings();
	
	boolean giveDoubleSettings(List<Pair<String, Double>> doubleSettings);
	boolean giveLongSettings(List<Pair<String, Long>> longSettings);
	// ---------------------------------------
	
	IAlgorithm createInstance(IRobot_Algorithms robot, IPortfolio portfolio, ITrader trader);
}
