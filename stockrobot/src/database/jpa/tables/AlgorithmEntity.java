package database.jpa.tables;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Daniel
 * A entity class representing an algorithm.
 * It has the very important field path, which is where the class file of an algorithm is.
 */
@Entity
@Table(name="AlgorithmEntity")
public class AlgorithmEntity {
	@Id 
	@GeneratedValue
	private int id;
	
	@Column(name="name", nullable=false, length=40, insertable=true)
	private String name;
	
	@Column(name="path", nullable=true, length=255, insertable=true)
	private String path;
	
	public AlgorithmEntity() {
		
	}
	/**
	 * The main constructor for an AlgorithmEntity
	 * @param name Name of this algorithm
	 * @param path The Path to the algorithm
	 */
	public AlgorithmEntity(String name, String path) {
		this.name = name;
		this.path = path;
	}
	/**
	 * Will return the id to this entity
	 * @return The id to this entity
	 */
	public int getId() {
		return id;
	}
	/**
	 * Will return the name of this algorithm
	 * @return name of the algorithm
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns the path to the algorithm
	 * @return the path to the algorithm
	 */
	public String getPath() {
		return path;
	}
	/**
	 * Sets a new name for this algorithm
	 * @param name The new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Mainly a debug text representation of an AlgorithmEntity
	 */
	public String toString() {
		return name + " | " + path;
	}
}
