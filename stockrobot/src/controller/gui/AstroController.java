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

import model.portfolio.PortfolioHandler;
import model.robot.AstroModel;

import utils.WindowCloseAdapter;
import view.AstroView;

/**
 * 
 * @author Daniel
 */
public class AstroController implements IController {
	
	public static final String CLASSNAME = "Astro Controller";
	
	AstroModel model;
	AstroView view = new AstroView();
	List<IController> subControllers = new ArrayList<IController>();
	
	//Hardcoded sub controllers:
	private IController graphController;
	private IController stockInfoController;
	private IController portfolioController;
	private IController algorithmSettingsController;
	
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
	
	ActionListener openPortfolioView = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (IController c : subControllers) {
				if (c.getName().equals(PortfolioController.CLASS_NAME)) {
					
					c.display(null);
				}
			}
		}
	};
	
	ActionListener openAlgorithmSettingsView = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (IController c : subControllers) {
				if (c.getName().contentEquals(AlgorithmSettingsController.CLASS_NAME)) {
					
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
		
		if (this.model == null) {
			this.model = new AstroModel();
		}
		
		view.addActions(getActionListeners());
		
		view.display(this.model);
		defineSubControllers();
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
		actions.put(AstroView.START_SIMULATION, startSim);
		actions.put(AstroView.OPEN_GRAPHWINDOW, openGraph);
		actions.put(AstroView.OPEN_STOCKTABLE, openStockInfo);
		actions.put(AstroView.OPEN_PORTFOLIOVIEW, openPortfolioView);
		actions.put(AstroView.OPEN_ALGORTIHM_SETTINGS, openAlgorithmSettingsView);
		actions.put(AstroView.WINDOW_CLOSE, windowClose);
		return actions;
	}


	@Override
	public String getName() {
		
		return CLASSNAME;
	}

	@Override
	public void defineSubControllers() {
		
		graphController = new GraphController();
		subControllers.add( graphController );
		
		stockInfoController = new StockTableController();
		subControllers.add( stockInfoController );
		
		subControllers.add(new SimController());
		
		portfolioController = new PortfolioController(model.getTrader(), PortfolioHandler.getInstance());
		subControllers.add( portfolioController );
		
		algorithmSettingsController = new AlgorithmSettingsController();
		subControllers.add( algorithmSettingsController );
	}
}