package controller.wizard.portfolio;

import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import junit.framework.Assert;

import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.WizardFromNewPageController;

public class WizardFromNewPageControllerTest {

	private WizardFromNewPageController toTest;
	
	@Before
	public void setupTest() {
		
		WizardModel model = new WizardModel();
		PortfolioWizardModel portfolioWizardModel = new PortfolioWizardModel( );
		
		toTest = new WizardFromNewPageController( model, portfolioWizardModel );
	}
	
	@After
	public void tearDownTest() {
		
		toTest = null;
	}
	
	@Test
	public void testGetAlgorithmListenerNotNull() {
		
		Assert.assertNotNull( toTest.getAlgorithmListener() );
	}
	
	
	/**
	 * Test selected, then the result from toTest.wizardModel.getFinish should be true
	 */
	@Test
	public void testItemStateChangedDESELECTED() {
		
		ItemListener listener = toTest.getAlgorithmListener();
		
		//Faked ItemEvent, to be able to test what the handler in listener does:
		ItemEvent fakeEvent = new ItemEvent(new ItemSelectable() {
			
			@Override
			public void removeItemListener(ItemListener l) {} //NOPMD
			
			@Override
			public Object[] getSelectedObjects() { return null; } //NOPMD
			
			@Override
			public void addItemListener(ItemListener l) {} //NOPMD
			
		}, 0, new Object(), ItemEvent.DESELECTED);
		
		listener.itemStateChanged(fakeEvent);
		
		Assert.assertEquals( false, toTest.getWizardModel().isAllowedFinish() );
	}
}