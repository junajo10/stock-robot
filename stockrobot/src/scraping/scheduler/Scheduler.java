package scraping.scheduler;

import java.util.Calendar;
import java.util.Date;

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
	private boolean _shouldRun = true;
	
	/**
	 * When called, shouldRun will return true or false depending on how
	 * 
	 * @return
	 */
	@Override
	public boolean shouldRun() {
			
			if( !_shouldRun )
				return false;
		
			Date d = new Date();
			
			//Only run all queries, scraping etc between 08:58 AM and 17:44 (44 to avoid getting 0's in prices after 17:30 avanza time)
			
			//Cases when it's now allowed to run:
			//If it's before 8AM
			//TODO Change getHours. The method is depricated
			if( d.getHours() < 8 )
				return false;
				
			//If it's 8 AM and before 8:58
			if( d.getHours() == 8 && d.getMinutes() < 58 )
				return false;
					
			//If it's after 17 PM
			if( d.getHours() > 17 )
				return false;
					
			//If it's 17 PM and after 17:47
			if( d.getHours() == 17 && d.getMinutes() > 44 )
				return false;
			
			//If saturday or sunday!
			if( d.getDay() == 6 || d.getDay() == 0 )
				return false;
			
			//Otherwise, just go for it!
			return true;
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
	public void shouldRun(boolean run) {
		
		_shouldRun = run;
	}
	

	public long timeUntilNextParse() {
		long time = 0;
		
		Date d = new Date(System.currentTimeMillis());
		
		if (d.getHours() > 17 || d.getDay() == 6 || d.getDay() == 0) {
			// seconds until midnight
			time = (3600*24) - (3600*d.getHours() + 60*d.getMinutes() + d.getSeconds());
			
			// seconds until 8:58
			time += ((60*9)-2)*60;

			if (d.getDay() == 5 && d.getHours() > 17) {
				// Friday after 17
				time += 3600*24*2;
			}
			else if (d.getDay() == 6) {
				// if its saturday
				time += 3600*24;
			}
		}
		else if( d.getHours() <= 8 && d.getMinutes() < 58 ) {
			time = (((60*9)-2)*60) - (60*(d.getHours() * 60)  + d.getMinutes() + d.getSeconds());
		}
		
		return time*1000;
	}
}