package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.joda.time.DateTime;
import org.joda.time.JodaTimePermission;

import utils.global.FinancialLongConverter;
import utils.global.Log;
import view.graph.GraphStockTogglerView;
import view.graph.GraphView;
import view.wizard.portfolio.PortfolioStartPage;

/**
 * Controller for the simple graph view
 * 
 * 
 * 
 * @author kristian
 *
 */
public class GraphController implements IController {
	
	public static final String CLASS_NAME = "GraphController";
	public static final String BIND_GRAPH_VIEW = "bindGraphView";
	public static final String WINDOW_TITLE = "Stock prices on a graph";
	
	public static final int YEAR_RANGE = 0;
	public static final int MONTH_RANGE = 1;
	public static final int DAY_RANGE = 2;
	public static final int HOUR_RANGE = 3;
	
	private int selectedRange = 2;
	private int sliderValue = 100;
	
	private int selectedValue = 1;
	
	//Keep a reference of the view to be able to insert and read stuff from it
	private GraphView view;
	
	private List<GraphStockTogglerController> subControllers;
	
	public GraphView getView() { return view; }
	
	private List<String> timeSeriesList; //List in which the stocks added to the graph get their names registered, so it's easy to know which to remove when they get called off the stage
	
	public void init() {
		
		timeSeriesList = new ArrayList<String>();
		
		view = new GraphView( WINDOW_TITLE );
		
		subControllers = new ArrayList<GraphStockTogglerController>();
		
		
		view.addActions(getActionListeners());

		
		defineSubControllers();
	}
	
	private void updateRange(){
	
		DateTime upperDate = new DateTime();
		upperDate = upperDate.minusDays(sliderValue);
		DateTime lowerDate = upperDate;
		
		switch (selectedRange) {
		case YEAR_RANGE:
			lowerDate = lowerDate.minusYears(selectedValue);
			break;
		case MONTH_RANGE:
			lowerDate = lowerDate.minusMonths(selectedValue);
			break;
		case DAY_RANGE:
			lowerDate = lowerDate.minusDays(selectedValue);
			break;
		case HOUR_RANGE:
			lowerDate = lowerDate.minusHours(selectedValue);
			break;
		default:
			break;
		}
		
		System.out.println("upper: "+ upperDate + " Lower: " + lowerDate);
		view.setRange(lowerDate.toDate(), upperDate.toDate());
	}
	
	private class YearRangeListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getStateChange() == ItemEvent.SELECTED) {
				selectedRange = YEAR_RANGE;
				Log.log(Log.TAG.NORMAL, "Year selected");
				updateRange();
			}
		}
	}
	
	private class MonthRangeListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getStateChange() == ItemEvent.SELECTED) {
				selectedRange = MONTH_RANGE;
				Log.log(Log.TAG.DEBUG, "Month selected");
				updateRange();
			}
		}
	}
	
	private class DayRangeListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getStateChange() == ItemEvent.SELECTED) {
				selectedRange = DAY_RANGE;
				Log.log(Log.TAG.DEBUG, "Day selected");
				updateRange();
			}
		}
	}
	
	private class HourRangeListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getStateChange() == ItemEvent.SELECTED) {
				selectedRange = HOUR_RANGE;
				Log.log(Log.TAG.DEBUG, "Hour selected");
				updateRange();
			}
		}
	}
	
	private class RangeSliderListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			
			JSlider slider = view.getSlider();
			
			sliderValue = slider.getMinimum()-slider.getValue();
			updateRange();
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
		//Fetch the associated stock name from the toggler interacted with
		String wantedStockName = ((GraphStockTogglerController) arg0.getSource() ).getAssociatedStockName();
		
		//If the box was checked, show stock in the graph
		if( arg0.getNewValue().equals( GraphStockTogglerController.CHECKED ) ) {
			
			showStock( wantedStockName );
		}
		
		if( arg0.getNewValue().equals( GraphStockTogglerController.UNCHECKED ) ) {
			
			hideStock( wantedStockName );
		}
	}
	

	
	private void showStock( final String stockName ) {
		
		//Sign up for the jpaHelper
		final IJPAHelper jpaHelper = JPAHelper.getInstance();
		final List<StockNames> nameList = jpaHelper.getAllStockNames();
		
		//Search through all stocknames to find the right one if it exists
		for( StockNames st : nameList ) {

			//If match
			if( st.getName().equals( stockName ) ) {

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
				
				timeSeriesList.add( stockName );
			}
		}
	}
	
	/**
	 * 
	 * @param stockName
	 */
	private void hideStock( final String stockName ) {
		
		view.removeSeries( timeSeriesList.indexOf( stockName ) );
		timeSeriesList.remove( stockName );
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
		
		Map<String, EventListener> listeners = new HashMap<String,EventListener>();
		listeners.put(view.rangeYearSelectListener, new YearRangeListener());
		listeners.put(view.rangeMonthSelectListener, new MonthRangeListener());
		listeners.put(view.rangeDaySelectListener, new DayRangeListener());
		listeners.put(view.rangeHourSelectListener, new HourRangeListener());
		listeners.put(view.rangeSliderListener, new RangeSliderListener());
		
		return listeners;
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