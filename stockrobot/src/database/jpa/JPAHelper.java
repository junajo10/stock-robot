package database.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import database.jpa.tables.AlgorithmsTable;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.PortfolioTable;

/**
 * @author Daniel
 *
 * Basicly the main JPA system we will use.
 */
public class JPAHelper {
	EntityManager em = null;
	EntityManagerFactory factory;
	public void initJPASystem() {
		java.util.Map<Object,Object> map = new java.util.HashMap<Object,Object>();
		factory = Persistence.createEntityManagerFactory("astroportfolio", map);
		
		em = factory.createEntityManager();
	}
	public void stopJPASystem() {
		em.close();
		factory.close();
	}
	public List<AlgorithmsTable> getAllAlgorithms() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AlgorithmsTable> q2 = cb.createQuery(AlgorithmsTable.class);
        
        Root<AlgorithmsTable> c = q2.from(AlgorithmsTable.class);
        
        q2.select(c);
        
        TypedQuery<AlgorithmsTable> query = em.createQuery(q2);
        return query.getResultList();
	}
	public List<PortfolioTable> getAllPortfolios() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PortfolioTable> q2 = cb.createQuery(PortfolioTable.class);
        
        Root<PortfolioTable> c = q2.from(PortfolioTable.class);
        
        q2.select(c);
        
        TypedQuery<PortfolioTable> query = em.createQuery(q2);
        return query.getResultList();
	}
	public boolean updateObject(Object o) {
		em.getTransaction().begin();
		em.merge(o);
		em.getTransaction().commit();
		return true;
	}
	public boolean storeObject(Object o) {
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
		return true;
	}
	public boolean storeListOfObjects(List<Object> list) {
		em.getTransaction().begin();
		for (Object o : list) {
			em.persist(o);
		}
		em.getTransaction().commit();
		return true;
	}
	public boolean investMoney(long amount, PortfolioTable portfolio) {
		em.getTransaction().begin();
		
		em.persist(new PortfolioInvestment(portfolio, amount, true));
		
		em.getTransaction().commit();
		
		portfolio.invest(amount, true);
		
		updateObject(portfolio);
		
		return true;
	}
}
