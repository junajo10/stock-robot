package model.wizard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import utils.observer.IObservable;


public abstract class WizardPageModel implements IObservable {

	protected WizardModel wizardModel;
	protected PropertyChangeSupport observers;

	public WizardPageModel(WizardModel wizardModel) {
	
		this.wizardModel = wizardModel;
		observers = new PropertyChangeSupport(this);
	}
	
	public abstract WizardPageModel copyModel();
	
	public void addAddObserver(PropertyChangeListener listener){
		observers.addPropertyChangeListener(listener);
	}
	
	public void removeObserver(PropertyChangeListener listener){
		observers.removePropertyChangeListener(listener);
	}
}
