package gui.controller;

import gui.StockInfoGUI;
import gui.view.AlgorithmSettingsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * 
 * @author kristian
 *
 */
public class MainMenuController implements IController {

	public MainMenuController() {
		
		
	}
	
	public void bindStockInfoGUIButton( JButton btn ) {
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				StockInfoGUI stockInfoWindow = new StockInfoGUI();
			}
		});
	}
	
	public void bindAlgorithmSettingsGUIButton( JButton btn ) {
		
		btn.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				AlgorithmSettingsView view = new AlgorithmSettingsView( "testalgo" );
			}
		});
	}
}
