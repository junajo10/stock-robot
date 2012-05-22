package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;

import model.simulation.SimModel;

import utils.global.FinancialLongConverter;
import view.SimView;

/**
 * 
 * @author Daniel
 */
public class SimController implements IController {
	private final SimView view = new SimView();
	private SimModel model;
	
	
	private SimulationAlgorithmSettingsController settingController;
	private SimResultController simResultController;
	
	private final ActionListener startSimulation = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent event) {
			model.setInitialValue(FinancialLongConverter.toFinancialLong(Long.valueOf(view.getInitialValue())));
			
			if (simResultController != null) {
				simResultController.display(model);
			}
		}
	};
	private final ActionListener comboBoxListener = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent event) {
			model.setAlgorithm((String)((JComboBox)event.getSource()).getSelectedItem());
		}
	};
	
	private final ActionListener configureAlgorithm = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent event) {
			settingController = new SimulationAlgorithmSettingsController(model.getAlgorithm());
			settingController.display(model);
		}
	};

	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}

	@Override
	public void display(final Object model) {
		defineSubControllers();
		
		if (this.model == null) {
			this.model = new SimModel();
		}
		
		
		view.addActions(getActionListeners());
		view.display(this.model);
		
	}

	@Override
	public void cleanup() {
		view.cleanup();
	}

	@Override
	public Map<String, EventListener> getActionListeners() {
		final Map<String, EventListener> actions = new HashMap<String,EventListener>();
		actions.put(SimView.STARTSIMULATION, startSimulation);
		actions.put(SimView.COMBOBOX, comboBoxListener);
		actions.put(SimView.CONFIGUREALGORTIHM, configureAlgorithm);
		return actions;
	}

	@Override
	public String getName() {
		return "SimController";
	}

	@Override
	public void defineSubControllers() {
		simResultController = new SimResultController();
	}

}
