package model.database.jpa;

import org.apache.openjpa.persistence.OpenJPAEntityManagerFactory;
import org.apache.openjpa.persistence.OpenJPAPersistence;

/**
 * @author Daniel
 * 
 * JPAHelper for the simulator
 */
public class JPAHelperSimulator extends JPAHelperBase{
	public JPAHelperSimulator() {
		OpenJPAEntityManagerFactory factory = OpenJPAPersistence.createEntityManagerFactory("testdb", "persistence.xml");

		this.em = factory.createEntityManager();
	}
}
