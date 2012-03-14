package testing.scraping.database;

import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;

import scraping.database.PriceDataRepresentation;

/**
 * Test the PriceDataRepresentation
 * 
 * Use Math.random to generate some different results for 
 * 
 * @author kristian
 *
 */
public class PriceDataTest {

	@Test
	public void testPriceDataRepresentation() {
		
		float multiplier = (float) Math.random() * 20;
		
		int 	vol 	= 29038 + (int) Math.round( Math.random() * 199 );
		float 	high 	= (float) 23.3		* multiplier;
		float 	low 	= (float) 234.3		* multiplier;
		float 	latest 	= (float) 123.344	* multiplier;
		float 	buy 	= (float) 1383.44	* multiplier;
		float 	sell 	= (float) 294.33	* multiplier;
		Date 	date 	= new Date( 234444 );
		
		//Price to test
		PriceDataRepresentation pdr = new PriceDataRepresentation( vol, high, low, latest, buy, sell, date );
		
		Assert.assertTrue( 	pdr.getVolume() == vol 		&&
							pdr.getHigh() 	== high 	&&
							pdr.getLow()	== low		&&
							pdr.getLatest()	== latest	&&
							pdr.getBuy()	== buy		&&
							pdr.getSell()	== sell		&&
							pdr.getDate().equals( date )
						);
	}
}