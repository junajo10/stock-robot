package gui.controller;

import gui.StockInfoGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

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
}
