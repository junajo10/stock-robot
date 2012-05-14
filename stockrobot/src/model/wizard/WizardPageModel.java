package model.wizard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;


import utils.observer.IObservable;


public abstract class WizardPageModel implements IObservable {

	protected WizardModel wizardModel;
	protected PropertyChangeSupport observers;
	protected Map<Integer,Boolean> properties = new HashMap<Integer,Boolean>(); //Returns a map with flags if property is set or not

	public WizardPageModel(WizardModel wizardModel) {
	
		this.wizardModel = wizardModel;
		observers = new PropertyChangeSupport(this);
	}
		
	public void addAddObserver(PropertyChangeListener listener){
		observers.addPropertyChangeListener(listener);
	}
	
	public void removeObserver(PropertyChangeListener listener){
		observers.removePropertyChangeListener(listener);
	}
	
	public Map<Integer,Boolean> getProperties(){
		
		return properties;
	}
	
	public void removeProperty(int property){
		
		properties.remove(property);
	}
	
	public abstract boolean canFinish();
	
	public abstract void finish();
	
	public PropertyChangeSupport getObservers() { return observers; }
}
