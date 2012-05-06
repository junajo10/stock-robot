package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import model.robot.StartModel;

import utils.global.Pair;
import view.StartView;

public class StartController implements IController {
	StartView view = new StartView();
	StartModel model;
	List<IController> subControllers = new ArrayList<IController>();
	
	
	ActionListener startAstroAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (IController c : subControllers) {
				if (c.getName().contentEquals("Astro Controller")) {
					c.display(null);
				}
			}
		}
	};
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	}

	@Override
	public void display(Object model) {
		this.model = (StartModel) model;
		
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
		
		actions.add(new Pair<String, ActionListener>("Start Parser", null));
		actions.add(new Pair<String, ActionListener>("Stop Parser", null));
		
		actions.add(new Pair<String, ActionListener>("Start Astro", startAstroAction));
		actions.add(new Pair<String, ActionListener>("Stop Astro", null));
		
		return actions;
	}

	@Override
	public void addSubController(IController subController) {
		subControllers.add(subController);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void defineSubControllers() {
		// TODO Auto-generated method stub
		
	}

}
