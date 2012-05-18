package controller;

import view.wizard.WizardPage;
import model.wizard.WizardPageModel;

public interface IPageFactory<T extends WizardPageModel> {

	public WizardPage build(T model);
}
