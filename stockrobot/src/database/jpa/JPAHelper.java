package database.jpa;

import generic.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import database.jpa.tables.AlgorithmsTable;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.PortfolioTable;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;

/**
 * @author Daniel
 *
 * Basicly the main JPA system we will use.
 */
public class JPAHelper {
	EntityManager em = null;
	EntityManagerFactory factory;
	
	private static JPAHelper instance = null;
	
	/**
	 * Creates an instance of JPAHelper if it dosent already exist, and returns the instance.
	 * @return An instance of JPAHelper
	 */
	public static JPAHelper getInstance() {
		if(instance == null) {
			instance = new JPAHelper();
		}
		return instance;
	}
	/**
	 * Inits the jpa system.
	 */
	public void initJPASystem() {
		Map<Object,Object> map = new java.util.HashMap<Object,Object>();
		factory = Persistence.createEntityManagerFactory("astroportfolio", map);
		
		em = factory.createEntityManager();
	}
	/*
	 * Stops the jpa system
	 */
	public void stopJPASystem() {
		em.close();
		factory.close();
	}
	/**
	 * Returns a list of all the algorithms.
	 * @return a list of all the algorithms.
	 */
	public List<AlgorithmsTable> getAllAlgorithms() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AlgorithmsTable> q2 = cb.createQuery(AlgorithmsTable.class);
        
        Root<AlgorithmsTable> c = q2.from(AlgorithmsTable.class);
        
        q2.select(c);
        
        TypedQuery<AlgorithmsTable> query = em.createQuery(q2);
        return query.getResultList();
	}
	/**
	 * Will give back all portfolios in the JPA system.
	 * @return A list with PortfolioTables
	 */
	public List<PortfolioTable> getAllPortfolios() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PortfolioTable> q2 = cb.createQuery(PortfolioTable.class);
        
        Root<PortfolioTable> c = q2.from(PortfolioTable.class);
        
        q2.select(c);
        
        TypedQuery<PortfolioTable> query = em.createQuery(q2);
        return query.getResultList();
	}
	/**
	 * Will give back all stockPrices
	 * @return A list of stockPrices
	 */
	public List<StockPrices> getAllStockPrices() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StockPrices> q2 = cb.createQuery(StockPrices.class);
        
        Root<StockPrices> c = q2.from(StockPrices.class);
        
        q2.select(c);
        
        TypedQuery<StockPrices> query = em.createQuery(q2);
        return query.getResultList();
	}
	/**
	 * Will give a list of all the diffrent StockNames
	 * @return A list of stockNames
	 */
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
	public boolean updateObject(Object o) {
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
	public boolean storeObject(Object o) {
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
		return true;
	}
	/**
	 * Stores a list of objects to the database
	 * @param list List of objects
	 * @return True if it went ok
	 */
	public boolean storeListOfObjects(List list) {
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
	public int storeListOfObjectsDuplicates(List list) {
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
	public boolean investMoney(long amount, PortfolioTable portfolio) {
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
	public void remove(Object objectToBeRemoved) {
		em.remove(objectToBeRemoved);
	}
	/**
	 * Gets the stockNames this portfolio is set to watch.
	 * @param portfolioTable The portfolio
	 * @return A list of stockNames
	 */
	public List<StockNames> getStockNames(PortfolioTable portfolioTable) {

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
	public List<StockPrices> getCurrentStocks(PortfolioTable portfolioTable) {
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
	public List<Pair<StockPrices, StockPrices>> getOldStocks(
			PortfolioTable portfolioTable) {
		
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
	 * Returns the total amount invested in this portfolio
	 * @param portfolioTable The portfolio to be audited.
	 * @return The total amount invested
	 */
	public long getTotalInvestedAmount(PortfolioTable portfolioTable) {
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
	public AlgorithmsTable getAlgorithmTable(PortfolioTable portfolioTable) {
		return portfolioTable.getAlgorithm();
	}
}
