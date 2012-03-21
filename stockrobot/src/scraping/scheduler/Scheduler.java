package scraping.scheduler;

import java.sql.Date;

/**
 * Class that can be asked whether something should happen or not
 * based on user entered dates.
 * 
 * Can be deactivated with ignoreTime(true), and activated with ignoreTime(false)
 * 
 * @author kristian
 *
 */
public class Scheduler implements IScheduler {

	private Date startTime;
	private Date endTime;
	private boolean shouldRun = true;
	
	/**
	 * When called, shouldRun will return true or false depending on how
	 * 
	 * @return
	 */
	@Override
	public boolean shouldRun() {
		
		//For now, always run!
		return true;
		
		/*
		if( !shouldRun )
			return false;
		
		return true;
		*/
	}

	@Override
	public void setStartTime(Date start) {
		
		startTime = start;
	}

	@Override
	public void setEndTime(Date end) {
		
		endTime = end;
	}

	@Override
	public void ignoreTime(boolean ignore) {
		
		shouldRun = !ignore;
	}
}