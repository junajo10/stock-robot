package controller;

import java.beans.PropertyChangeListener;
import java.util.EventListener;
import java.util.Map;

/**
 * Interface for controllers, this should help with our circular dependencies.
 * 
 * All controllers will hold a view that extends IView.
 * Should also hold a reference to the parent controller to re-enable buttons etc.
 * @author Daniel
 */
public interface IController extends PropertyChangeListener{
	/**
	 * Sets up and displays the view associated with this controller.
	 * Should also start to listen to the view for atleast WindowClose message.
	 * 
	 * @param model The model for this controller, could also be null.
	 */
	void display(Object model);
	
	/**
	 * Should be called when the view closes.
	 * Should remove all listeners.
	 * Should also cascade to subControllers.
	 * Should also tell parent controller that this window is closed
	 */
	void cleanup();
	
	/**
	 * Returns the list of all actions this controller has.
	 * @return A list of pairs where the left side is a string associated with the action
	 */
	Map<String, EventListener> getActionListeners();
	
	/**
	 * Lets the user define whatever subControllers are needed in this class.
	 */
	@Deprecated
	void defineSubControllers();
	
	/**
	 * The unique name for this controller, this can be used to sort out which subController should start.
	 * @return
	 */
	@Deprecated
	String getName();
}
