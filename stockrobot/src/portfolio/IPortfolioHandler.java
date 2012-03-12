package portfolio;

public interface IPortfolioHandler {

	IPortfolio createNewPortfolio(String name);
	IPortfolio[] getPortfolios();
	
	boolean removePortfolio(IPortfolio portfolio);
	
	IPortfolioHandler getInstance();
	
	
}
