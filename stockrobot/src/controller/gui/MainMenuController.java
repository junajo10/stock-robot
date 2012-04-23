package controller.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

<<<<<<< HEAD
=======
import javax.swing.JFrame;

import view.SimulationView;
import view.StockInfoGUI;
>>>>>>> 4fc7c7d616c7b1ff72869a6e3c734dc27bed7d83
import viewfactory.ViewFactory;

/**
 * 
 * @author kristian
 *
 */
public class MainMenuController implements IController {
<<<<<<< HEAD
=======
	StockInfoGUI stockInfoGUI;
	SimulationView simulationView;
>>>>>>> 4fc7c7d616c7b1ff72869a6e3c734dc27bed7d83
	public MainMenuController() {
		
		
	}
	
	public void bindStockInfoGUI() {
	}
<<<<<<< HEAD
	
=======
	public void bindSimulationView(SimulationView simulationView) {
		this.simulationView = simulationView;
	}
>>>>>>> 4fc7c7d616c7b1ff72869a6e3c734dc27bed7d83
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
				
<<<<<<< HEAD
				ViewFactory.getGetSettingsPanel("Blaha");
=======
				ViewFactory.getGetSettingsPanel( "testalgo" );
			}
		};
	}
	public ActionListener bindSimulationButton() {
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("aoaoaoa");
				//The purpose of this line is just to open a new JFrame
				simulationView.init();
>>>>>>> 4fc7c7d616c7b1ff72869a6e3c734dc27bed7d83
			}
		};
	}
}
