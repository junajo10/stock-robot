package model.wizard;


public abstract class WizardPageModel {

	protected WizardModel wizardModel;

	public WizardPageModel(WizardModel wizardModel) {
	
		this.wizardModel = wizardModel;
	}
	
	public abstract WizardPageModel copyModel();
}
