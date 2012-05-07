package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.robot.StartModel;

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
		defineSubControllers();
		
		this.model = (StartModel) model;
		
		view.addActions(getActionListeners());
		
		view.display(this.model);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, ActionListener> getActionListeners() {
		Map<String, ActionListener> actions = new HashMap<String,ActionListener>();
		
		actions.put("Start Parser", null);
		actions.put("Stop Parser", null);
		
		actions.put("Start Astro", startAstroAction);
		actions.put("Stop Astro", null);
		
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
		subControllers.add(new AstroController());
	}

}
