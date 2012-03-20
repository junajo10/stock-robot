package database.jpa;



import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import database.jpa.tables.AlgorithmsTable;
import database.jpa.tables.PortfolioTable;


public class MainBasicJPATest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		java.util.Map<Object,Object> map = new java.util.HashMap<Object,Object>();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("getstarted", map);
		
		
		EntityManager em = factory.createEntityManager();
		
		em.getTransaction().begin();
		// we'll put some codes here later
		
		
		PortfolioTable portfolio = new PortfolioTable("portfolio 1");
		
		AlgorithmsTable algorithm = new AlgorithmsTable("TestAlgorithm", "algorithms.TestAlgotihm");
		
		em.persist(algorithm);
		em.persist(portfolio);
		
		
		portfolio.setAlgorithm(algorithm);
		
		portfolio.invest(em, 10000, true);
		
		
		
		em.getTransaction().commit();
		
		/*
		Query q = em.createQuery("select a from AlgorithmsTable a");

        for (AlgorithmsTable a : (List<AlgorithmsTable>) q.getResultList()) {
            System.out.println("ID: " + a.getId() + " Name: " + a.getName() + " Path: " + a.getPath()); 
        }
		*/
		em.close();
		
		factory.close();

	}

}
