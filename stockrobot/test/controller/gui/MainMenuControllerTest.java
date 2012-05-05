package controller.gui;

import java.awt.event.ActionListener;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import testhelpers.DatabaseCleaner;
import view.SimulationView;
import view.StockInfoGUI;
import view.StockTableHistoryView;
import view.graph.GraphView;
import viewfactory.ViewFactory;

/**
 * Test class for the MainMenu Controller. 
 * 
 * This test generally makes sure things generated from the controller are not nulled by the controller before sending
 * 
 * It also checks that what you bind, is actually the same you get back when asking for it (although I created the getters in the controller just for testing purposes)
 * 
 * I extended the DatabaseCleaner just because I noticed some views are using the DB
 * 
 * @author kristian
 *
 */
public class MainMenuControllerTest extends DatabaseCleaner {
	
	private MainMenuController toTest;
	
	@Before
	public void setupTest() {
		
		toTest = new MainMenuController();
	}
	
	@After
	public void tearDownTest() {
		
		toTest = null;
	}
	
	/**
	 * First off, check that toTest isn't nulled by something, maybe not needed?
	 */
	@Test
	public void testControllerNotNull() {
		
		Assert.assertTrue( toTest != null );
	}
	
//Here, let's see if the Views that are binded (send into the controller) are actually the same that get sent back.
//This is important even if it's trivial, because maybe the controller is faulty and mixes views up, returns the wrong one and so on.
	
	/**
	 * Make sure what you send in is the same you get back
	 */
	@Test
	public void testBindStockInfoGUI() {
		
		StockInfoGUI gui = ViewFactory.getStockInfoGUI();
		
		toTest.bindStockInfoGUI( gui );
		
		Assert.assertTrue( gui.equals( toTest.getStockInfoGUI() ) && gui == toTest.getStockInfoGUI() );
	}
	
	/**
	 * Make sure what you send in is the same you get back
	 */
	@Test
	public void testBindSimulationView() {
		
		SimulationView simView = ViewFactory.getSimulationView();
		
		toTest.bindSimulationView( simView );
		
		Assert.assertTrue( simView.equals( toTest.getSimulationView() ) && simView == toTest.getSimulationView() );
	}
	
	/**
	 * Make sure what you send in is the same you get back
	 */
	@Test
	public void testBindGraphView() {
		
		GraphView view = ViewFactory.getGraphView();
		
		toTest.bindGraphView( view );
		
		Assert.assertTrue( view.equals( toTest.getGraphView() ) && view == toTest.getGraphView() );
	}
	
	/**
	 * Make sure what you send in is the same you get back
	 */
	@Test
	public void testBindStockTableHistoryView() {
		
		StockTableHistoryView view = new StockTableHistoryView(3);
		
		toTest.bindHistoryView( view );
		
		Assert.assertTrue( view.equals( toTest.getStockTableHistoryView() ) && view == toTest.getStockTableHistoryView() );
	}
	
//Let's test the ActionListeners sent back when requesting a new listener. For now, just make sure they are not returned as null variables
	
	@Test
	public void testBindStockInfoGUIButton() {
		
		ActionListener listener = toTest.bindStockInfoGUIButton();
		
		Assert.assertTrue( listener != null );
	}
	
	@Test
	public void testBindAlgorithmSettingsGUIButton() {
		
		ActionListener listener = toTest.bindAlgorithmSettingsGUIButton();
		
		Assert.assertTrue( listener != null );
	}
	
	@Test
	public void testBindSimulationButton() {
		
		ActionListener listener = toTest.bindSimulationButton();
		
		Assert.assertTrue( listener != null );
	}
	
	@Test
	public void testBindOpenGraphButton() {
		
		ActionListener listener = toTest.bindOpenGraphButton();
		
		Assert.assertTrue( listener != null );
	}
	
	@Test
	public void testBindHistoryButton() {
		
		ActionListener listener = toTest.bindHistoryButton();
		
		Assert.assertTrue( listener != null );
	}
}