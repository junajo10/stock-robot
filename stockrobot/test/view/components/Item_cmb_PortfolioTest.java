package view.components;

import junit.framework.Assert;

import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;
import model.portfolio.IPortfolio;
import model.portfolio.Portfolio;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Item_cmb_PortfolioTest
 * 
 * @author kristian
 *
 */
public class Item_cmb_PortfolioTest {

	Item_cmb_Portfolio toTest;
	IPortfolio portfolio;
	
	@Before
	public void setup() {
		
		//Apparently needed just to set JPA up so it's possible to test the created portfolio later
		JPAHelper.getInstance();
		
		//Stuff Item_cmb_Portfolio uses:
		PortfolioEntity ent = new PortfolioEntity( "Item_cmb_PortfolioTest::Test" );
		portfolio = new Portfolio( ent );
		
		//Initialize the tested class
		toTest = new Item_cmb_Portfolio( portfolio );
	}
	
	/**
	 * Test that the portfolio received when running getPortfolio equals the one created in setup
	 */
	@Test
	public void getPortfolioEquals() {
		
		Assert.assertTrue( toTest.getPortfolio().equals( portfolio ) );
	}
	
	/**
	 * Test that the portfolio received when running getPortfolio is THE instance that's created above
	 */
	@Test
	public void getPortfolioInstance() {
		
		Assert.assertTrue( toTest.getPortfolio() == portfolio );
	}
	
	/**
	 * Check so that calling the toString method of toTest gives the same result back repeatedly
	 */
	@Test
	public void sameNameRepeatedly() {
		
		String nameAtFirst = toTest.toString();
		String comp = "";
		
		for( int i = 0; i < 2000 + (int) Math.round( Math.random() * 1000 ); i ++ ) {
			
			comp = toTest.toString();
			
			if( !nameAtFirst.equals(comp) ) {
				
				nameAtFirst = comp;
				break;
			}
		}
		
		Assert.assertTrue( nameAtFirst.equals( comp ) );
	}
}