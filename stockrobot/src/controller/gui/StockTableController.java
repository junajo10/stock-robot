package controller.gui;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
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

	public static final String CLASS_NAME = "StockTableController";
	
	private IView _view;
	private Map<String, ActionListener> retList;
	
	public StockTableController() {
		
		retList = new HashMap<String,ActionListener>();
		
		_view = new StockTableView();
		_view.addActions( retList );
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
		
	}

	@Override
	public void display(Object model) {
		
		((StockTableView)_view).init();
	}

	@Override
	public void cleanup() {
		
		
	}

	@Override
	public Map<String, ActionListener> getActionListeners() {
		
		return retList;
	}

	@Override
	public void addSubController(IController subController) {
		
		
	}

	@Override
	public void defineSubControllers() {
		
		//None to define
	}

	@Override
	public String getName() {
		
		return CLASS_NAME;
	}
}