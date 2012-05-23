package controller;

import view.PortfolioHistoryView;

import model.database.jpa.tables.PortfolioEntity;
import model.portfolio.PortfolioHistoryModel;

/**
 * 
 * @author Daniel
 */
public class PortfolioHistoryController implements IController{
	private PortfolioHistoryModel portfolioModel;
	private PortfolioHistoryView portfolioHistoryView;

	@Override
	public void display(Object model) {
		portfolioModel = new PortfolioHistoryModel((PortfolioEntity)model);
		
		portfolioHistoryView = new PortfolioHistoryView(portfolioModel);
		
		portfolioHistoryView.display(portfolioModel);
	}

	@Override
	public void cleanup() {} //NOPMD
}
