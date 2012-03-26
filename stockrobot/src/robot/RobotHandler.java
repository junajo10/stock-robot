package robot;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Handler;

import algorithms.IAlgorithm;
import portfolio.IPortfolio;
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
		
		System.out.println( "Run Algorithms!!!" );
		
		//TODO make code to run algorithms
		List<AlgorithmAdapter> stillRunning = new LinkedList<AlgorithmAdapter>();
		for(AlgorithmAdapter a : algorithms){
			
			if(a.isRunning())
				stillRunning.add(a);
		}
		algorithms = new LinkedList<AlgorithmAdapter>();
		algorithms.addAll(stillRunning);
		
		//Only adds algorithms that isnt curently running
		for(IPortfolio portfolio : portfolioHandler.getPortfolios()){
			boolean found = false;
			for(AlgorithmAdapter adapter : stillRunning){
				if(adapter.getPortfolio() == portfolio){
					found = true;
				}
			}
			if(!found){
				algorithms.add(new AlgorithmAdapter(portfolio));
			}
		}
		//TODO runt the treads
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
		
		private IPortfolio portfolio;
		private boolean running = false;
		private long time = NON_VALID_TIME;
		private long startTime = NON_VALID_TIME;
		
		/**
		 * Creates an adapter class for IAlgorithms so they can be
		 * run with threads.
		 * 
		 * @param algorithm the algorithm to run
		 */
		public AlgorithmAdapter(IPortfolio portfolio){
			
			this.portfolio = portfolio;
		}
		
		public IPortfolio getPortfolio(){
			return portfolio;
		}
		
		/**
		 * Get the time it took to run the algorithm
		 * 
		 * @return >= 0 if finished : else < 0
		 */
		public long getRunTime(){
			
			long runTime = NON_VALID_TIME;
			
			if(time != NON_VALID_TIME){
				runTime = time;
			}else if(startTime != NON_VALID_TIME){
				runTime = System.currentTimeMillis() - startTime;
			}
			
			return runTime;
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
			startTime = System.currentTimeMillis();
			portfolio.getAlgorithm().update();
			time = System.currentTimeMillis() - startTime;
			running = false;
		}
		
	}
	
}
