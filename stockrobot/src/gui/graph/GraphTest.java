package gui.graph;

import javax.swing.JPanel;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * Example of an simple JFreeChart "cake"-chart.
 * 
 * For learning JFreeChart.
 * 
 * @author Erik
 *
 */
public class GraphTest extends JFrame {

	private static final long serialVersionUID = 1L;

	public GraphTest(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        // This will create the dataset 
        PieDataset dataset = createDataset();
        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, chartTitle);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);

    }
    
    /**
     * Creates a sample dataset 
     */
    private  PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Ericsson A", 45);
        result.setValue("Volvo B", 54);
        result.setValue("Boliden", 25);
        result.setValue("H&M B", 62);
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
    
	 public static void main(String[] args) {
	        GraphTest demo = new GraphTest("Statistics", "Stock data");
	        demo.pack();
	        demo.setVisible(true);
	    }
}



