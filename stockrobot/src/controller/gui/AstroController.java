package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import model.robot.AstroModel;

import utils.global.Pair;
import view.AstroView;

/**
 * 
 * @author daniel
 *
 */
public class AstroController implements IController {
	
	public static final String CLASSNAME = "Astro Controller";
	public static final String OPEN_GRAPHWINDOW = "openGraphwindow";
	public static final String START_SIMULATION = "startSimulation";
	
	AstroModel model;
	AstroView view = new AstroView();
	List<IController> subControllers = new ArrayList<IController>();
	
	//Hardcoded sub controllers:
	private IController graphController;
	
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
	
	ActionListener openGraph = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (IController c : subControllers) {
				if (c.getName().contentEquals(GraphController.CLASS_NAME)) {
					c.display(null);
				}
			}
		}
	};
	
	public AstroController() {
		
		//For now, just call this when the constructor is called. That way differentiating define from display
		defineSubControllers();
	}
	
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
		actions.add(new Pair<String, ActionListener>(START_SIMULATION, startSim));
		actions.add(new Pair<String, ActionListener>(OPEN_GRAPHWINDOW, openGraph));
		return actions;
	}

	@Override
	public void addSubController(IController subController) {
		this.subControllers.add(subController);
		
	}

	@Override
	public String getName() {
		return CLASSNAME;
	}

	@Override
	public void defineSubControllers() {
		
		graphController = new GraphController();
		this.subControllers.add( graphController );
	}
}