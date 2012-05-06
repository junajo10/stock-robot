package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import model.simulation.SimModel;

import utils.global.Pair;
import view.SimView;

public class SimController implements IController {
	SimView view = new SimView();
	SimModel model;
	List<IController> subControllers = new ArrayList<IController>();
	
	ActionListener startSimulation = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("apapapa");
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
	public List<Pair<String, ActionListener>> getActionListeners() {
		List<Pair<String, ActionListener>> actions = new ArrayList<Pair<String,ActionListener>>();
		actions.add(new Pair<String, ActionListener>("Start Simulation", startSimulation));
		actions.add(new Pair<String, ActionListener>("ComboboxListener", comboBoxListener));
		return actions;
	}

	@Override
	public void addSubController(IController subController) {
		this.subControllers.add(subController);
	}

	@Override
	public String getName() {
		return "SimController";
	}

}
