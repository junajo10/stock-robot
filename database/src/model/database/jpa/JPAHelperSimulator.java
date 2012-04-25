package model.database.jpa;

import java.util.Map;

import javax.persistence.Persistence;

/**
 * @author Daniel
 * 
 * JPAHelper for the simulator
 */
public class JPAHelperSimulator extends JPAHelperBase{
	public JPAHelperSimulator() {
		Map<Object,Object> map = new java.util.HashMap<Object,Object>();
		factory = Persistence.createEntityManagerFactory("testdb", map);
		this.em = factory.createEntityManager();
	}
}
