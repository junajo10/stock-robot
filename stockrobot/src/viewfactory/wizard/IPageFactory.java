package viewfactory.wizard;

import view.wizard.WizardPage;
import model.wizard.WizardPageModel;

public interface IPageFactory<T extends WizardPageModel> {

	public WizardPage<T> build(T model);
}
