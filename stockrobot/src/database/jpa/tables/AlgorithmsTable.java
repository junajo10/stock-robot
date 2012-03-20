package database.jpa.tables;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Daniel
 *
 */
@Entity
@Table(name="AlgorithmsTable")
public class AlgorithmsTable {
	@Id @GeneratedValue
	private int id;
	
	@Column(name="name", nullable=false, length=20, insertable=true)
	private String name;
	
	@Column(name="path", nullable=true, length=255, insertable=true)
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
