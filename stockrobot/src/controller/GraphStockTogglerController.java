package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import view.IView;
import view.graph.GraphStockTogglerView;

/**
 * Controller for the toggler class
 * 
 * @author kristian
 *
 */
public class GraphStockTogglerController implements IController {
	
	public static final String CHECKBOX_PROPERTY = "checkBoxProperty";
	public static final String CHECKED = "checked";
	public static final String UNCHECKED = "unchecked";
	private static final String CLASS_NAME = "GraphStockTogglerController";
	
	private Map<String, EventListener> events;
	private final String stockName;
	private IView view;
	private PropertyChangeSupport dispatcher;
	
	ActionListener checkboxChanged = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			boolean isChecked = ((GraphStockTogglerView) view).isSelected();
			
			if( isChecked ) {
				
				dispatcher.firePropertyChange( CHECKBOX_PROPERTY, UNCHECKED, CHECKED );
			
			} else {
				
				dispatcher.firePropertyChange( CHECKBOX_PROPERTY, CHECKED, UNCHECKED );
			}
		}
	};
	
	public GraphStockTogglerController( final String stockName ) {
		
		this.stockName = stockName;
	}
	
	/**
	 * Instantiate stuff that is not supposed to be created in the constructor (according to PMD)
	 */
	public void init() {
		
		dispatcher = new PropertyChangeSupport( this );
		
		events = new HashMap<String, EventListener>();
		events.put( GraphStockTogglerView.CHECKBOX_CHANGED , checkboxChanged );
		
		view = new GraphStockTogglerView( stockName );
		( ( GraphStockTogglerView) view ).init();
		view.addActions( events );
	}
	
	/**
	 * Register a listener to listen for checkbox toggling
	 * 
	 * @param controller
	 */
	public void registerListener( final IController controller ) {
		
		dispatcher.addPropertyChangeListener( controller );
	}
	
	/**
	 * Unregister a listener to listen for checkbox toggling
	 * 
	 * @param controller
	 */
	public void unregisterListener( final IController controller ) {
		
		dispatcher.removePropertyChangeListener( controller );
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {} //NOPMD

	@Override
	public void display( final Object model ) {
		
		view.display(new Object());
	}

	/**
	 * Clear all resources and cascade downwards!
	 */
	@Override
	public void cleanup() {
		
		//Remove all events
		for( String str : events.keySet() ) {
			
			events.remove( str );
		}
		
		view.cleanup();
	}
	
	public IView getView() {
		
		return view;
	}
	
	public String getAssociatedStockName() {
		
		return stockName;
	}
}