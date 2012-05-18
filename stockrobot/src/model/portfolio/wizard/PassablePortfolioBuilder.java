package model.portfolio.wizard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import utils.observer.IObservable;


/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: PassablePortgolioBuilder.java
 * Description:
 * PassablePortgolioBuilder used by portfolio wizard to incrementally build up all elements needed
 * to create a new portfolio.
 */
public class PassablePortfolioBuilder implements IObservable {

	private final PropertyChangeSupport observers;
	
	public PassablePortfolioBuilder(){
		
		observers = new PropertyChangeSupport(this);
	}
	
	@Override
	public void addAddObserver(final PropertyChangeListener listener) {
		
		observers.addPropertyChangeListener(listener);
	}

	@Override
	public void removeObserver(final PropertyChangeListener listener) {

		observers.removePropertyChangeListener(listener);
	}

	
}
