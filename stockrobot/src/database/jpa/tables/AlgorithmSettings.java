package database.jpa.tables;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.openjpa.persistence.ElementType;

/**
 * @author Daniel
 *
 * This will hold the settings of a given portfolio's algorithm.
 */
@Entity
public class AlgorithmSettings {
	@Id
	private int id;
	
	@OneToMany(targetEntity=Integer.class)
	private Set<AlgorithmSetting<Integer>> intSettings;
	
	@OneToMany(targetEntity=Double.class)
	@ElementType(Double.class)
	private Set<AlgorithmSetting<Double>> doubleSettings;
	
	@OneToMany(targetEntity=String.class)
	@ElementType(String.class)
	private Set<AlgorithmSetting<String>> stringSettings;
	
	@OneToMany
	private List<AlgorithmSetting<Long>> longSettings;
	
	@Column
	private PortfolioEntity portfolio;
	
	@Column
	private AlgorithmEntity algorithm;
	
	public AlgorithmSettings() {
		
	}
	public AlgorithmSettings(PortfolioEntity portfolio, AlgorithmEntity algorithm) {
		this.portfolio = portfolio;
		this.algorithm = algorithm;
	}
	public PortfolioEntity getPortfolio() {
		return portfolio;
	}
	public AlgorithmEntity getAlgorithm() {
		return algorithm;
	}
	public int getNumberOfSettings() {
		int number = 0;
		if (intSettings != null)
			number += intSettings.size();
		if (doubleSettings != null)
			number += doubleSettings.size();
		if (stringSettings != null)
			number += stringSettings.size();
		return number;
	}
	public void addIntSetting(AlgorithmSetting<Integer> intSetting) {
		this.intSettings.add(intSetting);
	}
	public Set<AlgorithmSetting<Integer>> getIntSettings() {
		return intSettings;
	}
}
