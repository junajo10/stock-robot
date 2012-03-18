package scraping.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	
	private static final String XML_SETTINGS_URL	= "config/priceinfo_db.xml"; 
	private static final String DB_USER_TAGNAME	= "dbuser";
	private static final String DB_PASS_TAGNAME	= "dbpass";
	private static final String DB_PORT_TAGNAME	= "dbport";
	private static final String DB_NAME_TAGNAME	= "dbname";
	private static final String DB_ADDRESS_TAGNAME = "dbaddress";
	
	private static final String SQL_STOCKNAME_TAB	= "allStockNames";
	private static final String SQL_STOCKPRICE_HIS	= "stockPriceHistory";
	
	private static String dbuser;
	private static String dbpass;
	private static String dbport;
	private static String dbname;
	private static String dbaddress;
	
	private HashMap<String, String> idToName = new HashMap<String, String>();
	
	public static void main( String[] args ) {
		
		System.out.println( "Injector!!!!" );
		
		Injector i = new Injector();
		
		ParserStock s1 = new ParserStock("STOCK1");
		ParserStock s2 = new ParserStock("Bepa");
		ParserStock s3 = new ParserStock("STOCK2");
		
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
		
		Connection readConn = null;
		Connection writeConn = null;
		Statement readst = null;
		Statement writest = null;
		ResultSet rs = null;
		StringBuilder SQLtoInsert = new StringBuilder();
		
		String d = "'";
		
		//Try to register the mysql driver
		//Also, connect to the db
		try {
			
			DriverManager.registerDriver( new com.mysql.jdbc.Driver() );
			
			System.out.println( "dbuser: " + dbuser );
			System.out.println( "dbpass: " + dbpass );
			
			String url = "jdbc:mysql://" + dbaddress + ":" + dbport + "/" + dbname + "?allowMultiQueries=true";
			Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
			readConn = DriverManager.getConnection(url,dbuser,dbpass);
			writeConn = DriverManager.getConnection(url,dbuser,dbpass);
			
			readst = readConn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			writest = writeConn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			
			addAllNewStocks(url,stocks);
			
			rs = readst.executeQuery( "SELECT * FROM " + SQL_STOCKNAME_TAB );
			
			//Loop through all current corporations from the allNames table
			while( rs.next() ) {
				
				//Store the names 
				String name = rs.getString("name");
				String market = rs.getString("market");
				String id = rs.getString( "id" );
				
				idToName.put( name, id );
				
				System.out.println( "name: " + rs.getString( "name" ) + ", market: " + rs.getString( "market" ) );
			}
			
			for( ParserStock s : stocks ) {
				
				String companyId = idToName.get( s.name ) == null? "-1" : idToName.get( s.name );
				
				SQLtoInsert.append( "INSERT INTO " + SQL_STOCKPRICE_HIS + " VALUES (" + 
									addApo( companyId ) + "," + //Name (id)
									addApo( Integer.toString( s.volume ) ) + ", " +//volume
									addApo( Double.toString( s.lastClose ) ) + ", " +//last close price
									addApo( Double.toString( s.buy ) ) + ", " +//last close price
									addApo( Double.toString( s.sell ) ) + ", " +//last close price
									addApo("3000-03-03 19:29:00") +//Date, TODO: FIX REAL TIME!
									" ); " );
				
				writest.execute( SQLtoInsert.toString() );
			}
			
			System.out.println( SQLtoInsert );
			
			readConn.close();
			writeConn.close();
			
		} catch ( SQLException e ) {
			
			e.printStackTrace();
			
		} catch ( ClassNotFoundException cnfE ) {
			
			cnfE.printStackTrace();
			
		} catch ( InstantiationException iE ) {
			
			iE.printStackTrace();
			
		} catch ( IllegalAccessException iaE ) {
			
			iaE.printStackTrace();
		}
		
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
	
	private static void addAllNewStocks( String url, ParserStock[] stocks ) {
		
		Connection readConn = null;
		Connection writeConn = null;
		Statement readst = null;
		Statement writest = null;
		ResultSet rs = null;
		
		try {
		
			readConn = DriverManager.getConnection(url,dbuser,dbpass);
			writeConn = DriverManager.getConnection(url,dbuser,dbpass);
			
			readst = readConn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			writest = writeConn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			
			ResultSet allNames = readst.executeQuery( "SELECT * FROM " + SQL_STOCKNAME_TAB );
			ArrayList<String> existingNames = new ArrayList<String>();
			
			
			while( allNames.next() ) {
				
				existingNames.add( allNames.getString("name") );
			}
			
			System.out.println( "Existing names: " + existingNames );
			
			StringBuilder sb = new StringBuilder();
			
			for( ParserStock s : stocks ) {
				
				//If this stock is not already in there, then add it!
				if( !existingNames.contains( s.name ) ) {
					
					sb.append( "INSERT INTO " + SQL_STOCKNAME_TAB + " VALUES (" + "'0'," + addApo( s.name ) + ", " + addApo( s.market ) + ");" );
				}
			}
			
			System.out.println( "sb: " + sb );
			
			writest.execute( sb.toString() );
			
			readConn.close();
			writeConn.close();
			
		} catch( SQLException e ) {
			
			
		}
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
	private static void updateMarket( Statement st, String name, String market ) {
		
		
	}
	
	private static void addStockName( Statement st, String name, String market ) {
		
		
	}
}