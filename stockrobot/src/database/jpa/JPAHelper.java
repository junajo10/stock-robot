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


import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;

/**
 * @author Daniel
 *
 * Basicly the main JPA system we will use.
 * 
 * It has methods for most of the things we want to accomplish.
 */
public class JPAHelper {
	EntityManager em = null;
	EntityManagerFactory factory;
	
	private static JPAHelper instance = null;
	
	private JPAHelper() {
		
	}
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
	public List<StockPrices> getAllStockPrices() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StockPrices> q2 = cb.createQuery(StockPrices.class);
        
        Root<StockPrices> c = q2.from(StockPrices.class);
        
        q2.select(c);
        
        TypedQuery<StockPrices> query = em.createQuery(q2);
        return query.getResultList();
	}
	/**
	 * Will give back stockInformation in the form of: A list of pairs, where the left side is the StockName, and the right part is a list of nLatest maxSize with StockPrices
	 * @return A list Pairs of StockNames, List of StockPrices
	 */
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
	public synchronized boolean storeObject(Object o) {
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
		return true;
	}
	public synchronized boolean storeObjectIfPossible(Object o) {
		em.getTransaction().begin();
		try {
			em.persist(o);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		em.getTransaction().commit();
		return true;
	}
	/**
	 * Stores a list of objects to the database
	 * @param list List of objects
	 * @return True if it went ok
	 */
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
	public void remove(Object objectToBeRemoved) {
		em.remove(objectToBeRemoved);
	}
	/**
	 * Gets the stockNames this portfolio is set to watch.
	 * @param portfolioTable The portfolio
	 * @return A list of stockNames
	 */
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
	 * Returns the total amount invested in this portfolio
	 * @param portfolioTable The portfolio to be audited.
	 * @return The total amount invested
	 */
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
	public AlgorithmEntitys getAlgorithmTable(PortfolioEntitys portfolioTable) {
		return portfolioTable.getAlgorithm();
	}
	
}
