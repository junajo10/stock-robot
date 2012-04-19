package database.jpa.tables;

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
public class AlgorithmSettingLong {
	@Column
	private int settingIndex;
	
	@Column
	private long value;
	
	@Column
	private long defaultValue;
	
	@Column
	private String settingText;
	
	//Change, 2012-04-13: minValue and maxValue seem to be reserved names in MySQL 5.5.9,
	//chaning the name in MySQL to avoid conflicts
	@Column(name="astro_minValue")
	private long minValue;
	
	@Column(name="astro_maxValue")
	private long maxValue;
	
	@Column
	private String name;
		
	public AlgorithmSettingLong() {
		
	}

	/**
	 * Main constructor for an algorithmSettingLong
	 * @param defaultValue The default value
	 * @param settingText The text to this setting
	 * @param settingIndex The index of this setting, giving the location of it
	 * @param minValue The min value of this setting
	 * @param maxValue The max value of this setting
	 */
	public AlgorithmSettingLong(String name, long defaultValue, String settingText, int settingIndex, long minValue, long maxValue) {
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
	public long getValue() {
		return value;
	}
	/**
	 * Sets a new value
	 * @param value the new value
	 */
	public void setValue(long value) {
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
	public long getDefaultValue() {
		return defaultValue;
	}
	
	public long getMinValue() {
		return minValue;
	}
	public long getMaxValue() {
		return maxValue;
	}
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
