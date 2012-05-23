package controller;

/**
 * Interface for controllers, this should help with our circular dependencies.
 * 
 * All controllers will hold a view that extends IView.
 * Should also hold a reference to the parent controller to re-enable buttons etc.
 * @author Daniel
 */
public interface IController {
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
	
}
