package scraping.database;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import parser.ParserStock;

/**
 * Class for injecting stock information to 
 * our already defined price database.
 * 
 * @see /doc/priceDatabase.sql
 * @author kristian
 *
 */
public class Injector implements IInjector {
	
	private String dbuser;
	private String dbpass;
	
	public static void main( String[] args ) {
		
		System.out.println( "Injector!!!!" );
		
		Injector i = new Injector();
		
		ParserStock s1 = new ParserStock("STOCK1");
		ParserStock s2 = new ParserStock("STOCK2");
		ParserStock s3 = new ParserStock("STOCK3");
		
		ParserStock[] apa = { s1, s2, s3 };
		
		i.injectStockData( apa );
	}
	
	/**
	 * Constructor:
	 * 
	 * Loads the XML settings for priceDB,
	 * Connects to the database
	 */
	public Injector() {
		
	//Load XML with settings!
		//Define parsing helpers
		DocumentBuilderFactory 	factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		//Try to create a new document builder
		try {
			
			builder = factory.newDocumentBuilder();
			
		} catch (ParserConfigurationException e) {
			
			System.out.println( "ERROR: Injector: Constructor! New Document Builder" );
		}
		
		String fileName = "config/priceinfo_db.xml";
		
		File f = new File( fileName );
		
		Document doc = null;
		
		try {
			
			if( builder != null )
				doc = builder.parse( f );
		
		} catch( IOException ioE ) {
			
			System.out.println( "ERROR: Injector: Constructor! IOException XML settings" );
			
		} catch( SAXException saE ) {
			
			System.out.println( "ERROR: Injector: Constructor! SAXException?" );
		}
		
		//Store the database username and password params
		if( builder != null ) {
			
			dbuser = doc.getDocumentElement().getElementsByTagName("dbuser").item(0).getTextContent();
			dbpass = doc.getDocumentElement().getElementsByTagName("dbpass").item(0).getTextContent();
		}
	}
	
	/**
	 * Receive 
	 */
	@Override
	public boolean injectStockData( ParserStock[] stocks ) {
		
		System.out.println( "injectStockData" );
		
		
		
		//First off, get a list of all currently registered stocks
		
		for( ParserStock s : stocks ) {
			
			//System.out.println( "Injector: Price data Representation, Hoho!" );
		}
		
		return true;
	}
	
	/**
	 * Updates the market associated to a stock
	 * 
	 * +If a row with the same name as name exists
	 * 		=> Update the market
	 * +Otherwise
	 * 		=> Insert a new row with name and market
	 * 
	 * @param name
	 * @param market
	 */
	private static void updateMarket( String name, String market ) {
		
		
	}
}