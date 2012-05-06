package model.simulation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import view.SimResultView;

public class SimResultModel extends SimModel implements PropertyChangeListener{
	SimulationHandler simulationHandler = new SimulationHandler();
	SimResultView view;
	
	public void startSimulation(SimResultView view) {
		this.view = view;
		System.out.println("Startar simulering på algorithm: " + getAlgorithm() + " " + getStocksBack());
		
		simulationHandler.addPropertyChangeListener(this);
		
		Thread simulationThread = new Thread(new Runnable() {
			@Override
			public void run() {
				simulationHandler.simulateAlgorithm(getAlgorithm(), getStocksBack(), null, null);
				
				System.out.println("Klar med simulering på algorithm: " + getAlgorithm());
				
			}
		});
		simulationThread.start();
	}
	
	public void cleanup() {
		simulationHandler.removePropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().contentEquals("Progress")) {
			view.setProgress((Integer)evt.getNewValue());
		}
	}
}
