package portfolio;

import java.util.List;

public interface IPortfolioHandler {

	IPortfolio createNewPortfolio(String name);
	public List<IPortfolio> getPortfolios();
	
	boolean removePortfolio(IPortfolio portfolio);
	
}
