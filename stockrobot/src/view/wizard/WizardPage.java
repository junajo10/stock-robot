package view.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import model.wizard.WizardModel;

public abstract class WizardPage<T> extends JPanel  implements PropertyChangeListener  {

	private static final long serialVersionUID = 5355811249972492866L;
	
	protected T pageModel;
	protected WizardModel wizardModel;
	
	public WizardPage(WizardModel wizardModel, T pageModel){
		this.wizardModel = wizardModel;
		this.pageModel = pageModel;
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
}
