package model.wizard.portfolio;

import model.wizard.WizardModel;
import model.wizard.WizardPageModel;

public class PortfolioWizardModel extends WizardPageModel {

	public PortfolioWizardModel(WizardModel wizardModel) {
		super(wizardModel);
	}

	@Override
	public WizardPageModel copyModel() {
		
		PortfolioWizardModel newModel = new PortfolioWizardModel(wizardModel);
		
		return newModel;
	}

}
