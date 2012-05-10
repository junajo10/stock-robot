package model.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Test;

public class WizardPageModelTest {

	WizardModel model = new WizardModel();
	WizardPageModel toTest = new WizardPageModel(model) {
		
		@Override
		public void finish() {
			
			
		}
		
		@Override
		public boolean canFinish() {
			
			return false;
		}
	};
	
	//TODO: Need to find out how to see if observers are added correctly
	@Test
	public void testAddAndRemoveObserver() {
		
		PropertyChangeListener listener = new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {}
		};
		
		toTest.addAddObserver( listener );
		toTest.removeObserver( listener );
	}
}