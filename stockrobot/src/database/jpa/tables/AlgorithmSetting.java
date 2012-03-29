package database.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author daniel
 *
 * @param <T> The type this setting will have
 * 
 * This Entity will hold settings for algorithms
 */
@Entity
public class AlgorithmSetting<T> {
	@Id
	@GeneratedValue
	private int id;
	
	@Column
	private T value;
	
	@Column
	private T defaultValue;
	
	@Column
	private String settingText;
	
	@Column
	private T minValue;
	
	@Column
	private T maxValue;
	
	@OneToOne
	private AlgorithmSettings settings;
	
	public AlgorithmSetting() {
		
	}
	/**
	 * Main constructor of the entity.
	 * @param defaultValue The default value, this will also be given as the starting value.
	 * @param settingText The text for this setting that will be displayed next to the field for it.
	 */
	public AlgorithmSetting(AlgorithmSettings settings, T defaultValue, String settingText) {
		this.defaultValue = this.value = defaultValue;
		this.settingText = settingText;
	}
	/**
	 * Returns the current value
	 * @return the current value
	 */
	public T getValue() {
		return value;
	}
	/**
	 * Sets a new value
	 * @param value the new value
	 */
	public void setValue(T value) {
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
	public T getDefaultValue() {
		return defaultValue;
	}
	
	public T getMinValue() {
		return minValue;
	}
	public T getMaxValue() {
		return maxValue;
	}
	public String toString() {
		return "SettingText: " + settingText + " Value: " + value; 
	}
}
