package robot;

import java.sql.ResultSet;

import database.jpa.tables.StockPrices;


// Interface to the framework from algorithms
public interface IRobot_Algorithms {
	int buyStock(StockPrices s, int amount);
	int buyStock(StockPrices s, int amount, int atLeastN);
	
	int sellStock(StockPrices s, int amount);
	int sellStock(StockPrices s, int amount, int atLeastN);
	
	int getCourtagePrice(StockPrices s, int amount, boolean buying);
	
	
	boolean reportToUser(String message);
	
	ResultSet askQuery(String query);
	
	
}
