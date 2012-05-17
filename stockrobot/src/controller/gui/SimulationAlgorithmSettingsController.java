package controller.gui;

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
	
	private Map<String,EventListener> actionListeners;
	
	private AlgorithmSettingsView view;
	
	List<AlgorithmSettingDouble> algortihmSettingsDouble;
	List<AlgorithmSettingLong> algortihmSettingsLong;
	
	private ActionListener saveDown = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (algortihmSettingsDouble != null) {
				model.giveDoubleSettings(algortihmSettingsDouble);
			}
			if (algortihmSettingsLong != null) {
				model.giveLongSettings(algortihmSettingsLong);
			}
		}
	};
	public SimulationAlgorithmSettingsController( String algorithmName ) {
		
		
		actionListeners = new HashMap<String, EventListener>();
		actionListeners.put( AlgorithmSettingsView.SAVE_DOWN, saveDown );
		
		view = new AlgorithmSettingsView( algorithmName );
		view.addActions( getActionListeners() );
		
		IAlgorithm algorithm = PluginAlgortihmLoader.getInstance().loadAlgorithm(algorithmName);
		
		algortihmSettingsDouble = algorithm.getDefaultDoubleSettings();
		algortihmSettingsLong = algorithm.getDefaultLongSettings();
		view.populateDoubleSettings(algortihmSettingsDouble);
		view.populateLongSettings(algortihmSettingsLong);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		
	}

	@Override
	public void display(Object model) {
		this.model = (SimModel) model;

		view.display(null);
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