package controller;

import java.beans.PropertyChangeEvent;
import view.wizard.WizardPage;

/**
 * 
 * @author Mattias
 *
 */
public abstract class WizardPageController implements IController {

	protected String name = WizardPageController.class.getName();
	
	public abstract WizardPage getView();
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {} //NOPMD

	@Override
	public String getName() {
		return name;
	}
}