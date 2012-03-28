package gui.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import gui.mvc.Constants;
import gui.view.StockTableView;

/**
 * Controller class for the StockTableView, basically tell's the
 * view to update whenever new price data has been stored to the DB
 * 
 * @author kristian
 *
 */
public class StockTableController implements PropertyChangeListener {
	
	private StockTableView _view;
	
	/**
	 * Receive the view
	 * 
	 * @param view
	 */
	public StockTableController( StockTableView view ) {
		
		_view = view;
	}

	/**
	 * Call updateInfo in the view when new data is available in the prices DB
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if( evt.getPropertyName().equals( Constants.EVENT_TYPE.PRICES_UPDATED ) )
			_view.updateInfo();
	}
}