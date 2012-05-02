package model.wizard.portfolio;

import model.wizard.WizardModel;
import model.wizard.WizardPageModel;

public class PortfolioWizardModel extends WizardPageModel{

	public PortfolioWizardModel(WizardModel wizardModel) {
		super(wizardModel);
	}

	@Override
	public WizardPageModel copyModel() {
		
		PortfolioWizardModel newModel = new PortfolioWizardModel(wizardModel);
		
		return newModel;
	}

	@Override
	public boolean canFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}
}
