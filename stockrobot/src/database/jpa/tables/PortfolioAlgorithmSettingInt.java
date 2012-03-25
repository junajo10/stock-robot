package database.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PortfolioAlgorithmSettingInt {
	@Id
	private int id;
	
	@Column
	private int value;
	
	@Column
	private int defaultValue;
	
	@Column
	private String settingText;
	
}
