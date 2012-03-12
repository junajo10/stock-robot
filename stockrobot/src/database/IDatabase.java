package database;

import java.sql.ResultSet;

public interface IDatabase {
	ResultSet askQuery(String query, String database);
	
	boolean createDatabase(String databaseName);
	// TODO: massa mer
}
