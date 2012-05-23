package controller;

import view.PortfolioHistoryView;

import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;
import model.portfolio.PortfolioHistoryModel;

/**
 * 
 * @author Daniel
 */
public class PortfolioHistoryController implements IController{
	private PortfolioHistoryModel portfolioModel;
	private PortfolioHistoryView portfolioHistoryView;

	public static void main(String args[]) {
		PortfolioHistoryController phc = new PortfolioHistoryController();
		phc.display(JPAHelper.getInstance().getAllPortfolios().get(0));
	}
	@Override
	public void display(Object model) {
		portfolioModel = new PortfolioHistoryModel((PortfolioEntity)model);
		
		portfolioHistoryView = new PortfolioHistoryView(portfolioModel);
		
		portfolioHistoryView.display(portfolioModel);
	}

	@Override
	public void cleanup() {} //NOPMD
}
