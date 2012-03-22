package testing.scraping.database;

import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;

import scraping.model.ParserStock;

/**
 * Test the PriceDataRepresentation
 * 
 * Use Math.random to generate some different results for 
 * 
 * @author kristian
 *
 */
public class ParserStockTest {

	@Test
	public void testPriceDataRepresentation() {
		
		float multiplier = (float) Math.random() * 20;
		
		int 	vol 	= 29038 + (int) Math.round( Math.random() * 199 );
		float 	latest 	= (float) 123.344	* multiplier;
		float 	buy 	= (float) 1383.44	* multiplier;
		float 	sell 	= (float) 294.33	* multiplier;
		String 	date 	= "2012-02-02 20:20:20";
		
		//Price to test
		ParserStock pdr = new ParserStock( "VOLVO" );
		pdr.setVolume( vol );
		pdr.setLastClose( latest );
		pdr.setBuy( buy );
		pdr.setSell( sell );
		pdr.setDate( date );
		
		Assert.assertTrue( 	pdr.getVolume() 	== vol 		&&
							pdr.getLastClose()	== latest	&&
							pdr.getBuy()		== buy		&&
							pdr.getSell()		== sell		&&
							pdr.getDate().equals( date )
						);
	}
}