package view;


import javax.swing.JFrame;
import javax.swing.JPanel;

import viewfactory.ViewFactory;


/**
 * Class to wrap the Stock Table View and possibly other related classes
 * 
 * @author kristian
 *
 */
public class StockInfoGUI extends JFrame {
	
	/**
	 * Serial version!
	 */
	private static final long serialVersionUID = 2592063216811547338L;

	private static final	String 					WINDOW_TITLE = "All publicly traded companies currently recorded in the database.";
	
	private					JPanel 					view;
	//private					StockTableController 	controller;
	
	public StockInfoGUI() {
		init();
	}

	public void init() {

		//TODO: Remove after alpha!
		
		view = ViewFactory.getStockTableView();
		
		setBounds( 100, 200, 800, 600 );
		add( view );
		
		//Set title
		setTitle( WINDOW_TITLE );
		
		this.setVisible( true );
		
	}
}