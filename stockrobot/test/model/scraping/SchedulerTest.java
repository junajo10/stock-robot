package model.scraping;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.scraping.scheduler.IScheduler;
import model.scraping.scheduler.Scheduler;

public class SchedulerTest {

	private IScheduler scheduler;
	
	@Before
	public void setupTest() {
		
		scheduler = new Scheduler();
	}
	
	@After
	public void tearDownTest() {
		
		scheduler = null;
	}
	
	//TODO: Find out a smart way to test the Date dependent method shouldRun
	
	/**
	 * Should always give false back
	 */
	@Test
	public void testShouldRunFalse() {
		
		//Now scheduler should always return false 
		scheduler.setShouldRun( false );
		
		Assert.assertEquals( false, scheduler.shouldRun() );
	}
	
	/**
	 * This test will do something useful in most cases, except for once a minute (during stock trading time) when the time is exactly zero.
	 */
	@Test
	public void testTimeUntilNextParse() {
		
		Assert.assertFalse( scheduler.timeUntilNextParse() == 0 );
	}
}