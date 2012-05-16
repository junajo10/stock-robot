package model.scraping.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.scraping.model.ParserStock;
import model.scraping.parser.AvanzaParser;
import model.scraping.parser.IParser;

public class AvanzaParserTest {
	
	IParser toTest = new AvanzaParser();
	ArrayList<ParserStock> list = null;
	
	@Before
	public void setupTest() {
		
		list = null;
		
		try {
			
			list = toTest.parse( new URL("https://www.avanza.se/aza/aktieroptioner/kurslistor/kurslistor.jsp?cc=SE&lkey=LargeCap.SE"), "LargeCap" );
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDownTest() {
		
		list = null;
	}
	
	@Test
	public void testParserListReceived() {
		
		Assert.assertTrue( list != null );
	}
	
	@Test
	public void testParserListLargerThanZero() {
		
		Assert.assertTrue( list.size() > 0 );
	}
}