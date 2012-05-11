package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;

import model.simulation.SimModel;

import view.SimView;

/**
 * 
 * @author Daniel
 */
public class SimController implements IController {
	SimView view = new SimView();
	SimModel model;
	List<IController> subControllers = new ArrayList<IController>();
	
	ActionListener startSimulation = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (IController c : subControllers) {
				if (c.getName().contentEquals("SimulationController")) {
					c.display(model);
				}
			}
			
		}
	};
	ActionListener comboBoxListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			model.setAlgorithm((String)((JComboBox)e.getSource()).getSelectedItem());
		}
	};
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(Object model) {
		defineSubControllers();
		
		if (this.model == null)
			this.model = new SimModel();
		
		
		view.addActions(getActionListeners());
		view.display(this.model);
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, EventListener> getActionListeners() {
		Map<String, EventListener> actions = new HashMap<String,EventListener>();
		actions.put("Start Simulation", startSimulation);
		actions.put("ComboboxListener", comboBoxListener);
		return actions;
	}

	@Override
	public String getName() {
		return "SimController";
	}

	@Override
	public void defineSubControllers() {
		subControllers.add(new SimResultController());
	}

}
