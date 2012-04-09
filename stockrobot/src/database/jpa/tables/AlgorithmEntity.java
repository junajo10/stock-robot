package database.jpa.tables;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import algorithms.IAlgorithm;

/**
 * A entity class representing an algorithm.
 * It has the very important field path, which is where the class file of an algorithm is.
 * 
 * @author Daniel
 */
@Entity
@Table(name="AlgorithmEntity")
public class AlgorithmEntity {
	@Id 
	@GeneratedValue
	private int id;
	
	@Column(name="name", nullable=false, length=40, insertable=true)
	private String name;
	
	@Column(name="path", nullable=true, length=255, insertable=true)
	private String path;
	
	@ElementCollection
    @CollectionTable(name = "longSettings")
    private Set<AlgorithmSettingLong> longSettings = new HashSet<AlgorithmSettingLong>();
	
	
	@ElementCollection
    @CollectionTable(name = "doubleSettings")
    private Set<AlgorithmSettingDouble> doubleSettings = new HashSet<AlgorithmSettingDouble>();
	
	@Column
	private boolean initiaded = false;
	
	
	public AlgorithmEntity() {
		
	}
	/**
	 * The main constructor for an AlgorithmEntity
	 * @param name Name of this algorithm
	 * @param path The Path to the algorithm
	 */
	public AlgorithmEntity(String name, String path) {
		this.name = name;
		this.path = path;
	}
	/**
	 * Will return the id to this entity
	 * @return The id to this entity
	 */
	public int getId() {
		return id;
	}
	/**
	 * Will return the name of this algorithm
	 * @return name of the algorithm
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns the path to the algorithm
	 * @return the path to the algorithm
	 */
	public String getPath() {
		return path;
	}
	/**
	 * Sets a new name for this algorithm
	 * @param name The new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Mainly a debug text representation of an AlgorithmEntity
	 */
	public String toString() {
		return name + " | " + path;
	}
	
	public boolean initiate(IAlgorithm algorithm) {
		if (initiaded) {
			System.out.println("Already Initiated");
			return false;
		}
		for (AlgorithmSettingDouble setting : algorithm.getDefaultDoubleSettings()) {
			addDoubleSetting(new AlgorithmSettingDouble(setting.getName(), setting.getDefaultValue(), setting.getSettingText(), setting.getSettingIndex(), setting.getMinValue(), setting.getMaxValue()));
		}
		for (AlgorithmSettingLong setting : algorithm.getDefaultLongSettings()) {
			addLongSetting(new AlgorithmSettingLong(setting.getName(), setting.getDefaultValue(), setting.getSettingText(), setting.getSettingIndex(), setting.getMinValue(), setting.getMaxValue()));
		}
		System.out.println("Initiated");
		initiaded = true;
		return true;
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
