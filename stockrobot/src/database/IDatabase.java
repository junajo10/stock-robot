package database;

import java.sql.ResultSet;

public interface IDatabase {
	ResultSet askQuery(String query);
	boolean statement(String statment);
	
	boolean createDatabase(String databaseName);

}
