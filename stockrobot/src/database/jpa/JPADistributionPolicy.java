package database.jpa;

import java.util.List;

import org.apache.openjpa.slice.DistributionPolicy;

import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.AlgorithmSetting;
import database.jpa.tables.PortfolioEntitys;
import database.jpa.tables.PortfolioHistory;
import database.jpa.tables.PortfolioInvestment;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;
import database.jpa.tables.StocksToWatch;

public class JPADistributionPolicy implements DistributionPolicy {

    public String distribute(Object p, List<String> slices, Object context) {
		if (p instanceof AlgorithmEntitys)
			return "Portfolio";
		else if (p instanceof PortfolioEntitys)
			return "Portfolio";
		else if (p instanceof PortfolioHistory)
			return "Portfolio";
		else if (p instanceof PortfolioInvestment)
			return "Portfolio";
		else if (p instanceof StocksToWatch)
			return "Portfolio";
		
		else if (p instanceof StockNames)
			return "Stocks";
		else if (p instanceof StockPrices)
			return "Stocks";
		
		throw new IllegalArgumentException("Missed object " + p);
	}
}
