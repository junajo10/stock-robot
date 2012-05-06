package view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.List;

import utils.global.Pair;

/**
 * Interface all views should implement.
 * @author Daniel
 */
public interface IView {
	/**
	 * This method should be called from the controller.
	 * 
	 * @param model The model this view should be associated with, could also be null if a fixed model is set.
	 */
	public void display(Object model);
	
	/**
	 * Should clean this view from listeners etc.
	 */
	public void cleanup();
	
	/**
	 * Adds actions to this view, the left side of an action is a string to help sort out where it should be.
	 * 
	 * @param actions a list of ActionListeners for this view
	 */
	public void addActions(List<Pair<String, ActionListener>> actions);
	
	/**
	 * Adds a listener to this view, the controller should listen to it for windowclose message etc 
	 * @param listener
	 */
    public void addPropertyChangeListener(PropertyChangeListener listener);
    
    /**
     * Removes a listener.
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener);
}
