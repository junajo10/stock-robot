package model.scraping;

import java.sql.Date;

import model.scraping.model.ParserStock;

import org.junit.Assert;
import org.junit.Test;


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
		
		long multiplier = (long) Math.random() * 20;
		
		int 	vol 	= 29038 + (int) Math.round( Math.random() * 199 );
		long 	latest 	= 123	* multiplier;
		long 	buy 	= 1383	* multiplier;
		long 	sell 	= 294	* multiplier;
		Date 	date 	= new Date(2884444);
		
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