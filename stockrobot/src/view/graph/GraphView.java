package view.graph;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import viewfactory.ViewFactory;

/**
 * A simple view in which users can add as many companies stock price's as they like to.
 * 
 * @author kristian
 *
 */
public class GraphView extends JFrame {

	private static final long serialVersionUID = -7937601249697689239L;
	
	private JButton addSomething;
	private XYDataset dataset;
	private JTextField nameField;
	
    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public GraphView( final String title ) {

        super( title );
        
        JPanel panel = new JPanel();
        panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ) );
        add( panel );
        
        dataset = createDataset( "First" );
        final JFreeChart chart = createChart( dataset );
        final ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 800, 600 ) );
        panel.add(chartPanel);
        
        nameField = new JTextField();
        nameField.setSize(100, 20);
        panel.add( nameField );
        
        addSomething = new JButton( "Add something" );
        panel.add( addSomething );
    }
    
    /**
     * Called from outside to set this visible
     */
    public void init() {
    	
    	System.out.println( "INIT INIT INIT INIT!!!!" );
    	
    	pack();
    	setVisible( true );
    }
    
    /**
     * Creates a sample dataset.
     * 
     * @return a sample dataset.
     */
    private XYDataset createDataset( String name ) {

        final TimeSeriesCollection dataset = new TimeSeriesCollection();
                
        return dataset;
    }
    
    /**
     * Will be called from outside this view to fill the model with 
     * 
     * @param series
     */
    public void insertSeries( TimeSeries series ) {
    	
    	((TimeSeriesCollection) dataset).addSeries(series);
    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        
        //Create the chart...
        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
        		"Stock price over time.",
        		"time",
        		"price",
        		dataset,
        		true, //create legend?
        		false, //generate tooltips?
        		false //generate URLs?
        );

        //Pimp the chart!
        chart.setBackgroundPaint( Color.black );

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint( Color.black );
        plot.setDomainGridlinePaint( Color.white );
        plot.setRangeGridlinePaint( Color.white );
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true,false);
        plot.setRenderer( renderer );
        
        return chart;
    }
   
    /**
     * Controller window where it is able to register
     * 
     * @param listener
     */
    public void bindAddStockButton( ActionListener listener ) {
    	
    	addSomething.addActionListener( listener );
    }
    
    /**
     * Getter for the current user entered company name
     * 
     * @return stock name
     */
    public String getCurrentWantedStock() {
    	
    	return nameField.getText();
    }
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        ViewFactory.getGraphView();
    }
}