package database.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AllStockNames")
public class AllStockNames {
	@Id 
	@GeneratedValue
	private int id;
	
	@Column(name="name", nullable=false, length=20, insertable=true)
	private String name;
	
	@Column(name="name", nullable=false, length=10, insertable=true)
	private String market;
	
	public AllStockNames() {
		
	}
	public AllStockNames(String name, String market) {
		this.name = name;
		this.market = market;
	}
}