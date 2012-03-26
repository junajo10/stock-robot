package robot;

import generic.Log;
import portfolio.IPortfolioHandler;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: RobotScheduler.java
 * Description:
 * RobotHandler takes care of running the stock algorithms
 * on a frequent basis. 
 */
public class RobotScheduler implements Runnable{

	private RobotHandler handler;	
	
	public static final long MILLI_SECOND = 1;
	public static final long SECOND = 1000*MILLI_SECOND;
	public static final long MINUTE = 60*SECOND;
	
	private boolean isRunning = false;
	private boolean pause = false;
	private long freq = 0;

	public RobotScheduler(IPortfolioHandler portfolioHandler){
		handler = new RobotHandler(portfolioHandler);
	}
		
	/**
	 * Completely stops the runner after current run is through
	 * 
	 * @return true if stopped else false if already stopped
	 */
	public boolean stop(){
			
		boolean result = false;
		if(!pause)
			Log.instance().log(Log.TAG.NORMAL , "RobotScheduler Stoped!" );
			result = pause = true;	
		return result;
	}
		
	/**
	 * Pauses the runner.
	 * gets uncaused by start() 
	 * 
	 * @return true if runner paused else false
	 */
	public boolean pause(){
		
		boolean result = false;
		if(!pause){
			Log.instance().log(Log.TAG.VERBOSE , "RobotScheduler pause!" );
			result = pause = true;
		}
			
		return result;
	}
		
	/**
	* Unpauses the runner. Does nothing if parser isn't paused.
	* 
	* @return return true if runner unpauses else false
	*/
	public boolean unpause(){
			
		boolean result = false;
		if(pause){
			Log.instance().log(Log.TAG.VERBOSE , "RobotScheduler unpause!" );
			pause = false;
			result = true;
		}
		return result;
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
	
	@Override
	public void run() {
		isRunning = true;
		Log.instance().log(Log.TAG.VERY_VERBOSE , "RobotScheduler!" );
		while(isRunning){
			
			//TODO make run interface to avoid polling
			while(pause){
				try {
					Thread.sleep(RobotScheduler.SECOND * 60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			Log.instance().log(Log.TAG.VERY_VERBOSE ,"RobotScheduler: RUN!" );
			RobotScheduler.this.handler.runAlgorithms();
			try {
				Thread.sleep(freq);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
