package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;

import utils.global.FinancialLongConverter;
import view.graph.GraphView;

/**
 * Controller for the simple graph view
 * 
 * @author kristian
 *
 */
public class GraphController implements IController {
	
	//Keep a reference of the view to be able to insert and read stuff from it
	GraphView _view;
	
	/**
	 * Generate a listener that looks into the model, searches for the wanted stock, and collects all of it's prices recorded
	 * 
	 * @return
	 */
	public ActionListener bindAddStock() {
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed( ActionEvent arg0 ) {
				
				//Sign up for the jpaHelper
				IJPAHelper jpaHelper = JPAHelper.getInstance();
				List<StockNames> nameList = jpaHelper.getAllStockNames();
				
				//Search through all stocknames to find the right one if it exists
				//TODO: Make this search more effective! Maybe by adding a new method to JPAHelper that does this
				for( StockNames st : nameList ) {
					
					//If match
					if( st.getName().equals( _view.getCurrentWantedStock() ) ) {
						
						//Create new series
						final TimeSeries series = new TimeSeries( st.getName() );
						
						//Get all of the stock's prices
						List<StockPrices> priceList = jpaHelper.getPricesForStock( st );
						
						//Add all prices through time to the serie
						for( StockPrices sp : priceList ) {
							
							series.addOrUpdate( new Minute( sp.getTime() ), FinancialLongConverter.toDouble( sp.getLatest() ) );
						}
						
						//Insert serie to the view / model of the view
						_view.insertSeries( series );
					}
				}
			}
		};
	}
	
	/**
	 * Bind a view of type GraphView (tight coupling here) to this controller
	 * 
	 * @param view
	 */
	public void bindGraphView( GraphView view ) {
		
		_view = view; 
	}
}