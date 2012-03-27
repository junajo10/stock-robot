package database.jpa;

import generic.Pair;

import java.util.ArrayList;
import java.util.Date;
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
import database.jpa.tables.StocksToWatch;

/**
 * @author Daniel
 *
 * Basicly the main JPA system we will use.
 * 
 * It has methods for most of the things we want to accomplish.
 */
public class JPAHelper extends JPAHelperBase{
	private static JPAHelper instance = null;
	
	private JPAHelper(String database) {
		Map<Object,Object> map = new java.util.HashMap<Object,Object>();
		factory = Persistence.createEntityManagerFactory(database, map);

		em = factory.createEntityManager();
	}
	/**
	 * Creates an instance of JPAHelper if it dosent already exist, and returns the instance of it.
	 * @return An instance of JPAHelper
	 */
	public static JPAHelper getInstance() {
		if(instance == null) {
			synchronized (JPAHelperBase.class) {
				if (instance == null)
					instance = new JPAHelper(); 
			}
		}
		return instance;
	}
	/**
	 * Just for testing purposes!
	 * @param database
	 * @return an instance of the testing JPAHelper
	 */
	public static JPAHelper getInstance(String database) {
		if(instance == null) {
			synchronized (JPAHelperBase.class) {
				if (instance == null)
					instance = new JPAHelper(database); 
			}
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
