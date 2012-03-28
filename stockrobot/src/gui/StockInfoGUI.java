package gui;

import gui.view.StockTableView;

import javax.swing.JFrame;

public class StockInfoGUI extends JFrame {
	
	StockTableView view = new StockTableView();
	
	public StockInfoGUI() {
		
		setBounds( 100, 200, 800, 600 );
		add( view );
		
		this.setVisible( true );
	}
}