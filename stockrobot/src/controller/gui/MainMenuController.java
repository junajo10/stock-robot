package controller.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import utils.global.Pair;
import view.MainMenuView;
/*import view.SimulationView;
import view.StockInfoGUI;
import view.StockTableHistoryView;
import view.graph.GraphView;
import viewfactory.ViewFactory;*/

/**
 * 
 * @author kristian
 *
 */
public class MainMenuController implements IController {

	//private StockInfoGUI stockInfoGUI;
	//private SimulationView simulationView;
	//private GraphView _graphView;
	//private StockTableHistoryView sthw;
	
	private static final String CLASS_NAME = "mainMenuController";
	
	private static final String OPEN_GRAPH_BUTTON = "openGraphButton";
	
	MainMenuView _view;
	
	private GraphController graphController;
	private List<Pair<String, ActionListener>> actionListeners;
	
	public MainMenuController() {
		
		actionListeners = new ArrayList<Pair<String,ActionListener>>();
	}
	
	/*
	public void bindStockInfoGUI(StockInfoGUI stockInfoGUI) {
		
		this.stockInfoGUI = stockInfoGUI;
	}

	public void bindSimulationView(SimulationView simulationView) {
		this.simulationView = simulationView;
	}
	
	public void bindHistoryView(StockTableHistoryView sthw) {
		this.sthw = sthw;
	}
	*/
	
	//Getters for testing
	/*
	public StockInfoGUI getStockInfoGUI() { return stockInfoGUI; }
	
	public SimulationView getSimulationView() { return simulationView; }
	
	public GraphView getGraphView() { return _graphView; }
	
	public StockTableHistoryView getStockTableHistoryView() { return sthw; }
	
	
	public ActionListener bindStockInfoGUIButton() {
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//The purpose of this line is just to open a new JFrame
				stockInfoGUI.init();
			}
		};
	}
	*/
	
	/*
	public ActionListener bindAlgorithmSettingsGUIButton() {
		
		return  new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//ViewFactory.getGetSettingsPanel("Blaha");
				ViewFactory.getGetSettingsPanel( "testalgo" );
			}
		};
	}
	
	public ActionListener bindSimulationButton() {
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//The purpose of this line is just to open a new JFrame
				simulationView.init();
			}
		};
	}
	*/
	public ActionListener bindOpenGraphButton() {
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//The purpose of this line is just to open a new JFrame
				
			}
		};
	}
	/*
	public ActionListener bindHistoryButton() {
		
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//The purpose of this line is just to open a new JFrame
				sthw.init();
			}
		};
	}
*/
	
	
	//_____________________________________________________________________________//
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
		
	}

	//Cascade downwards the tree
	@Override
	public void display(Object model) {
		
		_view = new MainMenuView();
		ActionListener openGraphListener = bindOpenGraphButton();
		actionListeners.add( new Pair<String, ActionListener>(OPEN_GRAPH_BUTTON, openGraphListener));
		_view.bindOpenGraphView( openGraphListener );
		
		_view.init();
		
		//For now, call defineSubControllers here
		defineSubControllers();
	}

	@Override
	public void cleanup() {
		
		//Cascade downwards the tree
		graphController.cleanup();
		graphController = null;
		
		_view = null;
	}

	@Override
	public Map<String, EventListener> getActionListeners() {
		
		return null;
	}

	@Override
	public void addSubController(IController subController) {
		
		
	}

	@Override
	public String getName() {
		
		return CLASS_NAME;
	}

	@Override
	public void defineSubControllers() {
		
		graphController = new GraphController();
	}	
}