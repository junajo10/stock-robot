package viewfactory.wizard.portfolio;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import model.database.jpa.tables.PortfolioEntity;
import model.portfolio.IPortfolio;
import model.portfolio.IPortfolioHandler;
import model.portfolio.Portfolio;
import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;
import view.wizard.WizardPage;
import view.wizard.WizardView;
import view.wizard.portfolio.PortfolioStartPage;

public class PortfolioWizardFactory {

	public static WizardView buildPortfolioWizard(){
		
		WizardModel wizardModel = new WizardModel();
    	wizardModel.setTitle("Portfolio Wizard");

    	final WizardView wizard = new WizardView(wizardModel);
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	WizardModel wizardModel = new WizardModel();
            	wizardModel.setTitle("Portfolio Wizard");
		
            	WizardPage<PortfolioWizardModel> startPage = buildStartPage(wizardModel,new TestPortfolioHandler(new ArrayList<IPortfolio>()));
            	wizard.registerPage(1,startPage);
            	wizard.loadScreen(1);
            }
        });
		
		return wizard;
	}
	
	private static WizardPage<PortfolioWizardModel> buildStartPage(WizardModel wizardModel, IPortfolioHandler portfolioHandler){
		
		PortfolioWizardModel pageModel = new PortfolioWizardModel(wizardModel);
		PortfolioStartPage startPage = new PortfolioStartPage(wizardModel, pageModel, portfolioHandler);
		
		return startPage;
	}
	
	public static void main(String[] args){
		
		buildPortfolioWizard();
	}
}

class TestPortfolioHandler implements IPortfolioHandler{

	private List<IPortfolio> listOfPortfolios = new ArrayList<IPortfolio>();
	
	public TestPortfolioHandler(List<IPortfolio> portfolios) {
				
		if(portfolios != null)
			listOfPortfolios.addAll(portfolios);
	}
	@Override
	public IPortfolio createNewPortfolio(String name) {
		PortfolioEntity pt = new PortfolioEntity(name);
		return new Portfolio(pt);
	}

	@Override
	public List<IPortfolio> getPortfolios() {
		return listOfPortfolios;
	}

	@Override
	public boolean removePortfolio(IPortfolio portfolio) {
	
		return false;
	}

	@Override
	public void addAddObserver(PropertyChangeListener listener) {
	}
	@Override
	public void removeObserver(PropertyChangeListener listener) {
	}
	@Override
	public List<String> getAlgorithmNames() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean setAlgorithm(IPortfolio p, String algorithmName) {
		// TODO Auto-generated method stub
		return false;
	}
}
