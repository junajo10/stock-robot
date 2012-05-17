package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.portfolio.IPortfolio;

import view.AlgorithmSettingsView;

public class AlgorithmSettingsController implements IController {

	public static final String CLASS_NAME = "AlgorithmSettingsController";
	
	
	private Map<String,EventListener> actionListeners;
	
	private AlgorithmSettingsView view;
	
	private IPortfolio portfolio;
	
	private ActionListener saveDown = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			IJPAHelper jpaHelper = JPAHelper.getInstance();
			
			jpaHelper.updateObject( portfolio.getPortfolioTable() );
			
			portfolio.updateSettings();
		}
	};
	
	public AlgorithmSettingsController( IPortfolio portfolio ) {
		
		this.portfolio = portfolio;
		
		actionListeners = new HashMap<String, EventListener>();
		actionListeners.put( AlgorithmSettingsView.SAVE_DOWN, saveDown );
		
		view = new AlgorithmSettingsView( portfolio.getAlgorithm().getName() );
		view.addActions( getActionListeners() );
		view.populateDoubleSettings( portfolio.getPortfolioTable().getAlgortihmSettings().getDoubleSettings() );
		view.populateLongSettings( portfolio.getPortfolioTable().getAlgortihmSettings().getLongSettings() );
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		
	}

	@Override
	public void display(Object model) {
		
		System.out.println( "AlgoSettingsController: display" );
		
		//No model yet
		view.display(new Object());
	}

	@Override
	public void cleanup() {
		
		
	}

	@Override
	public Map<String, EventListener> getActionListeners() {
		
		return actionListeners;
	}

	@Override
	public void defineSubControllers() {
		
		
	}

	@Override
	public String getName() {
		
		return CLASS_NAME;
	}
}