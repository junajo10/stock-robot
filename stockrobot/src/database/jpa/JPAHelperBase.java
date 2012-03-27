package database.jpa;

import generic.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import database.jpa.tables.StocksToWatch;

/**
 * @author Daniel
 *
 * Basicly the main JPA system we will use.
 * 
 * It has methods for most of the things we want to accomplish.
 */
class JPAHelperBase implements IJPAHelper {
	EntityManager em = null;
	EntityManagerFactory factory;

	/*
	 * Stops the jpa system
	 */
	@Override
	public void stopJPASystem() {
		em.close();
		factory.close();
	}
	/**
	 * Returns a list of all the algorithms.
	 * @return a list of all the algorithms.
	 */
	@Override
	public List<AlgorithmEntitys> getAllAlgorithms() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AlgorithmEntitys> q2 = cb.createQuery(AlgorithmEntitys.class);

		Root<AlgorithmEntitys> c = q2.from(AlgorithmEntitys.class);

		q2.select(c);

		TypedQuery<AlgorithmEntitys> query = em.createQuery(q2);
		return query.getResultList();
	}
	/**
	 * Will give back all portfolios in the JPA system.
	 * @return A list with PortfolioTables
	 */
	@Override
	public List<PortfolioEntitys> getAllPortfolios() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PortfolioEntitys> q2 = cb.createQuery(PortfolioEntitys.class);

		Root<PortfolioEntitys> c = q2.from(PortfolioEntitys.class);

		q2.select(c);

		TypedQuery<PortfolioEntitys> query = em.createQuery(q2);
		return query.getResultList();
	}
	/**
	 * Will give back all stockPrices
	 * @return A list of stockPrices
	 */
	@Override
	public List<StockPrices> getAllStockPrices() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StockPrices> q2 = cb.createQuery(StockPrices.class);

		Root<StockPrices> c = q2.from(StockPrices.class);

		q2.select(c);

		TypedQuery<StockPrices> query = em.createQuery(q2);
		return query.getResultList();
	}
	/**
	 * Will give back all stockPrices
	 * @return A list of stockPrices
	 */
	@Override
	public List<StockPrices> getAllStockPricesReverseOrdered() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StockPrices> q2 = cb.createQuery(StockPrices.class);

		Root<StockPrices> c = q2.from(StockPrices.class);
		q2.orderBy(cb.desc(c.get("time")));
		q2.select(c);
		
		
		TypedQuery<StockPrices> query = em.createQuery(q2);
		return query.getResultList();
	}
	/**
	 * Will give back all PortfolioInvestment
	 * @return A list of PortfolioInvestment
	 */
	@Override
	public List<PortfolioInvestment> getAllPortfolioInvestment() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PortfolioInvestment> q2 = cb.createQuery(PortfolioInvestment.class);

		Root<PortfolioInvestment> c = q2.from(PortfolioInvestment.class);

		q2.select(c);

		TypedQuery<PortfolioInvestment> query = em.createQuery(q2);
		return query.getResultList();
	}
	/**
	 * Will give back all StocksToWatch
	 * @return A list of StocksToWatch
	 */
	@Override
	public List<StocksToWatch> getAllStocksToWatch() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StocksToWatch> q2 = cb.createQuery(StocksToWatch.class);

		Root<StocksToWatch> c = q2.from(StocksToWatch.class);

		q2.select(c);

		TypedQuery<StocksToWatch> query = em.createQuery(q2);
		return query.getResultList();
	}
	/**
	 * Will give back stockInformation in the form of: A list of pairs, where the left side is the StockName, and the right part is a list of nLatest maxSize with StockPrices
	 * @return A list Pairs of StockNames, List of StockPrices
	 */
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

	/**
	 * Method for getting ALL prices for a specific stock  
	 * 
	 * @param st Stock to get for
	 * @return List of all prices
	 */
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

	/**
	 * Method for querying a specific stock's value within a specified timespan. 
	 * 
	 * @param st The stock to query for
	 * @param start Date where to start looking for prices
	 * @param end Date where to stop looking for prices
	 * @return
	 */
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

	/**
	 * Will give a list of all the diffrent StockNames
	 * @return A list of stockNames
	 */
	@Override
	public List<StockNames> getAllStockNames() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StockNames> q2 = cb.createQuery(StockNames.class);

		Root<StockNames> c = q2.from(StockNames.class);

		q2.select(c);

		TypedQuery<StockNames> query = em.createQuery(q2);
		return query.getResultList();
	}
	/**
	 * Updates an object to the database
	 * @param o The object to be updated.
	 * @return True if it went ok.
	 */
	@Override
	public synchronized boolean updateObject(Object o) {
		em.getTransaction().begin();
		em.merge(o);
		em.getTransaction().commit();
		return true;
	}
	/**
	 * Store 1 object in the database.
	 * @param o Object to be stored
	 * @return True if it went ok
	 */
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
	/**
	 * Stores a list of objects to the database
	 * @param list List of objects
	 * @return True if it went ok
	 */
	@Override
	public synchronized boolean storeListOfObjects(List list) {
		em.getTransaction().begin();
		for (Object o : list) {
			em.persist(o);
		}
		em.getTransaction().commit();
		return true;
	}
	/**
	 * A special case of storeListOfObjects, this will store the list but ignore duplicates.
	 * @param list List of objects
	 * @return the number of objects not stored.
	 */
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
	/**
	 * Invests money in a given portfolio
	 * @param amount The amount of money to invest
	 * @param portfolio The portfolio to invest to
	 * @return Returns true if everything went ok
	 */
	@Override
	public synchronized boolean investMoney(long amount, PortfolioEntitys portfolio) {
		em.getTransaction().begin();

		em.persist(new PortfolioInvestment(portfolio, amount, true));

		em.getTransaction().commit();

		portfolio.invest(amount, true);

		updateObject(portfolio);

		return true;
	}
	/**
	 * Deletes an object in the database.
	 * @param objectToBeRemoved The object to be removed.
	 */
	@Override
	public void remove(Object objectToBeRemoved) {
		em.getTransaction().begin();
		em.remove(objectToBeRemoved);
		em.getTransaction().commit();
	}
	/**
	 * Gets the stockNames this portfolio is set to watch.
	 * @param portfolioTable The portfolio
	 * @return A list of stockNames
	 */
	@Override
	public List<StockNames> getStockNames(PortfolioEntitys portfolioTable) {

		if (portfolioTable.watchAllStocks()) {
			return getAllStockNames();
		}
		else {
			return portfolioTable.getStocksToWatch();
		}
	}
	/**
	 * Returns a list of currently owned stocks.
	 * @param portfolioTable The portfolio.
	 * @return A List of currently owned stocks.
	 */
	@Override
	public List<StockPrices> getCurrentStocks(PortfolioEntitys portfolioTable) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PortfolioHistory> q2 = cb.createQuery(PortfolioHistory.class);

		Root<PortfolioHistory> c = q2.from(PortfolioHistory.class);

		q2.select(c);

		TypedQuery<PortfolioHistory> query = em.createQuery(q2);

		Predicate p = em.getCriteriaBuilder().equal(c.get("portfolio").get("portfolioId"), portfolioTable.getPortfolioId());
		Predicate p2 = em.getCriteriaBuilder().equal(c.get("soldDate"), null);

		q2.where(p, p2);

		List<PortfolioHistory> result = query.getResultList();

		List<StockPrices> sp = new ArrayList<StockPrices>();

		for (PortfolioHistory ph : result)
			sp.add(ph.getStockPrice());

				return sp;
	}
	/**
	 * Returns a list of pairs with old stocks, left is the stockpoint when it was bought
	 * the right one is the stockpoint of when it was sold
	 * @param portfolioTable
	 * @return
	 */
	@Override
	public List<Pair<StockPrices, StockPrices>> getOldStocks(
			PortfolioEntitys portfolioTable) {

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
	/**
	 * Given a StockPrice will return the latest stockprice with the same name.
	 * @param from The old stockPrice
	 * @return The latest stockPrice with same name as given stockPrice
	 */
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
	/**
	 * Returns the total amount invested in this portfolio
	 * @param portfolioTable The portfolio to be audited.
	 * @return The total amount invested
	 */
	@Override
	public long getTotalInvestedAmount(PortfolioEntitys portfolioTable) {
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
	/**
	 * Gives the AlgorithmTable for a given portfolio
	 * @param portfolioTable The portfolioTable to get the AlgorithmTable from
	 * @return An algorithmTable
	 */
	@Override
	public AlgorithmEntitys getAlgorithmTable(PortfolioEntitys portfolioTable) {
		return portfolioTable.getAlgorithm();
	}

	/**
	 * Given a stockPrice and a portfolio, will find the PortfolioHistory that has the StockPrice as buying price, 
	 * and coupled with the same portfolio.
	 * 
	 * This method will be used for example in the TraderSimulator to get the right PortfolioHistory.
	 * 
	 * @param stockPrice The buying stockPrice
	 * @param portfolio The portfolio
	 * @return A PortfolioHistory.
	 */
	@Override
	public PortfolioHistory getSpecificPortfolioHistory(StockPrices stockPrice, PortfolioEntitys portfolio) {


		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PortfolioHistory> q2 = cb.createQuery(PortfolioHistory.class);

		Root<PortfolioHistory> c = q2.from(PortfolioHistory.class);

		q2.select(c);

		Predicate p = em.getCriteriaBuilder().equal(c.get("portfolio"), portfolio);
		Predicate p2 = em.getCriteriaBuilder().equal(c.get("stockPrice"), stockPrice);

		q2.where(p, p2);

		TypedQuery<PortfolioHistory> query = em.createQuery(q2);

		query.setMaxResults(1);

		return query.getSingleResult();
	}
	/**
	 * Returns a list of Stockprices with same name as a given StockPrice, with max size of a given value.
	 * @param from The StockPrice that has the same name 
	 * @param n How many max results
	 * @return A list of stockPrices
	 */
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

}
