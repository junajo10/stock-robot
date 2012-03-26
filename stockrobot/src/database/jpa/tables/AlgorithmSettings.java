package database.jpa.tables;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.openjpa.persistence.ElementType;

@Entity
public class AlgorithmSettings {
	@Id
	private int id;
	
	@OneToMany
	@ElementType(Integer.class)
	private List<AlgorithmSetting<Integer>> intSettings;
	
	@OneToMany
	@ElementType(Double.class)
	private List<AlgorithmSetting<Double>> doubleSettings;
	
	@OneToMany
	@ElementType(String.class)
	private List<AlgorithmSetting<String>> stringSettings;
	
	@OneToMany
	private List<AlgorithmSetting<Long>> longSettings;
	
	@Column
	private PortfolioEntitys portfolio;
	
	@Column
	private AlgorithmEntitys algorithm;
	
	public AlgorithmSettings() {
		
	}
	public AlgorithmSettings(PortfolioEntitys portfolio, AlgorithmEntitys algorithm) {
		this.portfolio = portfolio;
		this.algorithm = algorithm;
	}
	public PortfolioEntitys getPortfolio() {
		return portfolio;
	}
	public AlgorithmEntitys getAlgorithm() {
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
}
