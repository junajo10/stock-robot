package model.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite of WizardModel
 * 
 * Please feel free to add more tests to this suite
 * 
 * @author kristian
 *
 */
public class WizardModelTest {

	private WizardModel toTest;
	
	@Before
	public void setupTest() {
		
		toTest = new WizardModel();
	}
	
	@After
	public void tearDownTest() {
		
		toTest = null;
	}
	
	/**
	 * Test a simple set and get of a string
	 */
	@Test
	public void testSetAndGetTitle() {
		
		String testWithString = "afioHIOHOEFenfaeknlfnpa,.n92033 3r3r";
		
		toTest.setTitle( testWithString );
		
		Assert.assertEquals( testWithString, toTest.getTitle() );
	}
	
	/**
	 * Test setting the title two times, the second first time should not be written as some kind of final value
	 */
	@Test
	public void testSetAndGetTitleTwoTimes() {
		
		String testWithString = "afioHIOHOEFenfaeknlfnpa,.n92033 3r3r";
		String testWithString2 = "AADiaefugi<<<giwyda893rr";
		
		toTest.setTitle( testWithString );
		toTest.setTitle( testWithString2 );
		
		Assert.assertTrue( !testWithString.equals( toTest.getTitle() ) && testWithString2.equals( toTest.getTitle() ) );
	}
	
	/**
	 * Test a simple set and get of the SUB title
	 */
	@Test
	public void testSetAndGetSubTitle() {
		
		String testWithString = "afioHIOHOEFenfaeknlfnpa,.n92033 3r3r";
		
		toTest.setSubtitle( testWithString );
		
		Assert.assertEquals( testWithString, toTest.getSubtitle() );
	}
	
	/**
	 * Test setting the SUB title two times, the second first time should not be written as some kind of final value
	 */
	@Test
	public void testSetAndGetSubTitleTwoTimes() {
		
		String testWithString = "afioHIOHOEFenfaeknlfnpa,.n92033 3r3r";
		String testWithString2 = "AADiaefugi<<<giwyda893rr";
		
		toTest.setSubtitle( testWithString );
		toTest.setSubtitle( testWithString2 );
		
		Assert.assertTrue( !testWithString.equals( toTest.getSubtitle() ) && testWithString2.equals( toTest.getSubtitle() ) );
	}
	
	/**
	 * Test canFinish
	 */
	@Test
	public void testCanFinishTrue() {
		
		boolean val = true;
		
		toTest.setFinish( val );
		
		Assert.assertEquals( val, toTest.isAllowedFinish() );
	}
	
	/**
	 * Test canFinish
	 */
	@Test
	public void testCanFinishFalse() {
		
		boolean val = false;
		
		toTest.setFinish( val );
		
		Assert.assertEquals( val, toTest.isAllowedFinish() );
	}
	
	/**
	 * Set and get the next page, and make sure they are the same
	 */
	@Test
	public void testSetAndGetNextPageTest() {
		
		int nextPage = (int) Math.round( Math.random() * 100 );
		
		toTest.setNextPage( nextPage );
		
		Assert.assertTrue( nextPage == toTest.getNextPage() );
	}
	
	/**
	 * Never add a page, the result when calling getNextPage should be null right?
	 */
	@Test
	public void testGetNextPageWithoutSetting() {
		
		Assert.assertNull( toTest.getNextPage() );
	}
	
	/**
	 * Add a next page, then remove it again, answer should be null?
	 */
	@Test
	public void testGetNextPageSetAndRemove() {
		
		int nextPage = (int) Math.round( Math.random() * 100 );
		
		toTest.setNextPage( nextPage );
		toTest.removeNextPage();
		
		Assert.assertNull( toTest.getNextPage() );
	}
	
	/**
	 * Test that the model really goes to the next page
	 */
	@Test
	public void testGotoNextPage() {
		
		int nextPage = (int) Math.round( Math.random() * 100 );
		
		toTest.setNextPage( nextPage );
		
		Assert.assertTrue( nextPage == toTest.goNextPage() );
	}
	
	/**
	 * Test the getBackPage method
	 */
	public void testGetBackPage() {
		
		int nextPage = (int) Math.round( Math.random() * 100 );
		int nextPage2 = (int) Math.round( Math.random() * 100 );
		
		toTest.setNextPage( nextPage );
		toTest.setNextPage( nextPage2 );
		
		Assert.assertTrue( toTest.getBackPage() == nextPage );
	}
	
	/**
	 * Add two pages and go two steps forward
	 * 
	 * Then go one step back.
	 * 
	 * After that nextPage (the first added should be the current page)
	 */
	@Test
	public void testGoBack() {
		
		int nextPage = (int) Math.round( Math.random() * 100 );
		int nextPage2 = (int) Math.round( Math.random() * 100 );
		
		toTest.setNextPage( nextPage );
		toTest.goNextPage();
		toTest.setNextPage( nextPage2 );
		toTest.goNextPage();
		
		Assert.assertTrue( nextPage == toTest.goBackPage() );
	}
	
	/**
	 * Add two pages and go two steps forward
	 * 
	 * Then go TWO steps back.
	 * 
	 * After that null should be the current page
	 */
	@Test
	public void testGoBackTwoTimes() {
		
		int nextPage = (int) Math.round( Math.random() * 100 );
		int nextPage2 = (int) Math.round( Math.random() * 100 );
		
		toTest.setNextPage( nextPage );
		toTest.goNextPage();
		toTest.setNextPage( nextPage2 );
		toTest.goNextPage();
		
		toTest.goBackPage();
		
		Assert.assertNull( toTest.goBackPage() );
	}
	
	/**
	 * Test the addPage method (which is synchronized by the way)
	 */
	@Test
	public void addPageTest() {
		
		toTest.addPage(234);
		toTest.addPage(232);
		toTest.addPage(0);
		Assert.assertFalse( toTest.addPage(35) );
	}
	
	/**
	 * Testing addPage with a negative value
	 */
	@Test
	public void addPageTestNegative() {
		
		toTest.addPage(-10);
		Assert.assertFalse( toTest.addPage(-124) );
	}
	
	@Test
	public void addAlreadyExistingPage() {
		
		toTest.addPage(10);
		Assert.assertTrue( toTest.addPage(10) );
	}
	
	//TODO: Find out how to test if a propertChangeSupport has listeners
	@Test
	public void testAddObservers() {
		
		PropertyChangeListener listener = new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {}
		};
		
		toTest.addAddObserver(listener);
		
		toTest.removeObserver(listener);
	}
}