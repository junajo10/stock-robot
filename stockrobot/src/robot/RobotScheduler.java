package robot;

import javax.swing.text.StyledEditorKit;

import portfolio.IPortfolioHandler;
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
	
	private AlgorithmRunner aRunner;
	private Thread tRunner;
	
	private boolean isRunning = false;
	
	public static final long MILLI_SECOND = 1;
	public static final long SECOND = 1000*MILLI_SECOND;
	public static final long MINUTE = 60*SECOND;
	
	public RobotScheduler(IPortfolioHandler portfolioHandler){
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
		isRunning = true;
	}
	
	/**
	 * The loop that starts and runs the algorithms
	 * for each portfolio. This clas has to be run in a 
	 * thread to work correctly.
	 */
	private class AlgorithmRunner implements Runnable{

		private boolean isRunning = true;
		private boolean pause = false;
		
		/**
		 * Completely stops the runner after current run is through
		 * 
		 * @return true if stopped else false if already stopped
		 */
		public boolean stop(){
			
			boolean result = false;
			if(!pause)
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
			if(!pause)
				result = pause = true;
			
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
				pause = false;
				result = true;
			}
			return result;
		}
		
		@Override
		public void run() {
			
			System.out.println( "RobotScheduler: RUN!" );
			
			while(isRunning){
				
				System.out.println( "RobotScheduler!" );
				
				//TODO make run interface to avoid polling
				while(pause){
					
					System.out.println( "RobotScheduler pause!" );
					try {
						Thread.sleep(RobotScheduler.SECOND * 5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				RobotScheduler.this.handler.runAlgorithms();
				try {
					Thread.sleep(RobotScheduler.MINUTE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
}
