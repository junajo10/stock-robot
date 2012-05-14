package controller.gui;


import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;
import view.wizard.WizardPage;

public class WizardFactory {
	
	public static WizardContoller buildPortfolioWizard(){
			
		PortfolioWizardModel pageModel = new PortfolioWizardModel();
		
	 	WizardContoller wizard = new WizardContoller(pageModel);
		WizardModel wizardModel = wizard.getModel();
		
		WizardPage startPage = buildStartPage(wizardModel,pageModel);
    	wizard.getView().registerPage(1,startPage);
    	wizardModel.setNextPage(1);
    	wizardModel.goNextPage();
    	
    	WizardPage fromNewPage = buildFromNewPage(wizardModel, pageModel);
    	wizard.getView().registerPage(2,fromNewPage);
							
		return wizard;
	}
	
	private static WizardPage buildStartPage(WizardModel wizardModel, PortfolioWizardModel pageModel){
		
		WizardStartPageController controller = new WizardStartPageController(wizardModel,pageModel);
		
		return controller.getView();
	}
	
	private static WizardPage buildFromNewPage(WizardModel wizardModel, PortfolioWizardModel pageModel){

		
		WizardFromNewPageController controller = new WizardFromNewPageController(wizardModel, pageModel);
		
		return controller.getView();
	}
}
