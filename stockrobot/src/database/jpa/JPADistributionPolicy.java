package database.jpa;

import java.util.List;

import org.apache.openjpa.slice.DistributionPolicy;

import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import database.jpa.tables.StocksToWatch;

/**
 * @author Daniel
 *
 * This will be in charge of giving back the right database to make changes to.
 */
public class JPADistributionPolicy implements DistributionPolicy {

	public static String portfolioDatabase = "Portfolio";
	public static String stockDatabase = "Stocks";
	
    public String distribute(Object p, List<String> slices, Object context) {
    	
		if (p instanceof AlgorithmEntitys)
			return portfolioDatabase;
		else if (p instanceof PortfolioEntitys)
			return portfolioDatabase;
		else if (p instanceof PortfolioHistory)
			return portfolioDatabase;
		else if (p instanceof PortfolioInvestment)
			return portfolioDatabase;
		else if (p instanceof StocksToWatch)
			return portfolioDatabase;
		
		else if (p instanceof StockNames)
			return stockDatabase;
		else if (p instanceof StockPrices)
			return stockDatabase;
		
		throw new IllegalArgumentException("Missed object " + p);
	}
}
