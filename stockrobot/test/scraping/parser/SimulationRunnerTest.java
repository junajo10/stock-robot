package scraping.parser;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.scraping.parser.SimulationRunner;

/**
 * This class tests if the simulationRunner starts and stops as it's supposed to do
 * 
 * @author kristian
 *
 */
public class SimulationRunnerTest {
	
	private int port;
	private SimulationRunner runner;
	
	@Before
	public void setup() {
		
		try {
			
			IJPAHelper helper = JPAHelper.getInstance();
			helper.getEntityManager().evictAll();
			
			//Setup a random port between 10000 and 11000 
			port = 25000 + (int) Math.round( Math.random() * 4000 );
			
			runner = new SimulationRunner( port );
			
			Thread th = new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					runner.run();
				}
			});
			th.start();
		
		} catch( Exception e ) {
			
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() {
		
		runner.stopRunner();
		runner.stopParser();
		runner = null;
	}
	
	@Test
	public void notYetStarted() {
		
		//The runner has not yet been told to start, and should not have started
		boolean isRunning = false;
		
		//Test status() some times to see that it gets the right result back
		for( int i = 0; i < 1000 + (int) Math.round( Math.random() * 1000 ); i ++ ) {
			
			isRunning = runner.status();
			
			//If the status method of SimulationRunner has returned true here, then we know something is wrong!
			if( isRunning )
				break;
		}
		
		//We want isRunning to be false here, even after testing it 1000 to 2000 times.
		//This is merely to ensure nothing strange happens in the first few runs
		Assert.assertTrue( !isRunning );
	}
	
	@Test
	public void started() {
		
		runner.startParser();
		
		//Wait some to make sure the runner starts running
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		//The runner has not yet been told to start, and should not have started
		boolean isRunning = true;
		
		//Test status() some times to see that it gets the right result back
		for( int i = 0; i < 1000 + (int) Math.round( Math.random() * 1000 ); i ++ ) {
			
			isRunning = runner.status();
			
			//If the status method of SimulationRunner has returned false here, then we know something is wrong!
			if( !isRunning )
				break;
		}
		
		//We want isRunning to be false here, even after testing it 1000 to 2000 times.
		//This is merely to ensure nothing strange happens in the first few runs
		Assert.assertTrue( isRunning );
	}
	
	@Test
	public void startAndStop() {
		
		//Start
		runner.startParser();
		boolean runningAfterRun = runner.status();
		
		//Stop
		runner.stopParser();
		boolean runningAfterStop = runner.status();
		
		//Check so it's started and stopped
		Assert.assertTrue( runningAfterRun && !runningAfterStop );
	}
}