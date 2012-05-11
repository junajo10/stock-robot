package controller.gui;

import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

import view.PortfolioHistoryView;

import model.database.jpa.tables.PortfolioEntity;
import model.portfolio.PortfolioHistoryModel;

/**
 * 
 * @author Daniel
 */
public class PortfolioHistoryController implements IController{
	private static final String NAME = "StockTableHistory";
	private PortfolioHistoryModel portfolioModel;
	private PortfolioHistoryView portfolioHistoryView;
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(Object model) {
		portfolioModel = new PortfolioHistoryModel((PortfolioEntity)model);
		
		portfolioHistoryView = new PortfolioHistoryView(portfolioModel);
		
		portfolioHistoryView.display(portfolioModel);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
	}

	@Override
	public Map<String, EventListener> getActionListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void defineSubControllers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return NAME;
	}

}
