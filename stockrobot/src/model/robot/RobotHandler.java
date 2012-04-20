package model.robot;


import java.util.LinkedList;
import java.util.List;

import model.portfolio.IPortfolio;
import model.portfolio.IPortfolioHandler;
import model.utils.Log;


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
	private List<AlgorithmThread> algorithms;
	
		
	/**
	 * Create a handler thats runs each algorithms in the
	 * portfolios frequently.
	 * 
	 * @param portfolioHandler where to get used portfolios
	 */
	public RobotHandler(IPortfolioHandler portfolioHandler){
		
		this.portfolioHandler = portfolioHandler;
		algorithms = new LinkedList<AlgorithmThread>();
	}

	/**
	 * 
	 * Start the handler. Nothing happens if 
	 * handler is already running.
	 * 
	 * @return true if started
	 */
	public void runAlgorithms(){
		
		Log.instance().log(Log.TAG.VERY_VERBOSE, "Running algorithms");
		
		List<AlgorithmThread> stillRunning = new LinkedList<AlgorithmThread>();
		
		//TODO make code to run algorithms
		for(AlgorithmThread a : algorithms){
			
			if(a.isRunning())
				stillRunning.add(a);
		}
		algorithms = new LinkedList<AlgorithmThread>();
		algorithms.addAll(stillRunning);
		
		//Only adds algorithms that isn't currently running
		for(IPortfolio portfolio : portfolioHandler.getPortfolios()){
			boolean found = false;
			for(AlgorithmThread adapter : stillRunning){
				if(adapter.getPortfolio() == portfolio){
					found = true;
				}
			}
			
			//TODO Make the treads rerunnable
			if(!found){
				AlgorithmThread aThread = new AlgorithmThread(portfolio);
				algorithms.add(aThread);
				aThread.start();
			}
		}
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
	private class AlgorithmThread extends Thread{
		
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
		public AlgorithmThread(IPortfolio portfolio){
			
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
