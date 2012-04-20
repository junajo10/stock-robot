package model.database.jpa;

//import database.jpa.tables.AlgorithmEntity;

/**
 * To be able to test ASTRo running with real stock data through RobotScheduler, 
 * here we create a portfolio and an algorithm for it.
 * 
 * @author kristian
 *
 */
public class InitiateAlgorithm1 {
	
	public static void main(String[] args) {
		/*
		IJPAHelper help = JPAHelper.getInstance();
		
		List<PortfolioEntity> portfolios = help.getAllPortfolios();
		
		if( portfolios.size() == 0 ) {
			
			//Create new portfolio and store it to DB
			PortfolioEntity port = new PortfolioEntity( "Port1" );
			help.storeObject( port );
			
			//Create new algorithm and store it to DB
			AlgorithmEntity algo = new AlgorithmEntity( "Algo1", "algorithms.TestAlgorithm" );
			port.setAlgorithm( algo );
			help.updateObject( port );
			
			//Add some money to the account!
			help.investMoney(100000, port);
		}
		*/	
	}
}