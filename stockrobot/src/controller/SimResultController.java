package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import model.simulation.SimModel;
import model.simulation.SimulationHandler;

import utils.global.FinancialLongConverter;
import view.SimResultView;

/**
 * Controller for simulation result
 * @author Daniel
 */
public class SimResultController implements IController {
	
	private static final String CLASS_NAME = "SimResultController";
	
	private SimResultView view;
	private SimulationHandler model;
	PortfolioHistoryController history = new PortfolioHistoryController();
	
	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (evt.getPropertyName().contentEquals(SimulationHandler.RESULTCLOSE)) {
			cleanup();
		}
		else if (evt.getPropertyName().contains(SimulationHandler.NEWPIEDATA)){
			view.setPieView((Map<String, Long>)evt.getNewValue());
		}
		else if (evt.getPropertyName().contains(SimulationHandler.PORTFOLIOWORTH)){
			long oldValue = (Long) evt.getOldValue();
			long newValue = (Long) evt.getNewValue();
			double diff = (double)newValue/(double)oldValue;
			diff *= 100;
			view.setWorth(diff);
		}
		else if (evt.getPropertyName().contains(SimulationHandler.PROGRESSUPDATE)){
			view.setProgress((Integer)evt.getNewValue());
		}
		else if (evt.getPropertyName().contains(SimulationHandler.WORTHUPDATE)){
			view.setCurrentWorth((Long)evt.getNewValue());
		}
		else if (evt.getPropertyName().contains(SimulationHandler.BALANCEUPDATE)){
			view.setCurrentBalance((Long)evt.getNewValue());
		}
		
		
	}

	ActionListener historyListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			history.display(model.getPortfolioEntity());
		}
	};
	@Override
	public void display(final Object simModel) {
		final SimModel oldModel = (SimModel) simModel;
		
		if (model == null) {
			model = new SimulationHandler();
		}
		model.setAlgorithm(oldModel.getAlgorithm());
		model.setStocksBack(oldModel.getStocksBack());
		model.setLongSettings(oldModel.getLongSettings());
		model.setDoubleSettings(oldModel.getDoubleSettings());
		
		this.view = new SimResultView();
		
		view.setStartBalance(model.getInitialValue());
		
		view.addActions(getActionListeners());
		
		view.display(this.model);
		
		view.addPropertyChangeListener(this);
		
		model.addPropertyChangeListener(this);
		
		model.startSimulation();
	}

	@Override
	public void cleanup() {
		view.cleanup();
		this.model = null;
	}

	@Override
	public Map<String, EventListener> getActionListeners() { //NOPMD
		Map<String, EventListener> actions = new HashMap<String, EventListener>();
		actions.put(SimResultView.HISTORYBUTTON, historyListener);
		return actions;
	}

	@Override
	public String getName() {
		return CLASS_NAME;
	}

	@Override
	public void defineSubControllers() {} //NOPMD
}