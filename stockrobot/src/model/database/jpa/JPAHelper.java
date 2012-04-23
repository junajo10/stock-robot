package model.database.jpa;

import java.util.Map;

import javax.persistence.Persistence;

/**
 * @author Daniel
 *
 * Basically the main JPA system we will use.
 * 
 * It has methods for most of the things we want to accomplish.
 */
public final class JPAHelper extends JPAHelperBase {
	private static JPAHelper instance = null;
	
	/**
	 * Creates an instance of JPAHelper if it doesn't  already exist, and returns the instance of it.
	 * @return An instance of JPAHelper
	 */
	public static IJPAHelper getInstance() {
		synchronized (JPAHelperBase.class) {
			if (instance == null)
				instance = new JPAHelper(); 
		}
		return instance;
	}
	
	/**
	 * Inits the jpa system.
	 */
	private void initJPASystem() {
		Map<Object,Object> map = new java.util.HashMap<Object,Object>();
		factory = Persistence.createEntityManagerFactory("astroportfolio", map);

		em = factory.createEntityManager();
	}
	
	private JPAHelper() {
		initJPASystem();
	}
}
