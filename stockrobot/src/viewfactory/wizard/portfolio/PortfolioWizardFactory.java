package viewfactory.wizard.portfolio;


import javax.swing.SwingUtilities;

import controller.wizard.WizardContoller;
import controller.wizard.portfolio.WizardFromNewPageController;
import controller.wizard.portfolio.WizardStartPageController;

import model.portfolio.IPortfolioHandler;
import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;
import view.wizard.WizardPage;
import view.wizard.WizardView;
import view.wizard.portfolio.PortfolioFromNewPage;
import view.wizard.portfolio.PortfolioStartPage;

public class PortfolioWizardFactory {

	public static WizardView buildPortfolioWizard(final IPortfolioHandler portfolioHandler){
		
		final WizardModel wizardModel = new WizardModel();
    	
    	
    	final WizardView wizard = new WizardView(wizardModel);
    	wizard.setCancelListener(WizardContoller.getCancelListener(wizard));
    	wizard.setEnableCancel(true);
    	wizard.setBackListener(WizardContoller.getBackListener(wizardModel));
    	wizard.setNextListener(WizardContoller.getNextListener(wizardModel));
    	
    	wizardModel.addAddObserver(wizard);
    	
    	wizardModel.setTitle("Portfolio Wizard");
    	wizardModel.setSubtitle("Portfolio Wizard");
    	
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
            	WizardPage<PortfolioWizardModel> startPage = buildStartPage(wizard,wizardModel,portfolioHandler);
            	wizard.registerPage(1,startPage);
            	WizardPage<PortfolioWizardModel> fromNewPage = buildFromNewPage(wizardModel, portfolioHandler);
            	wizard.registerPage(2,fromNewPage);
            	wizardModel.setNextPage(1);
            	wizardModel.goNextPage();
            	//wizard.loadScreen(PortfolioPages.PAGE_START);
            }
        });
		
		
		return wizard;
	}
	
	private static WizardPage<PortfolioWizardModel> buildStartPage(WizardView wizardView, WizardModel wizardModel, IPortfolioHandler portfolioHandler){
		
		PortfolioWizardModel pageModel = new PortfolioWizardModel(wizardModel);
		PortfolioStartPage startPage = new PortfolioStartPage(wizardModel, pageModel, portfolioHandler);
		WizardStartPageController controller = new WizardStartPageController(startPage, wizardView, wizardModel);
		startPage.setCreateFromNewListener(controller.getFromNewListener());
		startPage.setCreateFromCloneListener(controller.getFromCloneListener());
		pageModel.addAddObserver(startPage);
		//
		//startPage.setCreateFromNewListener(controller.getFromNewListener());
		
		return startPage;
	}
	
	private static WizardPage<PortfolioWizardModel> buildFromNewPage(WizardModel wizardModel, IPortfolioHandler portfolioHandler){
		PortfolioWizardModel pageModel = new PortfolioWizardModel(wizardModel);
		PortfolioFromNewPage fromNewPage = new PortfolioFromNewPage(wizardModel, pageModel);
		WizardFromNewPageController controller = new WizardFromNewPageController(fromNewPage, wizardModel);
		fromNewPage.setAlgorithmListener(controller.getAlgorithmListener());
		
		
		return fromNewPage;
	}
}
