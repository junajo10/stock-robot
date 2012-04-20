package controller.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import viewfactory.ViewFactory;

/**
 * 
 * @author kristian
 *
 */
public class MainMenuController implements IController {
	public MainMenuController() {
		
		
	}
	
	public void bindStockInfoGUI() {
	}
	
	public ActionListener bindStockInfoGUIButton() {
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//The purpose of this line is just to open a new JFrame
				ViewFactory.getStockInfoGUI();
			}
		};
	}
	
	public ActionListener bindAlgorithmSettingsGUIButton() {
		
		return  new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				ViewFactory.getGetSettingsPanel("Blaha");
			}
		};
	}
}
