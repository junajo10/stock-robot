package database.jpa;
import javax.persistence.*;

/**
 * @author Daniel
 *
 */
@Entity
public class AlgorithmsJPA {
	@Id
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
}
