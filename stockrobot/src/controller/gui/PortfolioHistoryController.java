package controller.gui;

import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

import view.PortfolioHistoryView;

import model.portfolio.PortfolioHistoryModel;

/**
 * 
 * @author Daniel
 */
public class PortfolioHistoryController implements IController{
	private static final String name = "StockTableHistory";
	
	
	public static void main(String args[]) {
		PortfolioHistoryController sthc = new PortfolioHistoryController();
		sthc.display(null);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(Object model) {
		PortfolioHistoryModel portfolioModel = new PortfolioHistoryModel();
		
		new PortfolioHistoryView(portfolioModel);
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
	public void addSubController(IController subController) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defineSubControllers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return name;
	}

}
