package view.wizard;

import java.beans.PropertyChangeEvent;
import javax.swing.JPanel;

import view.IView;

import model.wizard.WizardModel;
import model.wizard.WizardPageModel;

/**
 * 
 * @author Mattias
 *
 */
public abstract class WizardPage extends JPanel  implements IView  {

	private static final long serialVersionUID = 5355811249972492866L;
	
	protected WizardPageModel pageModel;
	protected WizardModel wizardModel;
	
	public WizardPage(WizardModel wizardModel, WizardPageModel pageModel){
		this.wizardModel = wizardModel;
		this.pageModel = pageModel;
		
	}
	
	public void propertyChange(PropertyChangeEvent evt) {} //NOPMD
	
	@Override
	public void cleanup() {
		
		pageModel.removeObserver(this);
	}
}
