package view.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import view.IView;
import javax.swing.JScrollPane;

/**
 * A simple view in which users can add as many companies stock price's as they like to.
 * 
 * This class also works as a wrapper for the JFreeChart graph
 * 
 * @author kristian
 *
 */
public class GraphView extends JFrame implements IView {

	private static final long serialVersionUID = -7937601249697689239L;
	
	private static final int WINDOW_WIDTH = 1060;
	private static final int WINDOW_HEIGHT = 700;
	
	private XYDataset dataset;
	private JPanel panel;
	private JScrollPane scrollPanel;
	private JPanel scrollPanelContainer;
	
    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public GraphView( final String title ) {

        super( title );
        setBackground(Color.WHITE);
        
        panel = new JPanel();
        getContentPane().add( panel );
        
        dataset = createDataset( "First" );
        final JFreeChart chart = createChart( dataset );
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        final ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new Dimension( 800, 600 ) );
        
        panel.add(chartPanel);
        
        scrollPanel = new JScrollPane();
        panel.add( scrollPanel );
        
        scrollPanelContainer = new JPanel();
        BoxLayout scrollPanelContainerLayout = new BoxLayout( scrollPanelContainer, BoxLayout.Y_AXIS );
        scrollPanelContainer.setLayout( scrollPanelContainerLayout );
        scrollPanel.getViewport().add( scrollPanelContainer );
    }
    
    public void addViewToScroller( JPanel view ) {
    	
    	scrollPanelContainer.add( view );
    }
    
    /**
     * Called from outside to set this visible
     */
    public void init() {
    	
    	pack();
    	setMaximumSize( new Dimension( WINDOW_WIDTH, WINDOW_HEIGHT ) );
        setSize( new Dimension( WINDOW_WIDTH, WINDOW_HEIGHT ) );
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
    
    public void removeSeries( int index ) {
    	
    	((TimeSeriesCollection) dataset).removeSeries( index );
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

        //Color the chart!
        chart.setBackgroundPaint( Color.black );

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint( Color.black );
        plot.setDomainGridlinePaint( Color.white );
        plot.setRangeGridlinePaint( Color.white );
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true,false);
        //TODO Change this to use a method that isnt depricated
        renderer.setStroke(
                new BasicStroke(
                    2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND
                )
            );
        
        
        plot.setRenderer( renderer );
        
        
                
        return chart;
    }

	@Override
	public void display(Object model) {} //NOPMD

	@Override
	public void cleanup() {} //NOPMD

	@Override
	public void addActions(Map<String, EventListener> actions) {} //NOPMD

	@Override
	public void propertyChange(PropertyChangeEvent evt) {} //NOPMD
}