package controller.wizard;

import java.awt.event.ActionListener;

import junit.framework.Assert;

import model.wizard.WizardModel;

import org.junit.Test;

import view.wizard.WizardView;

/**
 * For now, just testing to see that listeners received back from the controller are not null
 * 
 * TODO: This controller is a bit hard to test. It's not easy getting some of the listeners out of the controller this way. What to do?
 * 
 * @author kristian
 *
 */
public class WizardControllerTest {

	@Test
	public void testGetNextListenerNotNull() {
		
		WizardModel model = new WizardModel();
		model.goNextPage();
		
		//ActionListener listener = WizardContoller.getNextListener(model);
		//TODO add test
		//Assert.assertNotNull( listener );
	}
	
	@Test 
	public void testGetCancelListenerNotNull() {
		
		WizardModel model = new WizardModel();
		model.goNextPage();
		
		WizardView view = new WizardView(model);
		
		//ActionListener listener = WizardContoller.getCancelListener(view);
		//TODO add test
		//Assert.assertNotNull( listener );
	}
	
	//TODO: find out how to test the static classes within WizardController 
	/**
	@Test
	public void testNextPageListenerNotNull() {
		
		WizardModel model = new WizardModel();
		
		ActionListener listener = WizardContoller.NextPageListener(model);
	}
	*/
}