package model.robot;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import utils.observer.IObservable;

public class AlphaReceiver implements IObservable {

	private final PropertyChangeSupport propertyChangeSuport = new PropertyChangeSupport(this);
	private Thread callEvery60Sec;
	

	//Socket connections
	public static final String PRICES_UPDATED 		= "pricesUpdated";
	
	public AlphaReceiver() {
		
		callEvery60Sec = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				//No need to give any values, just notify
				while(true) {
					
					try {
					
						propertyChangeSuport.firePropertyChange( PRICES_UPDATED, null, null );
						Thread.sleep(20000);
					
					} catch( Exception e ) {
						
						e.printStackTrace();
					}
				}
			}
		});
		
		callEvery60Sec.start();
	}
	
	@Override
	public void addAddObserver(PropertyChangeListener listener) {
		
		propertyChangeSuport.addPropertyChangeListener( listener );
	}

	@Override
	public void removeObserver(PropertyChangeListener listener) {
		
		propertyChangeSuport.removePropertyChangeListener( listener );
	}
	
	public void startRunner() {
		
		callEvery60Sec.run();
	}
}