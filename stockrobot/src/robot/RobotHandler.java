package robot;

import portfolio.IPortfolioHandler;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: RobotHandler.java
 * Description:
 * RobotHandler takes care of running the stock algorithms 
 * on a frequent basis. 
 */
public class RobotHandler {

	//Used to retrieve the algorithms and portfolios that are used
	private IPortfolioHandler portfolioHandler;
	
	
	private boolean isRunning = false;
	
	//TODO Implement a shedueler for the handler
	
	/**
	 * Create a handler thats runs each algorithms in the
	 * portfolios frequently.
	 * 
	 * @param portfolioHandler where to get used portfolios
	 */
	public RobotHandler(IPortfolioHandler portfolioHandler){
		
		this.portfolioHandler = portfolioHandler;
	}

	/**
	 * 
	 * Start the handler. Nothing happens if 
	 * handler is already running.
	 * 
	 * @return true if started
	 */
	public void startHandler(){
		
		if(!isRunning){
			isRunning = true;
		}
	}

	/**
	 * Stop the handler after current run of
	 * algorithms are finished.
	 */
	public void stopHandler(){
		isRunning = false;
	}
	
	/**
	 * The loop that starts and runs the algorithms
	 * for each portfolio.
	 */
	private void runLoop(){
		
	}
	
}
