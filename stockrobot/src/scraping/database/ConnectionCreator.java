package scraping.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionCreator {
	
	public static final String READ = "read";
	public static final String WRITE = "write";
	
	private Connection conn = null;
	private Statement  st 	= null;
	
	private ConnectionCreator( Connection c, Statement s ) {
		
		conn = c;
		st 	 = s;
	}
	
	public Connection getConnection() {
		
		return conn;
	}
	
	public Statement getStatement() {
		
		return st;
	}
	
	public static ConnectionCreator getWriteConnection( String url, String user, String pass, String type ) {
		
		Connection c = null;
		Statement s  = null;
		
		try {
			
			c = DriverManager.getConnection(url,user,pass);
			
			if( type == READ ) {
			
				s = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
				
			} else if ( type == WRITE ) {
				
				s = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				
			} else {
				
				System.out.println( "ConnectionCreator, unsupported type!" );
			}
		
		} catch( SQLException e ) {
			
			e.printStackTrace();
		}
		
		ConnectionCreator ret = new ConnectionCreator( c, s ); 
		
		return ret;
	}
}