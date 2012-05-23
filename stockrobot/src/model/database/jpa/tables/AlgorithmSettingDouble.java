package model.database.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author daniel
 *
 * @param <T> The type this setting will have
 * 
 * This Entity will hold settings for algorithms
 */
@Embeddable
public class AlgorithmSettingDouble {
	@Column
	private int settingIndex;
	
	@Column
	private double value;
	
	@Column
	private double defaultValue;
	
	@Column
	private String settingText;
	
	//Change, 2012-04-13: minValue and maxValue seem to be reserved names in MySQL 5.5.9,
	//chaning the name in MySQL to avoid conflicts
	@Column(name="astro_minValue")
	private double minValue;
	
	@Column(name="astro_maxValue")
	private double maxValue;
	
	@Column
	private String name;
	
	public AlgorithmSettingDouble() {
		
	}

	/**
	 * Main constructor for an algorithmSettingDouble
	 * @param defaultValue The default value
	 * @param settingText The text to this setting
	 * @param settingIndex The index of this setting, giving the location of it
	 * @param minValue The min value of this setting
	 * @param maxValue The max value of this setting
	 */
	public AlgorithmSettingDouble(String name, double defaultValue, String settingText, int settingIndex, double minValue, double maxValue) {
		this.name = name;
		this.defaultValue = this.value = defaultValue;
		this.settingText = settingText;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	/**
	 * Returns the current value
	 * @return the current value
	 */
	public double getValue() {
		return value;
	}
	/**
	 * Sets a new value
	 * @param value the new value
	 */
	public void setValue(double value) {
		this.value = value;
	}
	/**
	 * Returns the text for this setting
	 * @return the text for this setting
	 */
	public String getSettingText() {
		return settingText;
	}
	/**
	 * Returns the default value
	 * @return the default value
	 */
	public double getDefaultValue() {
		return defaultValue;
	}
	
	public double getMinValue() {
		return minValue;
	}
	public double getMaxValue() {
		return maxValue;
	}
	@Override
	public String toString() {
		return "SettingText: " + settingText + " Value: " + value + " Index: " + settingIndex; 
	}
	public int getSettingIndex() {
		return settingIndex;
	}
	public String getName() {
		return name;
	}
}
