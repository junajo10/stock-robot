package gui;

import java.beans.PropertyChangeListener;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: IObservable.java
 * Description:
 * IObservable Is an interface used in the mvc model.
 * This interface is compatible with PropertyChangeListener.
 */
public interface IObservable {

	/**
	 * Adds a listener to the model
	 * 
	 * @param listener
	 */
	public void addAddObserver(PropertyChangeListener listener);
	
	/**
	 * Removes a listener from the model
	 * 
	 * @param listener
	 */
	public void removeObserver(PropertyChangeListener listener);
}
