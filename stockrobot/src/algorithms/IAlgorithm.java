package algorithms;
/**
 * 
 * @author daniel
 * 
 * Interface to algorithms
 */
public interface IAlgorithm {
	
	boolean update(); // Returns true when done
	
	String getName();
	String getDescription();
	
	
	// Settings-------------------------------
	boolean giveSetting(int id, int value);
	boolean giveSetting(int id, String value);
	boolean giveSetting(int id, double value);

	int getNumberOfSettings();
	String getSettingText(int id);
	String getSettingType(int id);
	String getSettingRange(int id);
	String getSettingDefault(int id);
	
	
	int getCurrentIntSetting(int id);
	String getCurrentStringSetting(int id);
	double getCurrentDoubleSetting(int id);
	//----------------------------------------
	
}
