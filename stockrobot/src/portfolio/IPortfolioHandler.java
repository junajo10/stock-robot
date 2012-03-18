package portfolio;

import java.util.List;

/**
 * @author Daniel
 *
 */
public interface IPortfolioHandler {

	/**
	 * Creates a new portfolio with default settings, and a given name
	 * @param name The name of the portfolio this must be unique
	 * @return Returns the created portfolio
	 */
	IPortfolio createNewPortfolio(String name);
	
	/**
	 * Will give a list of all the portfolios currently in the portfoliohandler
	 * @return A list of portfolios
	 */
	public List<IPortfolio> getPortfolios();
	
	/**
	 * Deletes a portfolio
	 * @param portfolio The portfolio to be deleted
	 * @return Returns true if it was successfully removed
	 */
	boolean removePortfolio(IPortfolio portfolio);
	
	/**
	 * Will start a GUI for seting up a given portfolio
	 * @param portfolio The portfolio to be setup
	 * @return Returns true if new changes was accepted
	 */
	boolean setupPortfolio(IPortfolio portfolio);
}
