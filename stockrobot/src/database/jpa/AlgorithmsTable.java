package database.jpa;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Daniel
 *
 */
@Entity
public class AlgorithmsTable {
	@Id @GeneratedValue
	private int id;
	
	@Column
	private String name;
	
	@Column
	private String path;
	
	public AlgorithmsTable() {
		
	}
	public AlgorithmsTable(String name, String path) {
		this.name = name;
		this.path = path;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
}
