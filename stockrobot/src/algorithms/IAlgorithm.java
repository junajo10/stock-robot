package algorithms;

import java.util.Set;

import database.jpa.tables.AlgorithmSettingDouble;
import database.jpa.tables.AlgorithmSettingLong;

/**
 * @author daniel
 * 
 * Interface to algorithms
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
	
	boolean giveDoubleSettings(Set<AlgorithmSettingDouble> doubleSettings);
	boolean giveLongSettings(Set<AlgorithmSettingLong> longSettings);
	// ---------------------------------------
	
}
