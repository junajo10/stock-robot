package robot;

<<<<<<< HEAD
import portfolio.IPortfolioHandler;
=======
import javax.swing.text.StyledEditorKit;

import portfolio.IPortfolioHandler;
import portfolio.PortfolioHandler;
>>>>>>> 0102877cb30f9bc30ad8ca1db17d71d0a8b449e8

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
	
<<<<<<< HEAD
	private boolean isRunning = false;
	private boolean pause = false;
	private long freq = 0;
=======
	public RobotScheduler(IPortfolioHandler portfolioHandler){
		handler = new RobotHandler(portfolioHandler);
		
		tRunner = new Thread( new AlgorithmRunner() );
	}
>>>>>>> 0102877cb30f9bc30ad8ca1db17d71d0a8b449e8
	
	public RobotScheduler(IPortfolioHandler portfolioHandler){
		handler = new RobotHandler(portfolioHandler);
	}
		
	/**
	 * Completely stops the runner after current run is through
	 * 
	 * @return true if stopped else false if already stopped
	 */
<<<<<<< HEAD
	public boolean stop(){
			
		boolean result = false;
		if(!pause)
			result = pause = true;	
		return result;
=======
	public void startScheduler(){
		isRunning = true;
		tRunner.start();
>>>>>>> 0102877cb30f9bc30ad8ca1db17d71d0a8b449e8
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
		while(isRunning){
			System.out.println("run algorithms");
			
<<<<<<< HEAD
			//TODO make run interface to avoid polling
			while(pause){
=======
			System.out.println( "RobotScheduler: RUN!" );
			
			while(isRunning){
				
				System.out.println( "RobotScheduler!" );
				
				//TODO make run interface to avoid polling
				while(pause){
					
					System.out.println( "RobotScheduler pause!" );
					try {
						Thread.sleep(RobotScheduler.SECOND * 20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				RobotScheduler.this.handler.runAlgorithms();
>>>>>>> 0102877cb30f9bc30ad8ca1db17d71d0a8b449e8
				try {
					Thread.sleep(RobotScheduler.SECOND * 5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			RobotScheduler.this.handler.runAlgorithms();
			try {
				Thread.sleep(freq);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
