package controller.gui;

import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

import model.portfolio.IPortfolioHandler;

import view.IView;
import view.PortfolioView;


public class PortfolioController implements IController {

	public static final String CLASS_NAME = "PortfolioController";
	private IView view;
	
	public PortfolioController(){
		
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(Object model) {
		
		view = new PortfolioView();
		if(model instanceof IPortfolioHandler){
			view.display(model);
		}
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

		return CLASS_NAME;
	}

}
