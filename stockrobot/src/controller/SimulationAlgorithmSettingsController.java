package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.algorithms.loader.PluginAlgortihmLoader;
import model.database.jpa.tables.AlgorithmSettingDouble;
import model.database.jpa.tables.AlgorithmSettingLong;
import model.portfolio.IAlgorithm;
import model.simulation.SimModel;

import view.AlgorithmSettingsView;

public class SimulationAlgorithmSettingsController implements IController {

	public static final String CLASS_NAME = "AlgorithmSettingsController";
	private SimModel model;
	private String algorithmName;
	private Map<String,EventListener> actionListeners;
	
	private AlgorithmSettingsView view;
	
	List<AlgorithmSettingDouble> algortihmSettingsDouble;
	List<AlgorithmSettingLong> algortihmSettingsLong;
	
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
	public void propertyChange(PropertyChangeEvent evt) {} //NOPMD

	@Override
	public void display(Object model) {
		view = new AlgorithmSettingsView( algorithmName );
		view.init();
		view.addActions( getActionListeners() );
		
		IAlgorithm algorithm = PluginAlgortihmLoader.getInstance().loadAlgorithm(algorithmName);
		
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

	@Override
	public Map<String, EventListener> getActionListeners() {
		if (actionListeners == null) {
			actionListeners = new HashMap<String, EventListener>();
			actionListeners.put( AlgorithmSettingsView.SAVE_DOWN, saveDown );
		}
		
		return actionListeners;
	}

	@Override
	public void defineSubControllers() {} //NOPMD

	@Override
	public String getName() {
		
		return CLASS_NAME;
	}
}