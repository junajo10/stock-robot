package model.simulation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import utils.global.FinancialLongConverter;
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
		else if (evt.getPropertyName().contentEquals("Portfolio Worth")) {
			long startingBalance = Long.valueOf("10000000000000");
			long currentWorth = (Long)evt.getNewValue();
			
			double change = (double)currentWorth/(double)startingBalance;
			view.setWorth(change);
		}
		else if (evt.getPropertyName().contentEquals("newPieData")) {
			view.setPieView((Map<String, Long>)evt.getNewValue());
		}
	}
	
	
    private PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        
        for (Entry<String, Long> e : simulationHandler.getLatestPieData().entrySet()) {
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
