import org.junit.AfterClass;
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
	protected IJPAHelper jpaHelper;
	
	@BeforeClass
	public void before() {
		
		jpaHelper = JPAHelper.getInstance();
		cleanDatabase();
	}  
	
	@AfterClass
	public void after() {
		
		cleanDatabase();
		jpaHelper = null;
	}
	
//	Auxiliary methods -----------------------
	
	/**
	 * Clean Database
	 */
	private void cleanDatabase() {
		
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