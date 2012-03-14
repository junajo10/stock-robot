package portfolio;

public class PortfolioHandler implements IPortfolioHandler{

	private static PortfolioHandler instance = null;

	IPortfolio[] arrayOfPortfolios;

	private PortfolioHandler() {
		// TODO: Get all portfolios from the database
	}
	@Override
	public IPortfolio createNewPortfolio(String name) {
		return null;
	}

	@Override
	public IPortfolio[] getPortfolios() {
		return arrayOfPortfolios;
	}

	@Override
	public boolean removePortfolio(IPortfolio portfolio) {
		// Only possible if no money left in portfolio
		
		return false;
	}
	@Override
	public IPortfolioHandler getInstance() {
		if(instance == null) {
			instance = new PortfolioHandler();
		}
		return instance;
	}
}
