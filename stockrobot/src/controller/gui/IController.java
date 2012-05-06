package controller.gui;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.List;

import utils.global.Pair;


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
	public void display(Object model);
	
	/**
	 * Should be called when the view closes.
	 * Should remove all listeners.
	 * Should also cascade to subControllers.
	 * Should also tell parent controller that this window is closed
	 */
	public void cleanup();
	
	/**
	 * Returns the list of all actions this controller has.
	 * @return A list of pairs where the left side is a string associated with the action
	 */
	public List<Pair<String, ActionListener>> getActionListeners();
	
	/**
	 * Adds subControllers that can be started from this controller.
	 * @param subController
	 */
	public void addSubController(IController subController);
	
	/**
	 * The unique name for this controller, this can be used to sort out which subController should start.
	 * @return
	 */
	public String getName();
}
