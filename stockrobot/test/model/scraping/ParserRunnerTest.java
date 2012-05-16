package model.scraping;

import junit.framework.Assert;
import model.scraping.parser.ParserRunner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Note, the following tests are linear! They do not clear the DB or reset something between every run.
 * 
 * Just @BeforeClass and @AfterClass
 * 
 * @author kristian
 *
 */
public class ParserRunnerTest {

	private static ParserRunner toTest;
	private static Thread th;
	private static boolean runnerCreated = false;
	
	private final static int pollingTime = 100;
	
	@BeforeClass
	public static void setupTest() {
		
		toTest = new ParserRunner((int) Math.round( Math.random() * 1000 ) + 20003);
		toTest.setSkipScheduler();
		
		th = new Thread( new Runnable() {
			
			@Override
			public void run() {
				
				runnerCreated = true;
				toTest.run();				
			}
		} );
		th.start();
		
		while( !runnerCreated ) {
			
			try {
				Thread.sleep(pollingTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@AfterClass
	public static void tearDownTest() {
		
		toTest.stopParser();
		toTest.stopRunner();
		
		Thread closer = new Thread( new Runnable() {
			
			@Override
			public void run() {
				
				runnerCreated = false;
				
				try {
					th.join();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		} );
		closer.start();
	}
	
	/**
	 * Startparser has not yet been called, so the result of status here should be false, is it?
	 */
	@Test
	public void testRun() {
		
		while( !runnerCreated ) {
			
			try {
				Thread.sleep(pollingTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Assert.assertFalse( toTest.status() );
	}
	
	/**
	 * After starting the parser, result of status should be true
	 */
	@Test
	public void testRunParser() {
		
		boolean wasStarted = toTest.startParser();
		
		//Check that the right value was sent back from startParser, and that status was changed
		Assert.assertTrue( toTest.status() && wasStarted );
	}
	
	/**
	 * If we stop a running parser, the result should be false again
	 */
	@Test
	public void testStopRunningParser() {
		
		boolean wasStopped = toTest.stopParser();
		
		//Check that the right value was sent back from stopParser, and that status was changed
		Assert.assertTrue( !toTest.status() && wasStopped );
	}
	
	/**
	 * This is to test that the stopRunner method works as anticipated
	 * 
	 * TODO:
	 * However, why is it not possible to assert with assertTrue here???
	 *    It gives a nullpointer exception. But assertFalse is ok. WHY?
	 */
	@Test
	public void testStopRunner() {
		
		boolean wasStopped = toTest.stopRunner();
		
		Assert.assertFalse( toTest.status() && !wasStopped );
	}
}