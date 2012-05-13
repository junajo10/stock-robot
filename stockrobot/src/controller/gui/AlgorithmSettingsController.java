package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.AlgorithmSettingDouble;
import model.database.jpa.tables.AlgorithmSettingLong;
import model.database.jpa.tables.AlgorithmSettings;
import model.portfolio.IAlgorithm;
import model.portfolio.IPortfolio;

import view.AlgorithmSettingsView;

public class AlgorithmSettingsController implements IController {

	public static final String CLASS_NAME = "AlgorithmSettingsController";
	
	
	private Map<String,EventListener> actionListeners;
	
	private AlgorithmSettingsView view;
	
	private IAlgorithm algo;
	private IPortfolio portfolio;
	
	//TODO: Daniel said it should be possible to update algorithm settings by just updating the portfolio.getPorfolioTable
	//		However that doesn't seem to work for me, so I use these variables in the mean time:
//	private Set<AlgorithmSettingDouble> doubleSettings;
//	private Set<AlgorithmSettingLong> longSettings;
	
	private ActionListener saveDown = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			IJPAHelper jpaHelper = JPAHelper.getInstance();
			
//			AlgorithmSettingLong[] settings = longSettings.toArray( new AlgorithmSettingLong[0] );
			jpaHelper.updateObject( portfolio.getPortfolioTable() );
			
//			for( AlgorithmSettingLong setting : settings ) {
				
//				jpaHelper.updateObject( setting );
//			}
		}
	};
	
	public AlgorithmSettingsController( IAlgorithm algo, IPortfolio portfolio ) {
		
		this.algo = algo;
		this.portfolio = portfolio;
		
		actionListeners = new HashMap<String, EventListener>();
		actionListeners.put( AlgorithmSettingsView.SAVE_DOWN, saveDown );
		
		view = new AlgorithmSettingsView( "Dummy value." );
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