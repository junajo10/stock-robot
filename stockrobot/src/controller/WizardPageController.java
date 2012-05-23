package controller;

import java.beans.PropertyChangeEvent;
import view.wizard.WizardPage;

/**
 * 
 * @author Mattias
 *
 */
public abstract class WizardPageController implements IController {
	
	public abstract WizardPage getView();
}