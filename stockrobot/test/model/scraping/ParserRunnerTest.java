package model.scraping;

import java.beans.PropertyChangeSupport;

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
 * ALSO: note that this class prefers the database to be empty. Maybe not a need but it's highly recommended to run this test after one of the tests that clears DB when finished
 * 
 * @author kristian
 *
 */
public class ParserRunnerTest {

	private static ParserRunner toTest;
	private static Thread th;
	private static boolean runnerCreated = false;
	private static PropertyChangeSupport pcs;
	private final static int POLLINGTIME = 100;
	
	@BeforeClass
	public static void setupTest() {
		
		pcs = new PropertyChangeSupport(new Object());
		
		toTest = new ParserRunner((int) Math.round( Math.random() * 1000 ) + 20003, pcs);
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
				Thread.sleep(POLLINGTIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@AfterClass
	public static void tearDownTest() {
		
		toTest.stopParser();
		
	}
	
	/**
	 * Startparser has not yet been called, so the result of status here should be false, is it?
	 */
	@Test
	public void testRun() {
		
		while( !runnerCreated ) {
			
			try {
				Thread.sleep(POLLINGTIME);
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
}