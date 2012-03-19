package database.jpa;


import java.util.*;

import javax.persistence.*;


public class MainBasicJPATest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		java.util.Map<Object,Object> map = new java.util.HashMap<Object,Object>();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("getstarted", map);
		
		
		EntityManager em = factory.createEntityManager();
		
		em.getTransaction().begin();
		// we'll put some codes here later
		

		em.persist(new AlgorithmsTable("TestAlgorithm", "algorithms.TestAlgotihm"));
		
		em.getTransaction().commit();
		
		
		Query q = em.createQuery("select a from AlgorithmsTable a");

        // Go through each of the entities and print out each of their
        // messages, as well as the date on which it was created 
        for (AlgorithmsTable a : (List<AlgorithmsTable>) q.getResultList()) {
            System.out.println("ID: " + a.getId() + " Name: " + a.getName() + " Path: " + a.getPath()); 
        }
		
		em.close();
		
		factory.close();

	}

}
