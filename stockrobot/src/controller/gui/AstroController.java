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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(Object model) {
		view.addActions(getActionListeners());
		view.display(model);
	}

	@Override
	public void cleanup() {
		view.cleanup();
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
