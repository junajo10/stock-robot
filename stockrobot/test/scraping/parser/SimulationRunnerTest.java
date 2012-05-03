package scraping.parser;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import testhelpers.DatabaseCleaner;

import model.scraping.parser.SimulationRunner;

/**
 * This class tests if the simulationRunner starts and stops as it's supposed to do
 * 
 * @author kristian
 *
 */
public class SimulationRunnerTest extends DatabaseCleaner {
	
	private int port;
	private SimulationRunner runner;
	private boolean runnercreated = false;
	private boolean okToShutDown = false;
	private Thread th;
	
	private static final int pollingTime = 100;
	
	@Before
	public void setup() {
		
		okToShutDown = false;
		
		th = new Thread( new Runnable() {
			
			@Override
			public void run() {
				
				port = 25000 + (int) Math.round( Math.random() * 4000 );
				runner = new SimulationRunner( port );
				runnercreated = true;
				
				try {
				
					runner.run();
					
				}catch(Exception e) {
					
					e.printStackTrace();
				}
			}
		} );
		th.start();
			
		while( !runnercreated ) {
			
			try {
				Thread.sleep(pollingTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@After
	public void tearDown() {
		
		new Thread( new Runnable() {
			
			@Override
			public void run() {
				
				try {
					if(th != null)
						th.join();
					
					if(runner != null)
						runner.stopParser();
					
					okToShutDown = true;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
		
		}).start();
		
		while( okToShutDown ) {
			
			try {
				Thread.sleep(pollingTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		runnercreated = false;
		
		runner.stopRunner();
		
		//runner = null; //TODO: Is it ok to set this to null? It dies when it finishes in a while loop
	}
	
	@Test
	public void notYetStarted() {
		
		while( !runnercreated ) {
			
			try {
				Thread.sleep(pollingTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
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
		Assert.assertFalse( isRunning );
	}
	
	@Test
	public void started() {
		
		while( !runnercreated ) {
			
			try {
				Thread.sleep(pollingTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		runner.startParser();
		
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
		
		while( !runnercreated ) {
			
			try {
				Thread.sleep(pollingTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
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