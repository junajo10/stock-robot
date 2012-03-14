package robot;

import java.sql.ResultSet;

import stock.IStock;

// Interface to the framework from algorithms
public interface IRobot_Algorithms {
	int buyStock(IStock s, int amount);
	int buyStock(IStock s, int amount, int atLeastN);
	
	int sellStock(IStock s, int amount);
	int sellStock(IStock s, int amount, int atLeastN);
	
	int getCourtagePrice(IStock s, int amount, boolean buying);
	
	
	boolean reportToUser(String message);
	
	ResultSet askQuery(String query);
	
	
}
