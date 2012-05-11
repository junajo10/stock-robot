package controller.gui;

import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import view.AlgorithmSettingsView;

public class AlgorithmSettingsController implements IController {

	public static final String CLASS_NAME = "AlgorithmSettingsController";
	
	private Map<String,EventListener> actionListeners;
	
	AlgorithmSettingsView view;
	
	public AlgorithmSettingsController() {
		
		actionListeners = new HashMap<String, EventListener>();
		
		view = new AlgorithmSettingsView( "Dummy value." );
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