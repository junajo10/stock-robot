package model.portfolio;


import java.util.List;

import utils.observer.IObservable;

/**
 * @author Daniel
 *
 */
public interface IPortfolioHandler extends IObservable {

	public final static String MSG_PORTFOLIO_ADDED = "msgPortfolioAdded";
	public final static String MSG_PORTFOLIO_REMOVED = "msgPortfolioRemoved";
	
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
	List<IPortfolio> getPortfolios();
	
	/**
	 * Deletes a portfolio
	 * @param portfolio The portfolio to be deleted
	 * @return Returns true if it was successfully removed
	 */
	boolean removePortfolio(IPortfolio portfolio);

	List<String> getAlgorithmNames();
	
	boolean setAlgorithm(IPortfolio p, String algorithmName);
	
}
