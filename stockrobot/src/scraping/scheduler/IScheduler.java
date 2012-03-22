package scraping.scheduler;

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
	 * true will make the shouldRun method to always return true
	 * false will make shouldRun to return true according to time of day 
	 * 
	 * @param ignore
	 */
	public void ignoreTime(boolean ignore);
}