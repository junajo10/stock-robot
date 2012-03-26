package database.jpa;

import java.util.List;

import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioEntitys;

/**
 * To be able to test ASTRo running with real stock data through RobotScheduler, 
 * here we create a portfolio and an algorithm for it.
 * 
 * @author kristian
 *
 */
public class InitiateAlgorithm1 {
	
	public static void main(String[] args) {
		
		JPAHelper help = JPAHelper.getInstance();
		
		List<PortfolioEntitys> portfolios = help.getAllPortfolios();
		
		if( portfolios.size() == 0 ) {
			
			//Create new portfolio and store it to DB
			PortfolioEntitys port = new PortfolioEntitys( "Port1" );
			help.storeObject( port );
			
			//Create new algorithm and store it to DB
			AlgorithmEntitys algo = new AlgorithmEntitys( "Algo1", "algorithms.TestAlgorithm" );
			port.setAlgorithm( algo );
			help.updateObject( port );
		}	
	}
}