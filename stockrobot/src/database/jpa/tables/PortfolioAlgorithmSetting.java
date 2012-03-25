package database.jpa.tables;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PortfolioAlgorithmSetting {
	@Id
	private int id;
	
	
	public int getNumberOfSettings() {
		return 0;
	}
}
