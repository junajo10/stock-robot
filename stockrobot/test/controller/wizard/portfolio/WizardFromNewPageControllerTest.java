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

import view.wizard.portfolio.PortfolioFromNewPage;

public class WizardFromNewPageControllerTest {

	WizardFromNewPageController toTest;
	
	@Before
	public void setupTest() {
		
		WizardModel model = new WizardModel();
		PortfolioWizardModel portfolioWizardModel = new PortfolioWizardModel( model );
		
		PortfolioFromNewPage pFNP = new PortfolioFromNewPage( model, portfolioWizardModel );
		
		toTest = new WizardFromNewPageController( pFNP, model );
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
	public void testItemStateChangedSELECTED() {
		
		ItemListener listener = toTest.getAlgorithmListener();
		
		//Faked ItemEvent, to be able to test what the handler in listener does:
		ItemEvent fakeEvent = new ItemEvent(new ItemSelectable() {
			
			@Override
			public void removeItemListener(ItemListener l) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object[] getSelectedObjects() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void addItemListener(ItemListener l) {
				// TODO Auto-generated method stub
				
			}
		}, 0, new Object(), ItemEvent.SELECTED);
		
		listener.itemStateChanged(fakeEvent);
		
		Assert.assertEquals( true, toTest.getWizardModel().isAllowedFinish() );
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
			public void removeItemListener(ItemListener l) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object[] getSelectedObjects() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void addItemListener(ItemListener l) {
				// TODO Auto-generated method stub
				
			}
		}, 0, new Object(), ItemEvent.DESELECTED);
		
		listener.itemStateChanged(fakeEvent);
		
		Assert.assertEquals( false, toTest.getWizardModel().isAllowedFinish() );
	}
}