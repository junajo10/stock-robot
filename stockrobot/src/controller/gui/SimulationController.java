package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.SimulationView;

import model.simulation.SimulationHandler;

/**
 * 
 * @author Daniel
 */
public class SimulationController {
	SimulationHandler handler = new SimulationHandler();
	SimulationView view;
	
	public SimulationController(SimulationView view) {
		view.addGoListener(goButton());
		
		this.view = view;
	}
	
	public ActionListener goButton() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				handler.clearTestDatabase();
				String algorithm = view.getSelectedAlgorithm();
				double apa = handler.simulateAlgorithm(algorithm, 30, null, null);
				
				JOptionPane.showMessageDialog(null, "" + apa);
			}
		};
	}
}
