package utils.global;

import java.util.Date;

import org.joda.time.DateTime;

public class ParseDateFromString {
	
	/**
	 * 
	 * 
	 * 
	 * @param time 
	 * @return
	 */
	public static Date getDate( String time ) {
		DateTime currDate = new DateTime(System.currentTimeMillis());
		DateTime d = new DateTime(currDate.getYear(), currDate.getMonthOfYear(), currDate.getDayOfMonth(), Integer.parseInt(time.substring(0,2)), Integer.parseInt(time.substring(3,5)));
		
		return d.toDate();
	}
}