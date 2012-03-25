package database.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PortfolioAlgorithmSetting<T> {
	@Id
	private int id;
	
	@Column
	private T value;
	
	@Column
	private T defaultValue;
	
	@Column
	private String settingText;
	
	public PortfolioAlgorithmSetting() {
		
	}
	public PortfolioAlgorithmSetting(T defaultValue, String settingText) {
		this.defaultValue = this.value = defaultValue;
		this.settingText = settingText;
	}
}
