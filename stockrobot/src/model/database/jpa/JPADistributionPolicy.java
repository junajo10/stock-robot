package model.database.jpa;

import java.util.List;

import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;

import org.apache.openjpa.slice.DistributionPolicy;


/**
 * @author Daniel
 *
 * This will be in charge of giving back the right database to make changes to.
 */
public class JPADistributionPolicy implements DistributionPolicy {

	public static String portfolioDatabase = "Portfolio";
	public static String stockDatabase = "Stocks";
	
    public String distribute(Object p, List<String> slices, Object context) {
    	if (p instanceof StockNames)
			return stockDatabase;
		else if (p instanceof StockPrices)
			return stockDatabase;
		
		return portfolioDatabase;
	}
}
