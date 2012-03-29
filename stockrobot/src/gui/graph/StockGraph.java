package gui.graph;

import javax.swing.JPanel;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.util.Rotation;

/**
 * Example of an simple JFreeChart time-graph of an fictional stock.
 * 
 * For learning JFreeChart only.
 * 
 * @author Erik
 *
 */
public class StockGraph extends JFrame {

	private static final long serialVersionUID = 1L;

	public StockGraph() {
        super("Stock data.");

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
        		"Stock price of H&M and Boliden.", // title
        		"Date", // x-axis label
        		"Price Per Unit", // y-axis label
        		getTimeSerie(), // data
        		true, // create legend?
        		true, // generate tooltips?
        		false // generate URLs?
        );
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(700, 400));
        setContentPane(chartPanel);

    }
    
    private TimeSeriesCollection getTimeSerie(){
    	TimeSeries s1 = new TimeSeries("Boliden B", Month.class);
    	s1.add(new Month(2, 2011), 181.8);
    	s1.add(new Month(3, 2011), 167.3);
    	s1.add(new Month(4, 2011), 153.8);
    	s1.add(new Month(5, 2011), 167.6);
    	s1.add(new Month(6, 2011), 158.8);
    	s1.add(new Month(7, 2011), 148.3);
    	s1.add(new Month(8, 2011), 153.9);
    	s1.add(new Month(9, 2011), 142.7);
    	s1.add(new Month(10, 2011), 123.2);
    	s1.add(new Month(11, 2011), 131.8);
    	s1.add(new Month(12, 2011), 139.6);
    	s1.add(new Month(1, 2012), 142.9);
    	s1.add(new Month(2, 2012), 138.7);
    	s1.add(new Month(3, 2012), 137.3);
    	s1.add(new Month(4, 2012), 143.9);
    	s1.add(new Month(5, 2012), 139.8);
    	s1.add(new Month(6, 2012), 137.0);
    	s1.add(new Month(7, 2012), 132.8);
    	TimeSeries s2 = new TimeSeries("H&M B", Month.class);
    	s2.add(new Month(2, 2011), 129.6);
    	s2.add(new Month(3, 2011), 123.2);
    	s2.add(new Month(4, 2011), 117.2);
    	s2.add(new Month(5, 2011), 124.1);
    	s2.add(new Month(6, 2011), 122.6);
    	s2.add(new Month(7, 2011), 119.2);
    	s2.add(new Month(8, 2011), 116.5);
    	s2.add(new Month(9, 2011), 112.7);
    	s2.add(new Month(10, 2011), 101.5);
    	s2.add(new Month(11, 2011), 106.1);
    	s2.add(new Month(12, 2011), 110.3);
    	s2.add(new Month(1, 2012), 111.7);
    	s2.add(new Month(2, 2012), 111.0);
    	s2.add(new Month(3, 2012), 109.6);
    	s2.add(new Month(4, 2012), 113.2);
    	s2.add(new Month(5, 2012), 111.6);
    	s2.add(new Month(6, 2012), 108.8);
    	s2.add(new Month(7, 2012), 101.6);
    	TimeSeriesCollection dataset = new TimeSeriesCollection();
    	dataset.addSeries(s1);
    	dataset.addSeries(s2);
    	
    	return dataset;
    }

	 public static void main(String[] args) {
	        StockGraph stock = new StockGraph();
	        stock.pack();
	        stock.setVisible(true);
	    }
}



