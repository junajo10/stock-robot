package model.portfolio;


import java.util.List;

import utils.global.Pair;

import model.database.jpa.tables.AlgorithmSettingDouble;
import model.database.jpa.tables.AlgorithmSettingLong;



/**
 * Interface to algorithms
 * 
 * @author Daniel
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
	/**
	 * From this method we generate all default values when an algortihm first gets hooked to a portfolio.
	 * 
	 * @return A list of AlgorithmSettingDouble
	 */
	List<AlgorithmSettingDouble> getDefaultDoubleSettings();
	
	/**
	 * From this method we generate all default values when an algortihm first gets hooked to a portfolio.
	 * 
	 * @return A list of AlgorithmSettingLong
	 */
	List<AlgorithmSettingLong> getDefaultLongSettings();
	
	/**
	 * Gives the algorithm a setting values.
	 * @param doubleSettings List of settings
	 * @return Should return true if all settings where ok
	 */
	boolean giveDoubleSettings(List<Pair<String, Double>> doubleSettings);
	
	/**
	 * Gives the algorithm a setting values.
	 * @param longSettings List of settings
	 * @return Should return true if all settings where ok
	 */
	boolean giveLongSettings(List<Pair<String, Long>> longSettings);
	// ---------------------------------------
	
	/**
	 * Sets the coupling to the robot, is uses this to hook up to the trader and JPA system
	 * @param robot Interface to ASTRo
	 */
	void setRobot(IRobot_Algorithms robot);

	/**
	 * Sets wich portfolio this algorithm belongs to
	 * @param portfolio The portfolio this algortihm should belong to
	 */
	void setPortfolio(IPortfolio portfolio);
}
