package gui;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import model.database.jpa.tables.PortfolioEntity;
import model.portfolio.IPortfolio;
import model.portfolio.IPortfolioHandler;
import model.portfolio.Portfolio;

public class TesterPortfolioGui {

	public static void main(String[] args) {
			
		//TODO make tests
		TestPortfolioHandler pHandler = new TestPortfolioHandler(null);
		
		//PortfolioWizardFactory.buildPortfolioWizard(pHandler);
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

