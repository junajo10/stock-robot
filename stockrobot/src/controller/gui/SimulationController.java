package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import utils.global.Log;
import utils.global.Log.TAG;
import view.SimulationView;

import model.simulation.SimulationHandler;

/**
 * 
 * @author Daniel
 */
public class SimulationController {
	SimulationHandler handler = new SimulationHandler();
	SimulationView view;
	
	public SimulationController(final SimulationView view) {
		view.addGoListener(goButton());
		
		this.view = view;
		
		handler.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().contentEquals("Progress")) {
					view.setProgress((Integer)evt.getNewValue());
				}
			}
		});
	}
	
	public ActionListener goButton() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Runnable apa = new Runnable() {
					
					@Override
					public void run() {
						handler.clearTestDatabase();
						String algorithm = view.getSelectedAlgorithm();
						double diff = handler.simulateAlgorithm(algorithm, 300, null, null);
						
						diff -= 100;
						if (diff > 0)
							JOptionPane.showMessageDialog(null, "Simulation done, increase balance: " + diff + "%");
						else
							JOptionPane.showMessageDialog(null, "Simulation done, decrease balance: " + diff + "%");
						
						handler.clearTestDatabase();
					}
				};
				
				Thread t = new Thread(apa);
				t.start();
			}
		};
	}
}
