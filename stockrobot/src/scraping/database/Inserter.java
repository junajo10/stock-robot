package scraping.database;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import scraping.parser.ParserStock;

/**
 * Class for injecting stock information to 
 * our already defined price database.
 * 
 * @see /doc/priceDatabase.sql
 * @author kristian
 *
 */
public class Inserter implements IInserter {
	
	private static final String XML_SETTINGS_URL	= "config/priceinfo_db.xml"; 
	private static final String DB_USER_TAGNAME		= "dbuser";
	private static final String DB_PASS_TAGNAME		= "dbpass";
	private static final String DB_PORT_TAGNAME		= "dbport";
	private static final String DB_NAME_TAGNAME		= "dbname";
	private static final String DB_ADDRESS_TAGNAME 	= "dbaddress";
	
	private static final String SQL_STOCKNAME_TAB	= "allStockNames";
	private static final String SQL_STOCKPRICE_HIS	= "stockPriceHistory";
	
	private static String dbuser;
	private static String dbpass;
	private static String dbport;
	private static String dbname;
	private static String dbaddress;
	private static String url;
	
	//Map the database name and id to each other
	private HashMap<String, String> nameToId = new HashMap<String, String>();
	//Map the database name and market
	private HashMap<String, String> nameToMarket = new HashMap<String, String>();
	
	public static void main( String[] args ) {
		
		Inserter i = new Inserter();
		
		ParserStock s1 = new ParserStock("STOCK_NEW");
		s1.market = "CapCap";
		ParserStock s2 = new ParserStock("Bepa");
		ParserStock s3 = new ParserStock("STOCK2");
		s3.market = "SmallCap";
		
		ParserStock[] apa = { s1, s2, s3 };
		
		boolean result = i.injectStockData( apa );
		//boolean result = i.updateAllMarkets( apa );
		
		System.out.println( "Run this code, did it work? " + result );
	}
	
	/**
	 * Constructor:
	 * 
	 * Loads the XML settings for priceDB,
	 * Connects to the database
	 */
	public Inserter() {
		
		try {
			
			//Register MYSQL driver
			DriverManager.registerDriver( new com.mysql.jdbc.Driver() );
		
		} catch( SQLException e ) {
			
			e.printStackTrace();
		}
		
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
		
		//File name
		String fileName = XML_SETTINGS_URL;
		File f = new File( fileName );
		
		//Document to hold XML tree structure
		Document doc = null;
		
		try {
			
			//Try to parse
			if( builder != null )
				doc = builder.parse( f );
		
		} catch( IOException ioE ) {
			
			System.out.println( "ERROR: Injector: Constructor! IOException XML settings" );
			
		} catch( SAXException saE ) {
			
			System.out.println( "ERROR: Injector: Constructor! SAXException?" );
		}
		
		//If parsing was successful, store the XML data to local variables
		if( builder != null ) {
			
			dbuser = doc.getDocumentElement().getElementsByTagName(DB_USER_TAGNAME).item(0).getTextContent();
			dbpass = doc.getDocumentElement().getElementsByTagName(DB_PASS_TAGNAME).item(0).getTextContent();
			dbport = doc.getDocumentElement().getElementsByTagName(DB_PORT_TAGNAME).item(0).getTextContent();
			dbname = doc.getDocumentElement().getElementsByTagName(DB_NAME_TAGNAME).item(0).getTextContent();
			dbaddress = doc.getDocumentElement().getElementsByTagName(DB_ADDRESS_TAGNAME).item(0).getTextContent();
			
			//Address to the database
			url = "jdbc:mysql://" + dbaddress + ":" + dbport + "/" + dbname + "?allowMultiQueries=true";
		}
	}
	
	/**
	 * Inject stock data!
	 * 
	 * +Connect to the databas,
	 * +Inject all companies in stocks not already in the database
	 * +Insert all new values for the stocks in the list
	 * 
	 * Do not care if the stock's market is changed here
	 */
	@Override
	public boolean injectStockData( ParserStock[] stocks ) {
		
		//Try to register the mysql driver
		//Also, connect to the db
		try {
			
			Class.forName( "com.mysql.jdbc.Driver" ).newInstance();
			
			//Create connections to use, abstracting the creation away from here
			ConnectionCreator reader = ConnectionCreator.getWriteConnection(url, dbuser, dbpass, ConnectionCreator.READ);
			ConnectionCreator writer = ConnectionCreator.getWriteConnection(url, dbuser, dbpass, ConnectionCreator.WRITE);
			
			//Add any new stock name not already in the list
			boolean allNewsStocksSuccessful = addAllNewStocks(url,stocks);
			
			//If the adding of new stocks wasn't successful, tell the using code!
			if( !allNewsStocksSuccessful )
				return false;
			
			//Get all names from the DB, even newly added ones (added in this method)
			ResultSet rs = reader.getStatement().executeQuery( "SELECT * FROM " + SQL_STOCKNAME_TAB );
			
			//Collect all insertions to one statement (less calls to the DB)
			StringBuilder SQLtoInsert = new StringBuilder();
			
			//Loop through all current corporations from the allNames table
			while( rs.next() ) {
				
				//Store the names 
				String name = rs.getString("name");
				String market = rs.getString("market");
				String id = rs.getString( "id" );
				
				//Hash map to bind names and id's from the database
				nameToId.put( name, id );
				nameToMarket.put( name, market );
			}
			
			//For all securities, create SQL code for insertion to the database, using 
			//Values from stocks
			for( ParserStock s : stocks ) {
				
				String companyId = nameToId.get( s.name );
				
				SQLtoInsert.append( "INSERT INTO " + SQL_STOCKPRICE_HIS + " VALUES (" + 
									addApo( companyId ) + "," + //Name (id)
									addApo( Integer.toString( s.volume ) ) + ", " +//volume
									addApo( Double.toString( s.lastClose ) ) + ", " +//last close price
									addApo( Double.toString( s.buy ) ) + ", " +//last close price
									addApo( Double.toString( s.sell ) ) + ", " +//last close price
									addApo("5000-03-03 19:29:00") +//Date, TODO: FIX REAL TIME!
									" ); " );
				
				//Send to DB!
				writer.getStatement().execute( SQLtoInsert.toString() );
			}
			
			reader.getConnection().close();
			writer.getConnection().close();
			
			return true;
			
		//Error handling!
		} catch ( SQLException e ) {
			
			System.out.println( "ERROR: Injector: injectStockData! SQL Exception" );
			return false; //Insertion not successful
			
		} catch ( ClassNotFoundException cnfE ) {
			
			System.out.println( "ERROR: Injector: injectStockData! ClassNotFoundException" );
			return false; //Insertion not successful
			
		} catch ( InstantiationException iE ) {
			
			System.out.println( "ERROR: Injector: injectStockData! InstantiationException" );
			return false; //Insertion not successful
			
		} catch ( IllegalAccessException iaE ) {
			
			System.out.println( "ERROR: Injector: injectStockData! IllegalAccessException" );
			return false; //Insertion not successful
		}
	}
	
	/**
	 * Add apostrophe
	 * Surround str with 'str'
	 * 
	 * @param str
	 * @return
	 */
	private static String addApo( String str ) {
		
		return "'" + str + "'";
	}
	
	/**
	 * Add all new stock
	 * 
	 * Loop through all stocks given in the parameter, if they are not already in 
	 * the DB, add them!
	 * 
	 * @param url
	 * @param stocks
	 */
	private static boolean addAllNewStocks( String url, ParserStock[] stocks ) {
		
		//Connections for reading and writing to the DB
		ConnectionCreator reader = ConnectionCreator.getWriteConnection(url, dbuser, dbpass, ConnectionCreator.READ);
		ConnectionCreator writer = ConnectionCreator.getWriteConnection(url, dbuser, dbpass, ConnectionCreator.WRITE);
		
		try {
			
			//Get all names currently in the DB
			ResultSet allNames = reader.getStatement().executeQuery( "SELECT * FROM " + SQL_STOCKNAME_TAB );
			
			//Save the names
			ArrayList<String> existingNames = new ArrayList<String>();
			
			while( allNames.next() )
				existingNames.add( allNames.getString("name") );
			
			//Add all SQL insertions to one chunk
			StringBuilder sb = new StringBuilder();
			
			//Loop through all stocks given in the parameter, if they are not already in 
			//the DB, add them with SQL!
			for( ParserStock s : stocks ) {
				
				//If this stock is not already in there, then add it!
				if( !existingNames.contains( s.name ) )
					sb.append( "INSERT INTO " + SQL_STOCKNAME_TAB + " VALUES (" + "'0'," + addApo( s.name ) + ", " + addApo( s.market ) + ");" );
			}
			
			//Send to DB, if not empty
			if( !sb.toString().equals( "" ) )
				writer.getStatement().execute( sb.toString() );
			
			//Close connections
			reader.getConnection().close();
			writer.getConnection().close();
			
		} catch( SQLException e ) {
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Updates the market associated to a stock
	 * 
	 * +If a row with the same name as name exists
	 * 		=> Update the market
	 * 
	 * Should only need to run once every day, first run of the morning.
	 * 
	 * This will ONLY update stocks that are already in the database, and NOT add new ones.
	 * 
	 * @param name
	 * @param market
	 */
	public boolean updateAllMarkets( ParserStock[] stocks ) {
		
		//Get a list of all current companies
		//Connections for reading and writing to the DB
		ConnectionCreator reader = ConnectionCreator.getWriteConnection(url, dbuser, dbpass, ConnectionCreator.READ);
		ConnectionCreator writer = ConnectionCreator.getWriteConnection(url, dbuser, dbpass, ConnectionCreator.WRITE);
		
		try {

			//SQL Result:
			StringBuilder sb = new StringBuilder();
			
			//Get all names currently in the DB
			ResultSet allNames = reader.getStatement().executeQuery( "SELECT * FROM " + SQL_STOCKNAME_TAB );
			
			//Loop through all existing stock names
			while( allNames.next() ) {
				
				String name 	= allNames.getString( "name" );
				String market 	= allNames.getString( "market" );
				
				//Find the matching stock in stocks
				for( ParserStock stock : stocks )
					if( name.equals( stock.name ) )
						//The name matches, if the market does NOT match, change the market in the DB
						if( !market.equals( stock.market ) )
							sb.append( "UPDATE " + SQL_STOCKNAME_TAB + " SET market=" + addApo(stock.market) + " WHERE name=" + addApo(name) + "; " );
			}
			
			//Send to DB, if not empty
			if( !sb.toString().equals( "" ) )
				writer.getStatement().execute( sb.toString() );
			
			reader.getConnection().close();
			writer.getConnection().close();
			
		} catch( SQLException e ) {
			
			e.printStackTrace();
			return false;
		} 
		
		//For each company, IFF the market is not equal to the market given by param stocks
		// => update the market
		
		return true;
	}
}