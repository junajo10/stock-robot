package scraping.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

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
	
	private final String XML_SETTINGS_URL	= "config/priceinfo_db.xml"; 
	private final String DB_USER_TAGNAME	= "dbuser";
	private final String DB_PASS_TAGNAME	= "dbpass";
	private final String DB_PORT_TAGNAME	= "dbport";
	private final String DB_NAME_TAGNAME	= "dbname";
	private final String DB_ADDRESS_TAGNAME = "dbaddress";
	
	private final String SQL_STOCKNAME_TAB	= "allStockNames";
	private final String SQL_STOCKPRICE_HIS	= "stockPriceHistory";
	
	private String dbuser;
	private String dbpass;
	private String dbport;
	private String dbname;
	private String dbaddress;
	
	private HashMap<Integer, String> idToName = new HashMap<Integer, String>();
	
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
		
		String fileName = XML_SETTINGS_URL;
		
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
			
			dbuser = doc.getDocumentElement().getElementsByTagName(DB_USER_TAGNAME).item(0).getTextContent();
			dbpass = doc.getDocumentElement().getElementsByTagName(DB_PASS_TAGNAME).item(0).getTextContent();
			dbport = doc.getDocumentElement().getElementsByTagName(DB_PORT_TAGNAME).item(0).getTextContent();
			dbname = doc.getDocumentElement().getElementsByTagName(DB_NAME_TAGNAME).item(0).getTextContent();
			dbaddress = doc.getDocumentElement().getElementsByTagName(DB_ADDRESS_TAGNAME).item(0).getTextContent();
		}
	}
	
	/**
	 * Receive 
	 */
	@Override
	public boolean injectStockData( ParserStock[] stocks ) {
		
		System.out.println( "injectStockData" );
		
		String connectionInfo = "jdbc:mysql://" + dbaddress + ":" + dbport + "/" + dbname;
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		StringBuilder SQLtoInsert = new StringBuilder();
		
		String d = "'";
		
		//Try to register the mysql driver
		//Also, connect to the db
		try {
			
			DriverManager.registerDriver( new com.mysql.jdbc.Driver() );
			
			System.out.println( "dbuser: " + dbuser );
			System.out.println( "dbpass: " + dbpass );
			
			String url = "jdbc:mysql://" + dbaddress + ":" + dbport + "/" + dbname;
			Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
			conn = DriverManager.getConnection(url,dbuser,dbpass);
			
			st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			rs = st.executeQuery( "SELECT * FROM " + SQL_STOCKNAME_TAB );
			
			//Loop through all current corporations from the allNames table
			while( rs.next() ) {
				
				//Store the names 
				String name = rs.getString("name");
				String market = rs.getString("market");
				int id = rs.getInt( "id" );
				
				idToName.put( id, name );
				
				System.out.println( "name: " + rs.getString( "name" ) + ", market: " + rs.getString( "market" ) );
			}
			
			for( ParserStock s : stocks ) {
				
				/*
				SQLtoInsert.append( "INSERT INTO " + SQL_STOCKPRICE_HIS + " VALUES (" + 
												addApo( idToName.get(s.name)) + //Name (id)
												addApo( Integer.toString( s.volume ) ) + //volume
												addApo( s. ) + //volume
												" )"  );
												
				*/
			}
			
		} catch ( SQLException e ) {
			
			e.printStackTrace();
			
		} catch ( ClassNotFoundException cnfE ) {
			
			cnfE.printStackTrace();
			
		} catch ( InstantiationException iE ) {
			
			iE.printStackTrace();
			
		} catch ( IllegalAccessException iaE ) {
			
			iaE.printStackTrace();
		}
		
		//First off, get a list of all currently registered stocks
		
		//for( ParserStock s : stocks ) {
			
			//System.out.println( "Injector: Price data Representation, Hoho!" );
		//}
		
		return true;
	}
	
	/**
	 * Surround str with 'str'
	 * 
	 * @param str
	 * @return
	 */
	private static String addApo( String str ) {
		
		return "'" + str + "'";
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