package portfolio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import database.IDatabase;

import stock.IStock;
import stock.Stock;

/**
 * @author Daniel
 *
 * An object of this class will hold one portfolio.
 */
//TODO: Implement with JDO when database system supports it 
public class Portfolio implements IPortfolio {
	private int portfolioId;
	private IDatabase db;
	private int algorithmId;
	private boolean stopBuying, stopSelling;
	
	/**
	 * Start up a existing portfolio
	 * @param portfolioId
	 * @param db
	 */
	public Portfolio(int portfolioId, IDatabase db) {
		this.portfolioId = portfolioId;
		this.db = db;
		this.algorithmId = getAlgorithmId();
	}
	/**
	 * Create a new portfolio
	 * @param name Name of the new portfolio
	 * @param db
	 */
	public Portfolio(String name, IDatabase db) {
		this.db = db;
		if (!db.statement("INSERT INTO portfolios (name, algorithmId) VALUES (" + name + ", -1)")) {
			//TODO: error name conflict, enter new name in some JOptionPane or something
		}
		this.algorithmId = -1;
	}
	@Override
	public List<Integer> getAvalibleStocks() {
		ResultSet result = db.askQuery("SELECT watchAllStocks FROM portfolios WHERE portfolioId = " + portfolioId);
		List<Integer> listOfStockIds = new ArrayList<Integer>();
		try {
			if (result.next()) {
				if (result.getBoolean("watchAllStocks")) {
					result.close();
					result = db.askQuery("SELECT id FROM allStockNames");
					
					while (result.next()) {
						listOfStockIds.add(result.getInt("id"));
					}
					return listOfStockIds;
				}
				else {
					result = db.askQuery("SELECT stock FROM portfolioStocksToWatch WHERE portfolioId = " + portfolioId);
					while (result.next()) {
						listOfStockIds.add(result.getInt("stock"));
					}
					return listOfStockIds;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<IStock> getCurrentStocks() {
		ResultSet result = db.askQuery("SELECT * FROM portfolioHistory WHERE portfolioId = " + portfolioId + " AND soldDate = NULL");
		LinkedList<IStock> currentStocks = new LinkedList<IStock>();
		try {
			while (result.next()) {
				currentStocks.add(new Stock(result.getInt("stock"), result.getTimestamp("buyDate")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return currentStocks;
	}

	@Override
	public List<IStock> getHistoryStocks() {
		ResultSet result = db.askQuery("SELECT * FROM portfolioHistory WHERE portfolioId = " + portfolioId + " AND soldDate != NULL");
		LinkedList<IStock> currentStocks = new LinkedList<IStock>();
		
		try {
			while (result.next()) {
				currentStocks.add(new Stock(result.getInt("stock"), result.getTimestamp("buyDate")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return currentStocks;
	}

	@Override
	public int getAlgorithmId() {
		ResultSet result = db.askQuery("SELECT algorithmId FROM portfolios WHERE portfolioId = " + portfolioId);
		
		try {
			if (result.next()) {
				return result.getInt("algorithmId");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return algorithmId;
	}

	@Override
	public boolean setAlgorithm(int algorithmId) {
		// TODO: Check if algorithm exists
		this.algorithmId = algorithmId;
		
		return false;
	}

	@Override
	public int getInvestedAmount() {
		ResultSet result = db.askQuery("SELECT SUM(amount) AS investedAmount FROM portfolioInvestment WHERE portfolioId = " + portfolioId + " AND INVESTED = True");
		int investedAmount = 0;
		try {
			if (result.next()) {
				investedAmount = result.getInt("investedAmount");
			}
			result.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return investedAmount;
	}

	@Override
	public int getUnusedAmount() {
		ResultSet result = db.askQuery("SELECT balance FROM portfolios WHERE portfolioId = " + portfolioId);
		int unsedAmount = 0;
		try {
			if (result.next()) {
				unsedAmount = result.getInt("balance");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return unsedAmount;
	}

	@Override
	public boolean investAmount(int n) {
		boolean result = db.statement("UPDATE portfolios SET balance = balance + " + n);
		
		if (result) {
			if (!db.statement("INSERT INTO portfolioInvestment VALUES (" + portfolioId + ", " + n + ");")) {
				//TODO Report error
			}
		}
		return result;
	}

	@Override
	public boolean setStocksToWatch(List<IStock> stocks) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stopBuying(boolean flag) {
		this.stopBuying = flag;
	}

	@Override
	public void stopSelling(boolean flag) {
		this.stopSelling = flag;
	}

	@Override
	public boolean isStopBuyingFlagSet() {
		return stopBuying;
	}

	@Override
	public boolean isStopSellingFlagSet() {
		return stopSelling;
	}
	@Override
	public String getName() {
		String name = null;
		
		ResultSet result = db.askQuery("SELECT name FROM portfolios WHERE portfolioId = " + portfolioId);
		
		try {
			if (!result.next()) {
				name = result.getString("name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return name;
	}

	@Override
	public int getPortfolioId() {
		return portfolioId;
	}
	
	public String toString() {
		return "AlgorithmId: " + getAlgorithmId() + "\nName: " + getName() + "\n";
	}
}
