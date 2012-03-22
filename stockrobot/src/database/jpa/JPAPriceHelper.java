package database.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;

/**
 * @author Daniel
 *
 * Basicly the main JPA system we will use.
 */
public class JPAPriceHelper {
	EntityManager em = null;
	EntityManagerFactory factory;
	
	private static JPAHelper instance = null;
	
	public static JPAHelper getInstance() {
		if(instance == null) {
			instance = new JPAHelper();
		}
		return instance;
	}
	
	public void initJPASystem() {
		java.util.Map<Object,Object> map = new java.util.HashMap<Object,Object>();
		factory = Persistence.createEntityManagerFactory("astropricelist", map);
		
		em = factory.createEntityManager();
	}
	public void stopJPASystem() {
		em.close();
		factory.close();
	}
	
	public List<StockPrices> getAllStockPrices() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StockPrices> q2 = cb.createQuery(StockPrices.class);
        
        Root<StockPrices> c = q2.from(StockPrices.class);
        
        q2.select(c);
        
        TypedQuery<StockPrices> query = em.createQuery(q2);
        return query.getResultList();
	}
	public List<StockNames> getAllStockNames() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StockNames> q2 = cb.createQuery(StockNames.class);
        
        Root<StockNames> c = q2.from(StockNames.class);
        
        q2.select(c);
        
        TypedQuery<StockNames> query = em.createQuery(q2);
        return query.getResultList();
	}
	
}
