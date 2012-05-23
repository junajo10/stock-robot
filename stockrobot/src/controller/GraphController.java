package controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
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

import utils.global.FinancialLongConverter;
import utils.global.Log;
import view.graph.GraphStockTogglerView;
import view.graph.GraphView;

/**
 * Controller for the simple graph view
 * 
 * 
 * 
 * @author kristian
 *
 */
public class GraphController implements IController, PropertyChangeListener {
	
	public static final String BIND_GRAPH_VIEW = "bindGraphView";
	public static final String WINDOW_TITLE = "Stock prices on a graph";
	
	private static final int YEAR_RANGE = 0;
	private static final int MONTH_RANGE = 1;
	private static final int DAY_RANGE = 2;
	private static final int HOUR_RANGE = 3;
	
	private int selectedRange = 2;
	private int sliderYearValue = 0;
	private int sliderMonthValue = 0;
	private int sliderDayValue = 0;
	
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
	
		final IJPAHelper jpaHelper = JPAHelper.getInstance();
		
		DateTime upperDate = new DateTime(jpaHelper.getLastStockPrice().getTime());
		
		
		upperDate = upperDate.minusYears(sliderYearValue);
		upperDate = upperDate.minusMonths(sliderMonthValue);
		upperDate = upperDate.minusDays(sliderDayValue);
		
		DateTime lowerDate = upperDate;
		
		switch (selectedRange) {
		case YEAR_RANGE:
			lowerDate = upperDate.minusYears(selectedValue);
			break;
		case MONTH_RANGE:
			lowerDate = upperDate.minusMonths(selectedValue);
			break;
		case DAY_RANGE :
			lowerDate = upperDate.minusDays(selectedValue);
			break;
		case HOUR_RANGE:
			lowerDate = upperDate.minusHours(selectedValue);
			break;
		default:
			break;
		}
		
		
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
	
	private class RangeYearSliderListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			
			JSlider slider = view.getYearSlider();
			
			sliderYearValue = Math.abs(slider.getValue());
			updateRange();
		}
	}
	
	private class RangeMonthSliderListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			
			JSlider slider = view.getMonthSlider();
			
			sliderMonthValue = Math.abs(slider.getValue());
			updateRange();
		}
	}
	
	private class RangeDaySliderListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			
			JSlider slider = view.getDaySlider();
			
			sliderDayValue = Math.abs(slider.getValue());
			updateRange();
		}
	}
	
	public class RangeValueLitesner implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {} //NOPMD

		@Override
		public void keyPressed(KeyEvent e) {} //NOPMD

		@Override
		public void keyReleased(KeyEvent e) {
			try {
				selectedValue = Integer.valueOf(view.getRangeValue());
			}catch (Exception err) {
				
			}
			finally{
				updateRange();
			}
			
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
	
	public Map<String, EventListener> getActionListeners() { //NOPMD
		
		Map<String, EventListener> listeners = new HashMap<String,EventListener>();
		listeners.put(view.rangeYearSelectListener, new YearRangeListener());
		listeners.put(view.rangeMonthSelectListener, new MonthRangeListener());
		listeners.put(view.rangeDaySelectListener, new DayRangeListener());
		listeners.put(view.rangeYearSliderListener, new RangeYearSliderListener());
		listeners.put(view.rangeMonthSliderListener, new RangeMonthSliderListener());
		listeners.put(view.rangeDaySliderListener, new RangeDaySliderListener());
		listeners.put(view.rangeValueListener, new RangeValueLitesner());
		
		return listeners;
	}

	/**
	 * Ask the model to get all stock names recorded, then create a GraphStockToggler for each of them
	 */
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