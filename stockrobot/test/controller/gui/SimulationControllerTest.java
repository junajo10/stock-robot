package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import testhelpers.DatabaseCleaner;
import view.SimulationView;

/**
 * Test the simulation controller!
 * 
 * TODO:
 * This test causes a NullPointer at:
 * 
 *  Exception in thread "Thread-5" java.lang.NullPointerException
 *	at view.SimulationView.getSelectedAlgorithm(SimulationView.java:113)
 *	at controller.gui.SimulationController$2$1.run(SimulationController.java:58)
 *	at java.lang.Thread.run(Thread.java:680)
 *
 *  It happens because the view has nothing selected in it's JComboBox. I created it with "new SimulationView()"
 * 
 * @author kristian
 *
 */
public class SimulationControllerTest extends DatabaseCleaner {
	
	SimulationView view;
	SimulationController toTest;
	
	@Before
	public void seetupTest() {
		
		view = new SimulationView();
		toTest = new SimulationController( view );
	}
	
	@After
	public void tearDownTest() {
		
		view = null;
		toTest = null;
	}
	
	/**
	 * Test that the map isn't null. The handler is a vital class and should not be null in the controller
	 */
	@Test
	public void testHandler() {
		
		Map<String, Long> map = toTest.handler.getLatestPieData();
		
		Assert.assertNotNull(map);
	}
	
	/**
	 * Simply looking to cover this method, looking to make it crash *evil grin*
	 */
	@Test
	public void testGoButton() {
		
		ActionListener listener = toTest.goButton();
		
		listener.actionPerformed( new ActionEvent( new Object(), 2424, "JUST_TESTING!") );
		
		//Make sure the listener didn't null handler
		Assert.assertNotNull( toTest.handler );
	}
	
	//TODO: How can we test the private methods in SimulationController?
	//In normal cases you would NOT do it or set them to public when testing. But don't want to mess with this class internals right now.
	/*
	@Test
	public void testCreateChart() {
		
		PieDataset dataset = toTest.
	}*/
}