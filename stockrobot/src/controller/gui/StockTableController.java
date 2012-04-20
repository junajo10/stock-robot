package controller.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;

import utils.global.FinancialLongConverter;
import utils.global.Pair;
import view.StockTableView;


import gui.mvc.Constants;

/**
 * Controller class for the StockTableView, basically tell's the
 * view to update whenever new price data has been stored to the DB
 * 
 * @author kristian
 *
 */
public class StockTableController implements PropertyChangeListener, IController {
	
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
	
	/**
	 * Populate
	 * 
	 * Take all stockNames, and all data that needs to be shown in the JTable, and put them
	 * arrays that can be sent to the JTable class. Then return them as a tuple
	 * 
	 * @return
	 */
	public Pair<Object[][], Object[]> populate() {
		
		//Database connection
		IJPAHelper jpa = JPAHelper.getInstance();
		List<Pair<StockNames,List<StockPrices>>> stockInfo = jpa.getStockInfo(1);
		
		//Define column names
		String[] tableColumnNames = {"Name","Market","Buy","Sell","Latest","Volume","Time"};
		
		//Create storage for row data
		Object[][] rows = new Object[stockInfo.size()][tableColumnNames.length];
		
		//Collect row data from DB
		for( int i = 0; i < stockInfo.size(); i ++ ) {
			
			rows[ i ][ 0 ] = stockInfo.get( i ).getLeft().getName();
			rows[ i ][ 1 ] = stockInfo.get( i ).getLeft().getMarket();
			
			rows[ i ][ 2 ] = FinancialLongConverter.toStringTwoDecimalPoints( stockInfo.get( i ).getRight().get(0).getBuy() );
			rows[ i ][ 3 ] = FinancialLongConverter.toStringTwoDecimalPoints( stockInfo.get( i ).getRight().get(0).getSell() );
			rows[ i ][ 4 ] = FinancialLongConverter.toStringTwoDecimalPoints( stockInfo.get( i ).getRight().get(0).getLatest() );
			rows[ i ][ 5 ] = stockInfo.get( i ).getRight().get(0).getVolume();
			rows[ i ][ 6 ] = stockInfo.get( i ).getRight().get(0).getTime();
		}
		
		//Return row data and column names
		return new Pair<Object[][], Object[]>(rows, tableColumnNames);
	}
}