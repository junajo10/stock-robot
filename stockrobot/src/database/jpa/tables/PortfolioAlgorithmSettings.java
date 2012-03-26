package database.jpa.tables;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class PortfolioAlgorithmSettings {
	@Id
	private int id;
	
	@OneToMany
	private List<PortfolioAlgorithmSetting<Integer>> intSettings;
	
	@OneToMany
	private List<PortfolioAlgorithmSetting<Double>> doubleSettings;
	
	@OneToMany
	private List<PortfolioAlgorithmSetting<String>> stringSettings;
	
	@OneToMany
	private List<PortfolioAlgorithmSetting<Long>> longSettings;
	
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
