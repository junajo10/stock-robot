package model.scraping.scheduler;

/**
 *
 * Abstract interface for the Scheduler.
 * @author Erik,kristian
 *
 */
public interface IScheduler {
	
	/**
	 * When called, shouldRun will return true or false depending on how
	 * 
	 * @return
	 */
	public boolean shouldRun();
	
	
	/**
	 * Ignore time, can be set to true or false,
	 * true will make the method check whether to run
	 * false will always return false
	 * 
	 * @param ignore
	 */
	public void setShouldRun(boolean run);
	public boolean getShouldRun();
	
	
	/**
	 * Number of milliseconds until the next parse should occur.
	 * @return Number of milliseconds until the next parse should occur.
	 */
	public long timeUntilNextParse();
}