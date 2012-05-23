package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;

import model.simulation.SimModel;

import utils.AbstractWindowCloseAdapter;
import utils.global.FinancialLongConverter;
import view.SimView;

/**
 * 
 * @author Daniel
 */
public class SimController implements IController {
	private final SimView view = new SimView();
	private SimModel model = new SimModel();
	
	
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
	
	private final WindowListener windowClose = new AbstractWindowCloseAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			cleanup();
		}
	};

	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}

	@Override
	public void display(final Object model) {
		defineSubControllers();
		
		view.addActions(getActionListeners());
		view.display(this.model);
	}

	@Override
	public void cleanup() {
		view.cleanup();
	}

	public Map<String, EventListener> getActionListeners() {
		final Map<String, EventListener> actions = new HashMap<String,EventListener>();
		actions.put(SimView.STARTSIMULATION, startSimulation);
		actions.put(SimView.COMBOBOX, comboBoxListener);
		actions.put(SimView.CONFIGUREALGORTIHM, configureAlgorithm);
		actions.put(SimView.WINDOWCLOSE, windowClose);
		return actions;
	}

	public void defineSubControllers() {
		simResultController = new SimResultController();
	}

}
