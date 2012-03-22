package robot;

import portfolio.PortfolioHandler;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: RobotScheduler.java
 * Description:
 * RobotHandler takes care of running the stock algorithms
 * on a frequent basis. 
 */
public class RobotScheduler {

	private RobotHandler handler;
	private boolean isRunning = false;
	
	public static final long MILLI_SECOND = 1;
	public static final long SECOND = 1000*MILLI_SECOND;
	public static final long MINUTE = 60*SECOND;
	
	public RobotScheduler(PortfolioHandler portfolioHandler){
		handler = new RobotHandler(portfolioHandler);
	}
	
	/**
	 * Stop the handler after current run of
	 * algorithms are finished.
	 */
	public void stopScheduler(){
		isRunning = false;
	}
	
	/**
	 * Starts the scheduler.
	 * does nothing if scheduler is already running
	 */
	public void startScheduler(){
		isRunning = false;
	}
	
	/**
	 * The loop that starts and runs the algorithms
	 * for each portfolio.
	 */
	private void runLoop(){
		
		///TODO implement runloop
	}
	
	
}
