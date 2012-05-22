package model.portfolio;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import model.IModel;



/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: PortfolioSetup.java
 * Description:
 * PortfolioSetup is used to create new portfolios.
 */
public class PortfolioSetup implements IModel {

	private final PropertyChangeSupport observers = new PropertyChangeSupport(this);
	
	@Override
	public void addAddObserver(PropertyChangeListener listener) {
		
		observers.addPropertyChangeListener( listener );
	}

	@Override
	public void removeObserver(PropertyChangeListener listener) {
		
		observers.removePropertyChangeListener( listener );
	}

}
