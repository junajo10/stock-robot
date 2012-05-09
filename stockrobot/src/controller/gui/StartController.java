package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.robot.StartModel;

import view.StartView;

public class StartController implements IController {
	StartView view = new StartView();
	StartModel model;
	List<IController> subControllers = new ArrayList<IController>();
	
	IController harvester;
	IController astroController;
	
	public static String name = "StartController";
	
	ActionListener startAstroAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			astroController.display(null);
		}
	};
	ActionListener startHavester = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			harvester.display(null);
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
	public Map<String, EventListener> getActionListeners() {
		Map<String, EventListener> actions = new HashMap<String,EventListener>();
		
		actions.put("Start Parser", startHavester);
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
		return name;
	}

	@Override
	public void defineSubControllers() {
		if (harvester == null)
			harvester = new HarvesterController();
		
		if (astroController == null)
			astroController = new AstroController();

	}

}
