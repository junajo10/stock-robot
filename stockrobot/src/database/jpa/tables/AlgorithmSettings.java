package database.jpa.tables;

import generic.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	private String algorithmName;
	
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
	public AlgorithmSettings(String algorithm) {
		this.algorithmName = algorithm;
		this.initiated = false;
	}
	public String getAlgorithmName() {
		return algorithmName;
	}
	public void initiate(IAlgorithm algorithm) {
		if (!initiated) {
			this.doubleSettings = algorithm.getDefaultDoubleSettings();
			this.longSettings = algorithm.getDefaultLongSettings();
		}
		algorithm.giveDoubleSettings(getCurrentDoubleSettings());
		algorithm.giveLongSettings(getCurrentLongSettings());
	}
	private List<Pair<String, Long>> getCurrentLongSettings() {
		List<Pair<String, Long>> currentLongSettings = new ArrayList<Pair<String,Long>>();
		for (AlgorithmSettingLong setting : longSettings) {
			currentLongSettings.add(new Pair<String, Long>(setting.getName(), setting.getValue()));
		}
		return currentLongSettings;
	}
	private List<Pair<String, Double>> getCurrentDoubleSettings() {
		List<Pair<String, Double>> currentDoubleSettings = new ArrayList<Pair<String,Double>>();
		for (AlgorithmSettingDouble setting : doubleSettings) {
			currentDoubleSettings.add(new Pair<String, Double>(setting.getName(), setting.getValue()));
		}
		return currentDoubleSettings;
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
