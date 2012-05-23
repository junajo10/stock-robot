package controller;

import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import view.IView;
import view.StockTableView;


/**
 * Controller class for the StockTableView, basically tell's the
 * view to update whenever new price data has been stored to the DB
 * 
 * @author kristian
 *
 */
public class StockTableController implements IController {
	
	private IView view;
	private Map<String, EventListener> retList;
	
	public StockTableController() {
		
		retList = new HashMap<String,EventListener>();
		
		view = new StockTableView();
		view.addActions( retList );
	}

	@Override
	public void display(Object model) {
		
		((StockTableView) view).init();
	}

	@Override
	public void cleanup() {
		
		
	}
}