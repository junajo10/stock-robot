package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;

import utils.global.FinancialLongConverter;
import utils.global.Log;
import utils.global.Pair;
import view.graph.GraphView;

/**
 * Controller for the simple graph view
 * 
 * @author kristian
 *
 */
public class GraphController implements IController {
	
	private static final String CLASS_NAME = "GraphController";
	private static final String BIND_GRAPH_VIEW = "bindGraphView";
	
	private static final String WINDOW_TITLE = "Stock prices on a graph";
	
	
	
	//Keep a reference of the view to be able to insert and read stuff from it
	private GraphView _view;
	private List<Pair<String, ActionListener>> retList;
	
	public GraphView getView() { return _view; }

	public GraphController() {
		
		retList = new ArrayList<Pair<String,ActionListener>>();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
		Log.instance().log(Log.TAG.VERY_VERBOSE, "GraphView: Property Change");
	}

	@Override
	public void display(Object model) {
		
		_view = new GraphView( WINDOW_TITLE );
		_view.bindAddStockButton( getActionListeners().get(0).getRight() );
		
		_view.init();
	}

	@Override
	public void cleanup() {
		
		_view = null;
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
	
	
	@Override
	public List<Pair<String, ActionListener>> getActionListeners() {
		
		
		
		retList.add( new Pair<String, ActionListener>( BIND_GRAPH_VIEW, bindGraphView() ) );
		
		return retList;
	}
	
	@Override
	public void addSubController(IController subController) {
		
		//For now, do nothing here since this view does not have sub views
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