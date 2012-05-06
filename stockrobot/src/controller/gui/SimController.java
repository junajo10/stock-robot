package controller.gui;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import utils.global.Pair;
import view.SimView;

public class SimController implements IController {
	SimView view = new SimView();
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(Object model) {
		view.display(model);
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
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
		return "SimController";
	}

}
