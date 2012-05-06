package controller.gui;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import model.simulation.SimModel;
import model.simulation.SimResultModel;

import utils.global.Pair;
import view.SimResultView;

public class SimResultController implements IController {
	SimResultView view;
	SimResultModel model;
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(evt.getPropertyName());
		if (evt.getPropertyName().contentEquals("Sim Result Close")) {
			cleanup();
		}
	}

	@Override
	public void display(Object model) {
		SimModel oldModel = (SimModel) model;
		
		if (this.model == null) {
			this.model = new SimResultModel();
		}
		this.model.setAlgorithm(oldModel.getAlgorithm());
		this.model.setStocksBack(oldModel.getStocksBack());
		
		
		this.view = new SimResultView();
		view.display(this.model);
		
		view.addPropertyChangeListener(this);
		
		this.model.startSimulation(view);
	}

	@Override
	public void cleanup() {
		System.out.println("SimResultController cleanup");
		view.cleanup();
		this.model = null;
	}

	@Override
	public List<Pair<String, ActionListener>> getActionListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSubController(IController subController) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "SimulationController";
	}

}
