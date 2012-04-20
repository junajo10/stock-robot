package gui.controller;

import gui.StockInfoGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.AlgorithmSettingsView;

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
				
				//The purpose of this line is just to open a new JFrame
				new StockInfoGUI();
			}
		});
	}
	
	public void bindAlgorithmSettingsGUIButton( JButton btn ) {
		
		btn.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//The purpose of this line is just to open a new JFrame
				new AlgorithmSettingsView( "testalgo" );
			}
		});
	}
}
