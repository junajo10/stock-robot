package model.scraping.scheduler;

import java.util.Date;

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
	 * A date that represents what the start time for a specific data source should be
	 * 
	 * @param start
	 * @return
	 */
	public void setStartTime( Date start );
	
	/**
	 * A date that represents what the end time for a specific data source should be
	 * 
	 * @param end
	 * @return
	 */
	public void setEndTime( Date end );
	
	/**
	 * Ignore time, can be set to true or false,
	 * true will make the method check whether to run
	 * false will always return false
	 * 
	 * @param ignore
	 */
	public void shouldRun(boolean run);
	
	
	/**
	 * Number of milliseconds until the next parse should occur.
	 * @return Number of milliseconds until the next parse should occur.
	 */
	public long timeUntilNextParse();
}