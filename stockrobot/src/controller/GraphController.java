package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;

import utils.global.FinancialLongConverter;
import utils.global.Log;
import view.graph.GraphView;

/**
 * Controller for the simple graph view
 * 
 * @author kristian
 *
 */
public class GraphController implements IController {
	
	public static final String CLASS_NAME = "GraphController";
	public static final String BIND_GRAPH_VIEW = "bindGraphView";
	
	public static final String WINDOW_TITLE = "Stock prices on a graph";
	
	//Keep a reference of the view to be able to insert and read stuff from it
	private GraphView view;
	private Map<String, EventListener> retList;
	
	public GraphView getView() { return view; }

	public GraphController() {
		
		retList = new HashMap<String,EventListener>();
		retList.put(BIND_GRAPH_VIEW, bindGraphView());
		
		view = new GraphView( WINDOW_TITLE );
		view.addActions(getActionListeners());
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
		Log.log(Log.TAG.VERY_VERBOSE, "GraphView: Property Change");
	}

	@Override
	public void display(Object model) {
		
		view.init();
	}

	@Override
	public void cleanup() {
		
		view = null;
	}

	public ActionListener bindGraphView() {
		
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
					if( st.getName().equals( view.getCurrentWantedStock() ) ) {
		
						//Create new series
						final TimeSeries series = new TimeSeries( st.getName() );
		
						//Get all of the stock's prices
						List<StockPrices> priceList = jpaHelper.getPricesForStock( st );
		
						//Add all prices through time to the serie
						for( StockPrices sp : priceList ) {
		
							series.addOrUpdate( new Minute( sp.getTime() ), FinancialLongConverter.toDouble( sp.getLatest() ) );
						}
		
						//Insert serie to the view / model of the view
						view.insertSeries( series );
					}
				}
			}
		};
	}
	
	
	@Override
	public Map<String, EventListener> getActionListeners() {
		
		return retList;
	}
		
	/**
	 * Get Class Name
	 */
	@Override
	public String getName() {
		
		return CLASS_NAME;
	}

	@Override
	public void defineSubControllers() {
		
		//We have no sub controllers at this level
	}
}