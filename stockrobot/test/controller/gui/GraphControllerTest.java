package controller.gui;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.GraphController;

import testhelpers.DatabaseCleaner;

/**
 * Test the GraphController for defects
 * 
 * @author kristian
 *
 */
public class GraphControllerTest extends DatabaseCleaner {
	
	
	private GraphController toTest;
	
	@Before 
	public void setupTest() {
		
		toTest = new GraphController();
	}
	
	@After
	public void tearDownTest() {
		
		toTest.cleanup();
		toTest = null;
	}
	
	/**
	 * It's important to know the view is created in the controller, and not destroyed by something by accident
	 */
	@Test
	public void viewIsntNull() {
		
		Assert.assertNotNull( toTest.getView() );
	}
}