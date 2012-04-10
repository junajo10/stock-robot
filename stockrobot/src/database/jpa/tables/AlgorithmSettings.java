package database.jpa.tables;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

import algorithms.IAlgorithm;


/**
 * This will hold the settings of a given portfolio's algorithm.
 * 
 * @author Daniel
 */
@Embeddable
public class AlgorithmSettings {
	@Column
	private String name;
	
	@Column
	private boolean initiated = false;
	
	@ElementCollection
    @CollectionTable(name = "longSettings")
    private Set<AlgorithmSettingLong> longSettings = new HashSet<AlgorithmSettingLong>();
	
	
	@ElementCollection
    @CollectionTable(name = "doubleSettings")
    private Set<AlgorithmSettingDouble> doubleSettings = new HashSet<AlgorithmSettingDouble>();
		
	public AlgorithmSettings() {
		
	}
	public String getAlgorithmName() {
		return name;
	}
	public void initiate(IAlgorithm algorithm) {
		if (!initiated) {
			// Add default settings
		}
		else {
			// Set the current settings
		}
	}
	public int getNumberOfSettings() {
		return longSettings.size() + doubleSettings.size();
	}
	public void addLongSetting(AlgorithmSettingLong longSetting) {
		longSettings.add(longSetting);
	}
	public Set<AlgorithmSettingLong> getLongSettings() {
		return longSettings;
	}
	
	public void addDoubleSetting(AlgorithmSettingDouble doubleSetting) {
		doubleSettings.add(doubleSetting);
	}
	public Set<AlgorithmSettingDouble> getDoubleSettings() {
		return doubleSettings;
	}
}
