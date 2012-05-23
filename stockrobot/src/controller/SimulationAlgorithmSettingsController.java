package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.database.jpa.tables.AlgorithmSettingDouble;
import model.database.jpa.tables.AlgorithmSettingLong;
import model.portfolio.IAlgorithm;
import model.portfolio.AlgortihmLoader;
import model.simulation.SimModel;

import view.AlgorithmSettingsView;

public class SimulationAlgorithmSettingsController implements IController {
	private SimModel model;
	private String algorithmName;
	private Map<String,EventListener> actionListeners;
	
	private AlgorithmSettingsView view;
	
	private List<AlgorithmSettingDouble> algortihmSettingsDouble;
	private List<AlgorithmSettingLong> algortihmSettingsLong;
	
	private ActionListener saveDown = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (algortihmSettingsDouble != null) {
				algortihmSettingsDouble = view.getDoubleSettings();
				model.giveDoubleSettings(algortihmSettingsDouble);
			}
			if (algortihmSettingsLong != null) {
				algortihmSettingsLong = view.getLongSettings();
				model.giveLongSettings(algortihmSettingsLong);
			}
		}
	};

	public SimulationAlgorithmSettingsController( String algorithmName ) {
		this.algorithmName = algorithmName;
	}
	
	@Override
	public void display(Object model) {
		view = new AlgorithmSettingsView( algorithmName );
		view.init();
		view.addActions( getActionListeners() );
		
		IAlgorithm algorithm = AlgortihmLoader.getInstance().loadAlgorithm(algorithmName);
		
		algortihmSettingsDouble = algorithm.getDefaultDoubleSettings();
		algortihmSettingsLong = algorithm.getDefaultLongSettings();
		view.populateDoubleSettings(algortihmSettingsDouble);
		view.populateLongSettings(algortihmSettingsLong);
		
		this.model = (SimModel) model;

		this.model.giveDoubleSettings(algortihmSettingsDouble);
		this.model.giveLongSettings(algortihmSettingsLong);
		
		view.display(null);
	}

	@Override
	public void cleanup() {} //NOPMD

	public Map<String, EventListener> getActionListeners() {
		if (actionListeners == null) {
			actionListeners = new HashMap<String, EventListener>();
			actionListeners.put( AlgorithmSettingsView.SAVE_DOWN, saveDown );
		}
		
		return actionListeners;
	}

}