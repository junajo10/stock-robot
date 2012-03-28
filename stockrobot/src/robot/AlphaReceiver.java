package robot;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import gui.IObservable;
import gui.mvc.Constants;

public class AlphaReceiver implements IObservable {

	private final PropertyChangeSupport propertyChangeSuport = new PropertyChangeSupport(this);
	private Thread callEvery60Sec;
	
	public AlphaReceiver() {
		
		System.out.println( "ALpha receiver!" );
		
		callEvery60Sec = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				//No need to give any values, just notify
				while(true) {
					
					try {
						
						System.out.println( "ALpha receiver RUN!" );
						propertyChangeSuport.firePropertyChange( Constants.EVENT_TYPE.PRICES_UPDATED, null, null );
						Thread.sleep(20000);
					
					} catch( Exception e ) {
						
						e.printStackTrace();
					}
				}
			}
		});
		
		callEvery60Sec.start();
		
		System.out.println( "</ALPHA!!!!!!" );
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