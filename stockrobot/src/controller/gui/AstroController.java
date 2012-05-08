package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.robot.AstroModel;

import utils.WindowCloseAdapter;
import view.AstroView;

/**
 * 
 * @author Daniel
 */
public class AstroController implements IController {
	
	public static final String CLASSNAME = "Astro Controller";
	public static final String OPEN_GRAPHWINDOW = "openGraphwindow";
	public static final String START_SIMULATION = "startSimulation";
	public static final String OPEN_STOCKTABLE = "stockTable";
	public static final String WINDOW_CLOSE = "windowClose";
	
	AstroModel model;
	AstroView view = new AstroView();
	List<IController> subControllers = new ArrayList<IController>();
	
	//Hardcoded sub controllers:
	private IController graphController;
	private IController stockInfoController;
	
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
	
	ActionListener openStockInfo = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (IController c : subControllers) {
				if (c.getName().contentEquals(StockTableController.CLASS_NAME)) {
					c.display(null);
				}
			}
		}
	};
	
	WindowListener windowClose = new WindowCloseAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			cleanup();
		}
	};
	
	public AstroController() {

	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}

	@Override
	public void display(Object model) {
		defineSubControllers();
		
		if (this.model == null) {
			this.model = new AstroModel();
		}
		
		view.addActions(getActionListeners());
		
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
	public Map<String, EventListener> getActionListeners() {
		Map<String, EventListener> actions = new HashMap<String,EventListener>();
		actions.put(START_SIMULATION, startSim);
		actions.put(OPEN_GRAPHWINDOW, openGraph);
		actions.put(OPEN_STOCKTABLE, openStockInfo);
		actions.put(WINDOW_CLOSE, windowClose);
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
		
		stockInfoController = new StockTableController();
		this.subControllers.add( stockInfoController );
		
		subControllers.add(new SimController());
	}
}