package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import model.robot.AstroModel;

import utils.global.Pair;
import view.AstroView;

public class AstroController implements IController {
	AstroModel model;
	AstroView view = new AstroView();
	List<IController> subControllers = new ArrayList<IController>();
	
	ActionListener startSim = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (IController c : subControllers) {
				if (c.getName().contentEquals("SimController")) {
					c.display(null);
				}
			}
		}
	};
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if (evt.getPropertyName().contentEquals("Window Close")) {
			System.out.println("AstroController: view closing, calling cleanup");
			
			cleanup();
		}
		
		
	}

	@Override
	public void display(Object model) {
		
		
		if (this.model == null) {
			this.model = new AstroModel();
		}
		
		view.addActions(getActionListeners());
		
		view.addPropertyChangeListener(this);
		
		view.display(this.model);
	}

	@Override
	public void cleanup() {
		view.removePropertyChangeListener(this);
		view.cleanup();
		
		model.cleanup();
		model = null;
	}

	@Override
	public List<Pair<String, ActionListener>> getActionListeners() {
		List<Pair<String, ActionListener>> actions = new ArrayList<Pair<String,ActionListener>>();
		actions.add(new Pair<String, ActionListener>("Start Simulation", startSim));
		return actions;
	}

	@Override
	public void addSubController(IController subController) {
		this.subControllers.add(subController);
		
	}

	@Override
	public String getName() {
		return "Astro Controller";
	}

}
