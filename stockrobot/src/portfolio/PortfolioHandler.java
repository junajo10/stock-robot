package portfolio;

public class PortfolioHandler implements IPortfolioHandler{

	private static PortfolioHandler instance = null;

	IPortfolio[] arrayOfPortfolios;

	private PortfolioHandler() {
		// TODO: Hämta portfolios från databasen
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
		//endast om inga pengar finns kvar i den
		
		
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
