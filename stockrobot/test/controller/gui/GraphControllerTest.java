package controller.gui;

/*
import org.junit.After;
import org.junit.Before;
*/
import testhelpers.DatabaseCleaner;

/**
 * Test the GraphController for defects
 * 
 * @author kristian
 *
 */
public class GraphControllerTest extends DatabaseCleaner {
	
	/*
	private GraphController toTest;
	
	@Before 
	public void setupTest() {
		
		toTest = new GraphController();
	}
	
	@After
	public void tearDownTest() {
		
		toTest = null;
	}
	
	
	@Test
	public void testBindViewIsTheSameEquals() {
		
		GraphView view = ViewFactory.getGraphView();
		toTest.bindGraphView( view );
		
		Assert.assertTrue( view.equals( toTest.getView() ) );
	}
	
	@Test
	public void testBindViewIsTheSameInstance() {
		
		GraphView view = ViewFactory.getGraphView();
		toTest.bindGraphView( view );
		
		Assert.assertTrue( view == toTest.getView() );
	}
	
	@Test
	public void testRunActionListener() {
		
		//Fake a user/GUI call to the ActionListener provided by toTest
		//If nothing was broken/no error was raised by the actionPerformed, for now, let's say everything went OK
		ActionListener listener = toTest.bindAddStock();
		
		ActionEvent fakeEvent = new ActionEvent( new Object(), 0, "FakeTest" );
		
		listener.actionPerformed( fakeEvent );
	}
	*/
}