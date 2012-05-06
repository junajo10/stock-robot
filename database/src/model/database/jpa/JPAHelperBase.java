package model.database.jpa;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.openjpa.persistence.OpenJPAEntityManager;

import utils.global.Pair;

import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;





/**
 * Basically the main JPA system we will use.
 * 
 * It has methods for most of the things we want to accomplish.
 * 
 * @author Daniel
 */
class JPAHelperBase implements IJPAHelper {
	OpenJPAEntityManager em = null;
	EntityManagerFactory factory;

	@Override
	public void stopJPASystem() {
		em.close();
		factory.close();
	}
	@Override
	public List<PortfolioEntity> getAllPortfolios() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PortfolioEntity> q2 = cb.createQuery(PortfolioEntity.class);

		Root<PortfolioEntity> c = q2.from(PortfolioEntity.class);

		q2.select(c);

		TypedQuery<PortfolioEntity> query = em.createQuery(q2);
		return query.getResultList();
	}
	@Override
	public List<StockPrices> getAllStockPrices() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StockPrices> q2 = cb.createQuery(StockPrices.class);

		Root<StockPrices> c = q2.from(StockPrices.class);

		q2.select(c);

		TypedQuery<StockPrices> query = em.createQuery(q2);
		return query.getResultList();
	}
	@Override
	public synchronized List<StockPrices> getStockPricesReverseOrdered(int limit) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StockPrices> q = cb.createQuery(StockPrices.class);
		Root<StockPrices> c = q.from(StockPrices.class);
		q.select(c);
		q.orderBy(cb.desc(c.get("time")));
		
		
		TypedQuery<StockPrices> query = em.createQuery(q);
		
		query.setMaxResults(limit);
		
		List<StockPrices> result = query.getResultList();
		
		List<StockPrices> list = new ArrayList<StockPrices>();
		
		for (int i = result.size()-1; i >= 0; i--) {
			list.add(result.get(i));
		}
		
		return list;
	}
	@Override
	public List<Pair<StockNames, List<StockPrices>>> getStockInfo(int nLatest) {
		List<Pair<StockNames, List<StockPrices>>> output = new LinkedList<Pair<StockNames,List<StockPrices>>>();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StockNames> q2 = cb.createQuery(StockNames.class);

		Root<StockNames> c = q2.from(StockNames.class);

		q2.select(c);

		TypedQuery<StockNames> query = em.createQuery(q2);


		List<StockNames> stockNames = query.getResultList(); 

		for (StockNames sn : stockNames) {
			CriteriaQuery<StockPrices> q3 = cb.createQuery(StockPrices.class);

			Root<StockPrices> r = q3.from(StockPrices.class);
			Predicate p = em.getCriteriaBuilder().equal(r.get("stockName"), sn);

			q3.select(r);
			q3.where(p);
			q3.orderBy(cb.desc(r.get("time")));

			TypedQuery<StockPrices> query2 = em.createQuery(q3);

			query2.setMaxResults(nLatest);

			List<StockPrices> re = query2.getResultList();
			if (re.size()>0) {
				output.add(new Pair<StockNames, List<StockPrices>>(sn, re));
			}
		}

		return output;
	}
	@Override
	public List<StockPrices> getPricesForStock( StockNames st ) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StockPrices> cri = cb.createQuery(StockPrices.class);

		Root<StockPrices> root = cri.from(StockPrices.class);

		Predicate correctName = em.getCriteriaBuilder().equal( root.get("stockName"), st );

		cri.select(root);
		cri.where(correctName);

		TypedQuery<StockPrices> query = em.createQuery(cri);

		return query.getResultList();
	}
	
	@Override
	public StockPrices getPricesForStock( StockNames st, Date date ) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StockPrices> cri = cb.createQuery(StockPrices.class);

		Root<StockPrices> root = cri.from(StockPrices.class);

		Predicate correctName = em.getCriteriaBuilder().equal( root.get("stockName"), st );

		Predicate correctDate = em.getCriteriaBuilder().equal( root.get("time"), date );
		
		cri.select(root);
		cri.where(correctName);
		cri.where(correctDate);
		
		TypedQuery<StockPrices> query = em.createQuery(cri);

		if (query.getResultList().size() == 1)
			return query.getResultList().get(0);
		
		System.out.println("ASDFASDFASDF");
		return null;
	}
	@Override
	public List<StockPrices> getPricesForStockPeriod( StockNames st, Date start, Date end ) {

		//TODO: Fix this to be type safe! I couldn't find a way to compare dates without using JPQL.
		//Get all prices WITHIN (including ends) start -> end, that are of the company defined in st
		@SuppressWarnings("unchecked")
		TypedQuery<StockPrices> query = (TypedQuery<StockPrices>) em.createQuery( "SELECT o FROM StockPrices o WHERE o.time >= :startTime AND o.time <= :endTime AND o.stockName.id = :stockId" )
				.setParameter("startTime", start)
				.setParameter("endTime",   end)
				.setParameter("stockId", st.getId() );

		return query.getResultList();
	}
	@Override
	public List<StockNames> getAllStockNames() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StockNames> q2 = cb.createQuery(StockNames.class);

		Root<StockNames> c = q2.from(StockNames.class);

		q2.select(c);

		TypedQuery<StockNames> query = em.createQuery(q2);
		return query.getResultList();
	}
	@Override
	public synchronized boolean updateObject(Object o) {
		em.getTransaction().begin();
		em.merge(o);
		em.getTransaction().commit();
		return true;
	}
	@Override
	public synchronized boolean storeObject(Object o) {
		if (em.getTransaction().isActive())
			em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
		return true;
	}
	@Override
	public synchronized boolean storeObjectIfPossible(Object o) {
		try {
			em.getTransaction().begin();
			em.persist(o);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			return false;
		}
		return true;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public synchronized boolean storeListOfObjects(List list) {
		em.getTransaction().begin();
		for (Object o : list) {
			em.persist(o);
		}
		em.getTransaction().commit();
		return true;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public synchronized int storeListOfObjectsDuplicates(List list) {
		int dup = 0;
		for (Object o : list) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			try {
				em.getTransaction().begin();
				em.merge(o);
				em.getTransaction().commit();
			} catch (Exception e) {
				dup++;
			}
		}
		if (em.getTransaction().isActive())
			em.getTransaction().rollback();
		return dup;
	}
	@Override
	public synchronized boolean investMoney(long amount, PortfolioEntity portfolio) {
		portfolio.invest(amount, true);

		updateObject(portfolio);

		return true;
	}
	@Override
	public void remove(Object objectToBeRemoved) {
		em.getTransaction().begin();
		em.remove(objectToBeRemoved);
		em.getTransaction().commit();
	}
	@Override
	public List<StockNames> getStockNames(PortfolioEntity portfolioTable) {
		return getAllStockNames();
	}
	@Override
	public List<StockPrices> getCurrentStocks(PortfolioEntity portfolioTable) {
		List<StockPrices> sp = new ArrayList<StockPrices>();
		
		for (PortfolioHistory ph : portfolioTable.getHistory()) {
			if (ph.getSoldDate() == null)
				sp.add(ph.getStockPrice());
		}

		return sp;
	}
	@Override
	public List<PortfolioHistory> getCurrentStocksHistory(PortfolioEntity portfolioTable) {
		List<PortfolioHistory> history = new ArrayList<PortfolioHistory>();
		
		for (PortfolioHistory ph : portfolioTable.getHistory()) {
			if (ph.getSoldDate() == null)
				history.add(ph);
		}

		return history;
	}
	@Override
	public List<Pair<StockPrices, StockPrices>> getOldStocks(
			PortfolioEntity portfolioTable) {

		List<Pair<StockPrices, StockPrices>> oldStocks = new LinkedList<Pair<StockPrices,StockPrices>>();
		for (PortfolioHistory ph : portfolioTable.getHistory()) {
			if (ph.getSoldDate() != null) {
				StockPrices old = ph.getStockPrice();
				StockPrices ny = ph.getSoldStockPrice(em);

				oldStocks.add(new Pair<StockPrices, StockPrices>(old, ny));
			}
		}

		return oldStocks;
	}
	@Override
	public StockPrices getLatestStockPrice(StockPrices from) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StockPrices> q2 = cb.createQuery(StockPrices.class);

		Root<StockPrices> c = q2.from(StockPrices.class);

		q2.select(c);

		Predicate p = em.getCriteriaBuilder().equal(c.get("stockName"), from.getStockName());

		q2.where(p);
		q2.orderBy(cb.desc(c.get("time")));

		TypedQuery<StockPrices> query = em.createQuery(q2);

		query.setMaxResults(1);

		return query.getSingleResult();
	}
	@Override
	public long getTotalInvestedAmount(PortfolioEntity portfolioTable) {
		return portfolioTable.getTotalInvestedAmount();
	}
	@Override
	public List<PortfolioHistory> getPortfolioHistory(StockPrices sp, PortfolioEntity portfolioTable) {
		List<PortfolioHistory> result = new LinkedList<PortfolioHistory>();
		for (PortfolioHistory ph : portfolioTable.getHistory()) {
			if (ph.getStockPrice() == sp)
				result.add(ph);
		}

		return result;
	}
	@Override
	public List<StockPrices> getNLatest(StockPrices from, int n) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StockPrices> q2 = cb.createQuery(StockPrices.class);

		Root<StockPrices> c = q2.from(StockPrices.class);

		q2.select(c);

		Predicate p = em.getCriteriaBuilder().equal(c.get("stockName"), from.getStockName());

		q2.where(p);

		q2.orderBy(cb.desc(c.get("time")));

		TypedQuery<StockPrices> query = em.createQuery(q2);

		query.setMaxResults(n);

		return query.getResultList();
	}

	@Override
	public OpenJPAEntityManager getEntityManager() {
		return em;
	}
	@Override
	public Map<String, StockPrices> getLatestMap() {
		Map<String, StockPrices> map = new HashMap<String, StockPrices>();
		
		for (StockPrices sp : getLatestStockPrices()) {
			map.put(sp.getStockName().getName(), sp);
		}
		
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<StockPrices> getLatestStockPrices() {
		// TODO: fix in jpa instead.
		
		//SELECT * FROM StockPrices s1 JOIN ( SELECT stockName, MAX(time) AS mTime FROM StockPrices GROUP BY stockName) AS s2 ON s1.stockName = s2.stockName AND s1.time = s2.mTime;
		 
		Query query = em.createNativeQuery("SELECT * " +
			"FROM StockPrices s1 " +
			"JOIN ( " +
			"SELECT stockName, MAX(time) AS mTime " +
			"FROM StockPrices " +
			"GROUP BY stockName) AS s2 " +
			"ON s1.stockName = s2.stockName " + 
			"AND s1.time = s2.mTime;", StockPrices.class);
		
		return query.getResultList();
	}
	@Override
	public void close() {
		em.close();
	}
}
