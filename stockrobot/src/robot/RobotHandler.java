package robot;

import java.util.LinkedList;
import java.util.List;

import algorithms.IAlgorithm;
import portfolio.IPortfolioHandler;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: RobotHandler.java
 * Description:
 * RobotHandler takes care of running the stock algorithms 
 */
public class RobotHandler {

	//Used to retrieve the algorithms and portfolios that are used
	private IPortfolioHandler portfolioHandler;
	private List<AlgorithmAdapter> algorithms;
	
		
	//TODO Implement a shedueler for the handler
	
	/**
	 * Create a handler thats runs each algorithms in the
	 * portfolios frequently.
	 * 
	 * @param portfolioHandler where to get used portfolios
	 */
	public RobotHandler(IPortfolioHandler portfolioHandler){
		
		this.portfolioHandler = portfolioHandler;
		algorithms = new LinkedList<AlgorithmAdapter>();
	}

	/**
	 * 
	 * Start the handler. Nothing happens if 
	 * handler is already running.
	 * 
	 * @return true if started
	 */
	public void runAlgorithms(){
		
		//TODO make code to run algorithms
	}
	
	/**
	 * 
	 * @author Mattias Markehed
	 * mattias.markehed@gmail.com
	 * 
	 * Description:
	 * An adapter to make algorithms runnable in threads.
	 * This is done by running IAlgorithms update() method.
	 * 
	 * Additional functionality is to see it the algorithm have 
	 * finished and how long the run took.
	 */
	private class AlgorithmAdapter implements Runnable{
		
		public static final long NON_VALID_TIME = -1;
		
		private IAlgorithm algorithm;
		private boolean running = false;
		private long time = NON_VALID_TIME;
		
		/**
		 * Creates an adapter class for IAlgorithms so they can be
		 * run with threads.
		 * 
		 * @param algorithm the algorithm to run
		 */
		public AlgorithmAdapter(IAlgorithm algorithm){
			
			this.algorithm = algorithm;
		}
		
		/**
		 * Get the time it took to run the algorithm
		 * 
		 * @return >= 0 if finished : else < 0
		 */
		public long getLastRunTime(){
			return time;
		}
		
		/**
		 * Check if the algorithm is currently running
		 * 
		 * @return true if algorithm started but not finished : else false
		 */
		public boolean isRunning(){
			return running;
		}
		
		@Override
		public void run() {
			time = NON_VALID_TIME;
			
			running = true;
			long startTime = System.currentTimeMillis();
			algorithm.update();
			time = System.currentTimeMillis() - startTime;
			running = false;
		}
		
	}
	
}
