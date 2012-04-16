package gui;

import gui.components.GUIFactory;
import gui.controller.StockTableController;
import gui.view.StockTableView;

import javax.swing.JFrame;

import robot.AlphaReceiver;

/**
 * Class to wrap the Stock Table View and possibly other related classes
 * 
 * @author kristian
 *
 */
public class StockInfoGUI extends JFrame {
	
	private static final	String 					WINDOW_TITLE = "All publicly traded companies currently recorded in the database.";
	
	private					StockTableView 			view;
	private					StockTableController 	controller;
	
	public StockInfoGUI() {
		
		//TODO: Remove after alpha!
		AlphaReceiver receiveNotifier = new AlphaReceiver();
		
		GUIFactory guiFactory = new GUIFactory();
		
		view = new StockTableView();
		controller = new StockTableController( view );
		view.registerController( controller );
		view.init();
		receiveNotifier.addAddObserver(controller);
		
		setBounds( 100, 200, 800, 600 );
		add( view );
		
		//Set title
		setTitle( WINDOW_TITLE );
		
		this.setVisible( true );
	}
}