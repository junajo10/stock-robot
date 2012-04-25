package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import utils.global.FinancialLongConverter;
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
						
						PieDataset dataset = createDataset();
						JFreeChart chart = createChart(dataset, "Stock distribution");
						
						ChartPanel chartPanel = new ChartPanel(chart);
				        
				        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
				        
				        JFrame apa = new JFrame("Stock distribution");
				        apa.add(chartPanel);
				        apa.setVisible(true);
				        apa.pack();
						
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
    private PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        
        for (Entry<String, Long> e : handler.getLatestPieData().entrySet()) {
        	result.setValue(e.getKey(), FinancialLongConverter.toDouble(e.getValue()));
        }
        return result;
    }
    /**
     * Creates a chart
     */
    private JFreeChart createChart(PieDataset dataset, String title) {
        
        JFreeChart chart = ChartFactory.createPieChart3D(
            title,  				// chart title
            dataset,                // data
            true,                   // include legend
            true,
            false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
        
    }
}
