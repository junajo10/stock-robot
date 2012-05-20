package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
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
import view.graph.GraphStockTogglerView;
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
	
	private List<GraphStockTogglerController> subControllers;
	
	public GraphView getView() { return view; }
	
	public void init() {
		
		view = new GraphView( WINDOW_TITLE );
		view.addActions(getActionListeners());
		
		subControllers = new ArrayList<GraphStockTogglerController>();
		
		defineSubControllers();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
		//Fetch the associated stock name from the toggler interacted with
		String wantedStockName = ((GraphStockTogglerController) arg0.getSource() ).getAssociatedStockName();
		
		//Sign up for the jpaHelper
		final IJPAHelper jpaHelper = JPAHelper.getInstance();
		final List<StockNames> nameList = jpaHelper.getAllStockNames();
		
		//Search through all stocknames to find the right one if it exists
		for( StockNames st : nameList ) {

			//If match
			if( st.getName().equals( wantedStockName ) ) {

				//Create new series
				final TimeSeries series = new TimeSeries( st.getName() );

				//Get all of the stock's prices
				final List<StockPrices> priceList = jpaHelper.getPricesForStock( st );

				//Add all prices through time to the serie
				for( StockPrices sp : priceList ) {

					series.addOrUpdate( new Minute( sp.getTime() ), FinancialLongConverter.toDouble( sp.getLatest() ) );
				}

				//Insert serie to the view / model of the view
				view.insertSeries( series );
			}
		}
	}

	@Override
	public void display(Object model) {
		
		view.init();
	}

	@Override
	public void cleanup() {
		
		//Remove all subcontrollers
		for( GraphStockTogglerController subCon : subControllers ) {
			
			subCon.unregisterListener( this ); //Remove listener to this object
			subCon.cleanup();
			subControllers.remove( subCon );
		}
		
		//Null stuff to clear from GC
		subControllers = null;
		view = null;
	}
	
	@Override
	public Map<String, EventListener> getActionListeners() { //NOPMD
		
		return null;
	}
		
	/**
	 * Get Class Name
	 */
	@Override
	public String getName() {
		
		return CLASS_NAME;
	}

	/**
	 * Ask the model to get all stock names recorded, then create a GraphStockToggler for each of them
	 */
	@Override
	public void defineSubControllers() {
		
		IJPAHelper jpaHelper = JPAHelper.getInstance();
		List<StockNames> nameList = jpaHelper.getAllStockNames();
		
		//Store all stock names to a String array
		for( int i = 0; i < nameList.size(); i ++ ) {
			
			GraphStockTogglerController cont = new GraphStockTogglerController( nameList.get( i ).getName() );
			cont.init();
			subControllers.add(cont);
			cont.registerListener( this );
			
			view.addViewToScroller( (GraphStockTogglerView) cont.getView() );
		}
	}
}