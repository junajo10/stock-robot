package model.robot;

import java.net.UnknownHostException;

import utils.global.Log;
import utils.global.Log.TAG;
import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.portfolio.IPortfolio;
import model.portfolio.IPortfolioHandler;

/**
 * Description:
 * RobotHandler takes care of running the stock algorithms
 * on a frequent basis.
 * 
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 */
public class RobotScheduler implements Runnable{

	//private RobotHandler handler;	
	IPortfolioHandler portfolioHandler;
	
	public static final long MILLI_SECOND = 1;
	public static final long SECOND = 1000*MILLI_SECOND;
	public static final long MINUTE = 60*SECOND;

	private boolean isRunning = false;
	private volatile boolean pause = false;
	private boolean isStoped = false;
	
	public static boolean stopThis = false;
	
	@SuppressWarnings("unused")
	private long freq = 0;
	private long pauseLength = SECOND;
	private boolean usingServer = false;

	RobotSchedulerClient client;

	public RobotScheduler(IPortfolioHandler portfolioHandler){
		//handler = new RobotHandler(portfolioHandler);
		this.pauseLength = 5*SECOND;
		usingServer = false;
		this.portfolioHandler = portfolioHandler;
	}
	public RobotScheduler(IPortfolioHandler portfolioHandler, String host, int port) throws UnknownHostException {
		//handler = new RobotHandler(portfolioHandler);
		this.portfolioHandler = portfolioHandler;
		usingServer = true;

		client = new RobotSchedulerClient(this, host, port);
		client.start();
	}
	/**
	 * Completely stops the runner after current run is through
	 * 
	 * @return true if stopped else false if already stopped
	 */
	public synchronized boolean stop(){

		boolean result = false;
		if(isRunning){
			Log.log(Log.TAG.NORMAL , "RobotScheduler Stoped!" );
			isRunning = pause = false;	
			result = true;

		}
		return isStoped;
	}

	/**
	 * Pauses the runner.
	 * gets uncaused by start() 
	 * 
	 * @return true if runner paused else false
	 */
	public synchronized boolean pause(){

		synchronized (this) {
			pause = true;
		}
		boolean result = false;
		if(!pause && isRunning){
			Log.log(Log.TAG.VERBOSE , "RobotScheduler pause!" );
			result = pause = true;
		}

		return result;
	}

	/**
	 * Unpauses the runner. Does nothing if parser isn't paused.
	 * To make the unpause instant. Run interrupt on the thread running the scheduler.
	 * 
	 * @return return true if runner unpauses else false
	 */
	public synchronized boolean unpause(){
		if (pause) {
			Log.log(Log.TAG.VERY_VERBOSE ,"Wakeing RobotScheduler" );
			synchronized (this) {
				pause = false;
			}
		}
		boolean result = false;
		if(pause && isRunning){
			Log.log(Log.TAG.VERBOSE , "RobotScheduler unpause!" );
			pause = false;
			result = true;
		}
		return result;
	}

	/**
	 * @return true if paused
	 */
	public boolean isPaused(){

		return pause;
	}

	/**
	 * Set the update frequency of the scheduler. The algorithms will be run once
	 * every milli second freq.
	 * 
	 * @param freq frequency in milli seconds
	 */
	public void setUpdateFrequency(long freq){
		this.freq = freq; 
	}

	/**
	 * Set the sleep time of the thread when paused. 
	 * 
	 * @param length time in milli seconds
	 */
	public void setPauseLength(long length){
		pauseLength	= length;
	}

	@Override
	public void run() {
		isRunning = true;
		Log.log(Log.TAG.VERY_VERBOSE , "RobotScheduler!" );
		while(!stopThis){

			while (pause) {
				synchronized (this) {
					try {
						Log.log(Log.TAG.VERY_VERBOSE ,"Putting RobotScheduler to sleep" );
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if (usingServer) {
				Log.log(Log.TAG.VERY_VERBOSE ,"RobotScheduler: RUN using client server!" + JPAHelper.getInstance().getAllStockPrices().size() + " stockPrices");
				runAlgorithms();

				IJPAHelper jpaHelper = JPAHelper.getInstance();
				jpaHelper.getEntityManager().evictAll();
				
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			else {
				Log.log(Log.TAG.VERY_VERBOSE ,"RobotScheduler: RUN!" );
				
				
				runAlgorithms();

				try {
					Thread.sleep(pauseLength);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		isStoped = true;
	}
	
	public void runAlgorithms() {
		for (IPortfolio p : portfolioHandler.getPortfolios())
			p.updateAlgorithm();
	}

	/**
	 * This method is only called by {@link RobotSchedulerClient}
	 */
	public void doWork() {
		
		synchronized (this) {
			notify();
		}
	}
	public void cleanup() {
		if (client != null) {
			client.cleanup();
		}
	}
}
