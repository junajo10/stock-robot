package database.jpa;

import generic.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import database.jpa.tables.AlgorithmEntity;
import database.jpa.tables.AlgorithmSetting;
import database.jpa.tables.AlgorithmSettings;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.PortfolioEntity;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import database.jpa.tables.StocksToWatch;

/**
 * @author Daniel
 *
 * Basically the main JPA system we will use.
 * 
 * It has methods for most of the things we want to accomplish.
 */
class JPAHelperBase implements IJPAHelper {
	EntityManager em = null;
	EntityManagerFactory factory;

	@Override
	public void stopJPASystem() {
		em.close();
		factory.close();
	}
	@Override
	public List<AlgorithmEntity> getAllAlgorithms() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AlgorithmEntity> q2 = cb.createQuery(AlgorithmEntity.class);

		Root<AlgorithmEntity> c = q2.from(AlgorithmEntity.class);

		q2.select(c);

		TypedQuery<AlgorithmEntity> query = em.createQuery(q2);
		return query.getResultList();
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
	public List<StockPrices> getAllStockPricesReverseOrdered() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StockPrices> q = cb.createQuery(StockPrices.class);
		Root<StockPrices> c = q.from(StockPrices.class);
		q.select(c);
		q.orderBy(cb.asc(c.get("time")));
		TypedQuery<StockPrices> query = em.createQuery(q);
		
		return query.getResultList();
	}
	@Override
	public List<PortfolioInvestment> getAllPortfolioInvestment() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PortfolioInvestment> q2 = cb.createQuery(PortfolioInvestment.class);

		Root<PortfolioInvestment> c = q2.from(PortfolioInvestment.class);

		q2.select(c);

		TypedQuery<PortfolioInvestment> query = em.createQuery(q2);
		return query.getResultList();
	}
	@Override
	public List<StocksToWatch> getAllStocksToWatch() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StocksToWatch> q2 = cb.createQuery(StocksToWatch.class);

		Root<StocksToWatch> c = q2.from(StocksToWatch.class);

		q2.select(c);

		TypedQuery<StocksToWatch> query = em.createQuery(q2);
		return query.getResultList();
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
	public List<StockPrices> getPricesForStockPeriod( StockNames st, Date start, Date end ) {

		//TODO: Fix this to be type safe! I couldn't find a way to compare dates without using JPQL.
		//Get all prices WITHIN (including ends) start -> end, that are of the company defined in st
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
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
		return true;
	}
	@Override
	public synchronized boolean storeObjectIfPossible(Object o) {
		em.getTransaction().begin();
		try {
			em.persist(o);
		} catch (Exception e) {
			System.out.println("asdf");
			em.getTransaction().commit();
			return false;
		}
		em.getTransaction().commit();
		return true;
	}
	@Override
	public synchronized boolean storeListOfObjects(List list) {
		em.getTransaction().begin();
		for (Object o : list) {
			em.persist(o);
		}
		em.getTransaction().commit();
		return true;
	}
	@Override
	public synchronized int storeListOfObjectsDuplicates(List list) {
		int dup = 0;
		for (Object o : list) {
			try {
				em.getTransaction().begin();
				em.merge(o);
				em.getTransaction().commit();
			} catch (Exception e) {
				dup++;
			}
		}
		return dup;
	}
	@Override
	public synchronized boolean investMoney(long amount, PortfolioEntity portfolio) {
		storeObject(new PortfolioInvestment(portfolio, amount, true));

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

		if (portfolioTable.watchAllStocks()) {
			return getAllStockNames();
		}
		else {
			return portfolioTable.getStocksToWatch();
		}
	}
	@Override
	public List<StockPrices> getCurrentStocks(PortfolioEntity portfolioTable) {
		List<PortfolioHistory> portfolioHistory = getPortfolioHistory(portfolioTable);
		List<StockPrices> sp = new ArrayList<StockPrices>();
		
		for (PortfolioHistory ph : portfolioHistory) {
			if (ph.getSoldDate() == null)
				sp.add(ph.getStockPrice());
		}

		return sp;
	}
	@Override
	public List<PortfolioHistory> getPortfolioHistory(PortfolioEntity portfolio) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PortfolioHistory> q2 = cb.createQuery(PortfolioHistory.class);

		Root<PortfolioHistory> c = q2.from(PortfolioHistory.class);

		q2.select(c);

		Predicate p = em.getCriteriaBuilder().equal(c.get("portfolio"), portfolio);
		
		q2.where(p);
		
		TypedQuery<PortfolioHistory> query = em.createQuery(q2);
		
		return query.getResultList();
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
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PortfolioInvestment> q2 = cb.createQuery(PortfolioInvestment.class);

		Root<PortfolioInvestment> c = q2.from(PortfolioInvestment.class);

		q2.select(c);

		TypedQuery<PortfolioInvestment> query = em.createQuery(q2);

		Predicate p = em.getCriteriaBuilder().equal(c.get("portfolio").get("portfolioId"), portfolioTable.getPortfolioId());
		Predicate p2 = em.getCriteriaBuilder().notEqual(c.get("invested"), false);

		q2.where(p, p2);

		List<PortfolioInvestment> result = query.getResultList();

		long sum = 0;
		for (PortfolioInvestment ph : result)
			sum += ph.getAmount();

				return sum;
	}
	@Override
	public AlgorithmEntity getAlgorithmTable(PortfolioEntity portfolioTable) {
		return portfolioTable.getAlgorithm();
	}
	@Override
	public PortfolioHistory getSpecificPortfolioHistory(StockPrices stockPrice, PortfolioEntity portfolio, long amount) {


		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PortfolioHistory> q2 = cb.createQuery(PortfolioHistory.class);

		Root<PortfolioHistory> c = q2.from(PortfolioHistory.class);

		q2.select(c);

		Predicate p = em.getCriteriaBuilder().equal(c.get("portfolio"), portfolio);
		Predicate p2 = em.getCriteriaBuilder().equal(c.get("stockPrice"), stockPrice);
		Predicate p3 = em.getCriteriaBuilder().equal(c.get("amount"), amount);

		q2.where(p, p2);

		TypedQuery<PortfolioHistory> query = em.createQuery(q2);

		query.setMaxResults(1);

		return query.getSingleResult();
	}
	@Override
	public List<PortfolioHistory> getPortfolioHistory(StockPrices sp, PortfolioEntity portfolioTable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PortfolioHistory> q2 = cb.createQuery(PortfolioHistory.class);

		Root<PortfolioHistory> c = q2.from(PortfolioHistory.class);

		q2.select(c);

		Predicate p = em.getCriteriaBuilder().equal(c.get("portfolio"), portfolioTable);
		Predicate p2 = em.getCriteriaBuilder().equal(c.get("stockPrice"), sp);
		Predicate p3 = em.getCriteriaBuilder().equal(c.get("soldDate"), null);

		q2.where(p, p2, p3);

		TypedQuery<PortfolioHistory> query = em.createQuery(q2);

		return query.getResultList();
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
	public EntityManager getEntityManager() {
		return em;
	}
	@Override
	public List<AlgorithmSetting> getSettings(AlgorithmSettings settings) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AlgorithmSetting> q2 = cb.createQuery(AlgorithmSetting.class);

		Root<AlgorithmSetting> c = q2.from(AlgorithmSetting.class);

		q2.select(c);

		Predicate p = em.getCriteriaBuilder().equal(c.get("settings"), settings);

		q2.where(p);

		TypedQuery<AlgorithmSetting> query = em.createQuery(q2);

		return query.getResultList();
	}


}
