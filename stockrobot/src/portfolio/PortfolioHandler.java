package portfolio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.IDatabase;
/**
 * @author Daniel
 *
 */
//TODO: Implement with JDO when database system supports it 
public class PortfolioHandler implements IPortfolioHandler{

	private static PortfolioHandler instance = null;
	private IDatabase db;
	List<IPortfolio> listOfPortfolios = new ArrayList<IPortfolio>();

	private PortfolioHandler(IDatabase db) {
		this.db = db;
		
		ResultSet result = db.askQuery("SELECT * from portfolios");
		
		try {
			while (result.next()) {
				listOfPortfolios.add(new Portfolio(result.getInt("portfolioId"), db));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public IPortfolio createNewPortfolio(String name) {
		return new Portfolio(name, db);
	}

	@Override
	public List<IPortfolio> getPortfolios() {
		return listOfPortfolios;
	}

	@Override
	public boolean removePortfolio(IPortfolio portfolio) {
		ResultSet result = db.askQuery("SELECT balance from portfolios WHERE portfolioId = " + portfolio.getPortfolioId());
		
		try {
			if (result.next()) {
				if (result.getInt("balance") == 0) {
					listOfPortfolios.remove(portfolio);
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static IPortfolioHandler getInstance() {
		if(instance == null) {
			instance = new PortfolioHandler(null);
		}
		return instance;
	}
}
