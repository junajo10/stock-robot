package testhelpers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;

/**
 * DatabaseCleaner
 * 
 * <p>
 * Superclass to all other tests that want to test things with the database. It cleans/resets the database before and after each test file, so we can run tests on an empty database 
 * </p>
 * 
 * @author kristian
 *
 */
public class DatabaseCleaner {

	//Protected variables, accessible in subclasses
	protected static IJPAHelper jpaHelper;
	
	/**
	 * Before each class DB test suite is instantiated
	 */
	@BeforeClass
	public static void beforeClass() {
		
		jpaHelper = JPAHelper.getInstance();
		cleanDatabase();
	}  
	
	/**
	 * Before every test in a suite
	 */
	@Before
	public void before() {
		
		cleanDatabase();
	}
	
	/**
	 * After running each DB test suite completely
	 */
	@AfterClass
	public static void afterClass() {
		
		cleanDatabase();
		jpaHelper = null;
	}
	
//	Auxiliary methods -----------------------
	
	/**
	 * Clean Database
	 */
	private static void cleanDatabase() {
		
		while( jpaHelper.getAllPortfolios().size() > 0 ) {
			
			PortfolioEntity p = jpaHelper.getAllPortfolios().get( 0 );
			jpaHelper.remove( p );
		}
		
	    for( StockPrices sp : jpaHelper.getAllStockPrices() ) {
	    	
	    	jpaHelper.remove( sp );
	    }
	    
		for( StockNames sn : jpaHelper.getAllStockNames() ) {
			
	    	jpaHelper.remove( sn );
		}
	}
}