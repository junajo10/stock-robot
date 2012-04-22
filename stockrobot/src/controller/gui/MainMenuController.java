package controller.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.StockInfoGUI;
import viewfactory.ViewFactory;

/**
 * 
 * @author kristian
 *
 */
public class MainMenuController implements IController {
	StockInfoGUI stockInfoGUI;
	public MainMenuController() {
		
		
	}
	public void bindStockInfoGUI(StockInfoGUI stockInfoGUI) {
		this.stockInfoGUI = stockInfoGUI;
	}
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
				
				ViewFactory.getGetSettingsPanel( "testalgo" );
			}
		};
	}
}
