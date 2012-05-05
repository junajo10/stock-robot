package controller.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.SimulationView;
import view.StockInfoGUI;
import view.StockTableHistoryView;
import view.graph.GraphView;
import viewfactory.ViewFactory;

/**
 * 
 * @author kristian
 *
 */
public class MainMenuController implements IController {

	private StockInfoGUI stockInfoGUI;
	private SimulationView simulationView;
	private GraphView graphView;
	private StockTableHistoryView sthw;
	
	public MainMenuController() {
		
		
	}
	
	public void bindStockInfoGUI(StockInfoGUI stockInfoGUI) {
		
		this.stockInfoGUI = stockInfoGUI;
	}

	public void bindSimulationView(SimulationView simulationView) {
		this.simulationView = simulationView;
	}
	
	public void bindGraphView(GraphView graphView) {
		
		this.graphView = graphView;
	}
	
	public void bindHistoryView(StockTableHistoryView sthw) {
		this.sthw = sthw;
	}
	
	//Getters for testing
	public StockInfoGUI getStockInfoGUI() { return stockInfoGUI; }
	
	public SimulationView getSimulationView() { return simulationView; }
	
	public GraphView getGraphView() { return graphView; }
	
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
	
	public ActionListener bindOpenGraphButton() {
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//The purpose of this line is just to open a new JFrame
				graphView.init();
			}
		};
	}
	
	public ActionListener bindHistoryButton() {
		
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//The purpose of this line is just to open a new JFrame
				sthw.init();
			}
		};
	}
	
}
