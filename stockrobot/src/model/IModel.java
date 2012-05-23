package model;

import java.beans.PropertyChangeListener;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: IModel.java
 * Description:
 * IModel Is an interface used in the mvc model.
 * This interface is compatible with PropertyChangeListener.
 */
public interface IModel {

	/**
	 * Adds a listener to the model
	 * 
	 * @param listener
	 */
	public void addObserver(PropertyChangeListener listener);
	
	/**
	 * Removes a listener from the model
	 * 
	 * @param listener
	 */
	public void removeObserver(PropertyChangeListener listener);
}
