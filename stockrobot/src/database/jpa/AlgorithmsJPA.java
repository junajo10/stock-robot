package database.jpa;
import javax.persistence.*;

/**
 * @author Daniel
 *
 */
@Entity
public class AlgorithmsJPA {
	@Id @GeneratedValue
	private int id;
	
	@Column
	private String name;
	
	@Column
	private String path;
	
	public AlgorithmsJPA() {
		
	}
	public AlgorithmsJPA(String name, String path) {
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
