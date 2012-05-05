package model.scraping;

import junit.framework.Assert;
import model.scraping.parser.ParserRunner;

import org.junit.Test;

public class ParserRunnerTest {

	private ParserRunner toTest;
	private Thread th;
	private boolean runnerCreated = false;
	
	private final static int pollingTime = 100;
	
	@Test
	public void testRun() {
		toTest = new ParserRunner(20003);
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
		
		System.out.println( "now running!" );
		
		Assert.assertTrue( toTest.startParser() );
		
		try {
			th.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}