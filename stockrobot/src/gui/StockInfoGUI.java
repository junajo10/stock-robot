package gui;

import gui.components.GUIFactory;
import gui.view.StockTableView;

import javax.swing.JFrame;

/**
 * Class to wrap the Stock Table View and possibly other related classes
 * 
 * @author kristian
 *
 */
public class StockInfoGUI extends JFrame {
	
	private static final String WINDOW_TITLE = "All publicly traded companies currently recorded in the database.";
	
	private						StockTableView view;
	
	public StockInfoGUI() {
		
		GUIFactory guiFactory = new GUIFactory();
		
		view = (StockTableView) guiFactory.getStockTableView();
		
		setBounds( 100, 200, 800, 600 );
		add( view );
		
		//Set title
		setTitle( WINDOW_TITLE );
		
		this.setVisible( true );
	}
}