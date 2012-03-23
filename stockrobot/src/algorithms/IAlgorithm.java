package algorithms;
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
	/**
	 * Gives the algorithm a new int setting
	 * @param id The id of this setting
	 * @param value The new value of the given setting
	 * @return Returns True if change went ok
	 */
	boolean giveSetting(int id, int value);
	/**
	 * Gives the algorithm a new string setting
	 * @param id The id of this setting
	 * @param value The new value of the given setting
	 * @return Returns True if change went ok
	 */
	boolean giveSetting(int id, String value);
	/**
	 * Gives the algorithm a new double setting
	 * @param id The id of this setting
	 * @param value The new value of the given setting
	 * @return Returns True if change went ok
	 */
	boolean giveSetting(int id, double value);

	/**
	 * A simple method to get the number of settings, for algorithm without settings this should ofcourse give back 0
	 * @return number of settings
	 */
	int getNumberOfSettings();
	
	
	/**
	 * Get the text that should be just before the input box.
	 * @param id Id of the settings
	 * @return A string with the text
	 */
	String getSettingText(int id);
	/**
	 * Gets a string representing what type this setting should be.
	 * @param id Id of the settings
	 * @return A string with the type
	 */
	String getSettingType(int id);
	/**
	 * Gets the valid range for this setting.
	 * @param id Id of the setting
	 * @return The range of this setting
	 */
	String getSettingRange(int id);
	/**
	 * Just gets the default value of a given setting
	 * @param id Id of the setting
	 * @return The default setting.
	 */
	String getSettingDefault(int id);
	
	
	/**
	 * Gets the current int setting of a given setting
	 * @param id The id of the setting
	 * @return the current int setting of a given setting
	 */
	int getCurrentIntSetting(int id);
	/**
	 * Gets the current String setting of a given setting
	 * @param id The id of the setting
	 * @return the current String setting of a given setting
	 */
	String getCurrentStringSetting(int id);
	/**
	 * Gets the current double setting of a given setting
	 * @param id The id of the setting
	 * @return the current double setting of a given setting
	 */
	double getCurrentDoubleSetting(int id);
	//----------------------------------------
	
}
