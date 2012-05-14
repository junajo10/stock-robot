package controller.gui;

import java.beans.PropertyChangeEvent;
import view.wizard.WizardPage;

public abstract class WizardPageController implements IController {

	protected String name = WizardPageController.class.getName();
	
	public abstract WizardPage getView();
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return name;
	}

}
