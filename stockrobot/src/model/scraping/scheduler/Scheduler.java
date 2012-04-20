package model.scraping.scheduler;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

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

	//TODO: Implement startTime and endTime
	//private Date startTime;
	//private Date endTime;
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
		
			
			DateTime d = new DateTime(System.currentTimeMillis());
			//Only run all queries, scraping etc between 08:58 AM and 17:44 (44 to avoid getting 0's in prices after 17:30 avanza time)
			
			//Cases when it's now allowed to run:
			//If it's before 8AM
			if( d.getHourOfDay() < 8 )
				return false;
				
			//If it's 8 AM and before 8:58
			if( d.getHourOfDay() == 8 && d.getMinuteOfHour() < 58 )
				return false;
					
			//If it's after 17 PM
			if( d.getHourOfDay() > 17 )
				return false;
					
			//If it's 17 PM and after 17:47
			if( d.getHourOfDay() == 17 && d.getMinuteOfHour() > 44 )
				return false;
			
			//If saturday or sunday!
			if( d.getDayOfWeek() == DateTimeConstants.SATURDAY || d.getDayOfWeek() == DateTimeConstants.SUNDAY )
				return false;
			
			//Otherwise, just go for it!
			return true;
		}

	//TODO: IMPLEMENT setStartTime
	@Override
	public void setStartTime(Date start) {
		
		//startTime = start;
	}

	//TODO: IMPLEMENT setEndTime
	@Override
	public void setEndTime(Date end) {
		
		//endTime = end;
	}

	@Override
	public void shouldRun(boolean run) {
		
		_shouldRun = run;
	}
	

	public long timeUntilNextParse() {
		long time = 0;
		
		DateTime d = new DateTime(System.currentTimeMillis());
		
		if (d.getHourOfDay() > 17 || d.getDayOfWeek() == DateTimeConstants.SATURDAY || d.getDayOfWeek() == DateTimeConstants.SUNDAY) {
			// seconds until midnight
			time = (3600*24) - (3600*d.getHourOfDay() + 60*d.getMinuteOfHour() + d.getSecondOfMinute());
			
			// seconds until 8:58
			time += ((60*9)-2)*60;

			if (d.getDayOfWeek() == DateTimeConstants.FRIDAY && d.getHourOfDay() > 17) {
				// Friday after 17
				time += 3600*24*2;
			}
			else if (d.getDayOfWeek() == DateTimeConstants.SATURDAY) {
				// if its saturday
				time += 3600*24;
			}
		}
		else if( d.getHourOfDay() <= 8 && d.getMinuteOfHour() < 58 ) {
			time = (((60*9)-2)*60) - (3600*d.getHourOfDay() + 60*d.getMinuteOfHour() + d.getSecondOfMinute());
		}
		
		return time*1000;
	}
}