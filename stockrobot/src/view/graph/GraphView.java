package view.graph;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.StockNames;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import controller.gui.GraphController;

import view.IView;

/**
 * A simple view in which users can add as many companies stock price's as they like to.
 * 
 * @author kristian
 *
 */
public class GraphView extends JFrame implements IView {

	private static final long serialVersionUID = -7937601249697689239L;
	
	private JButton addSomething;
	private XYDataset dataset;
	private JComboBox dropDown;
	private JPanel panel;
	
    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public GraphView( final String title ) {

        super( title );
        
        panel = new JPanel();
        panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ) );
        add( panel );
        
        dataset = createDataset( "First" );
        final JFreeChart chart = createChart( dataset );
        final ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 800, 600 ) );
        panel.add(chartPanel);
        
        //Create  a drop down that will store all company names
        constructDropDown();
        
        addSomething = new JButton( "Show stock on chart" );
        panel.add( addSomething );
    }
    
    /**
     * Construct constructDropDown  
     */
    private void constructDropDown() {
    	
    	//Request all stock names from the database.
    	//They have been loaded not long ago and will be cached in JPA, so it's quick to ask for them here
    	
    	IJPAHelper jpaHelper = JPAHelper.getInstance();
		List<StockNames> nameList = jpaHelper.getAllStockNames();
		
		//Store all stock names to a String array 
		String[] names = new String[nameList.size()];
		for( int i = 0; i < nameList.size(); i ++ ) {
			
			names[ i ] = nameList.get( i ).getName();
		}
		
		//Instantiate drop down
    	dropDown = new JComboBox( names );
    	panel.add( dropDown );
    }
    
    /**
     * Called from outside to set this visible
     */
    public void init() {
    	
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
     * Getter for the current user entered company name
     * 
     * @return stock name
     */
    public String getCurrentWantedStock() {
    	
    	return dropDown.getSelectedItem().toString();
    }

	@Override
	public void display(Object model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {
		
		addSomething.addActionListener((ActionListener) actions.get(GraphController.BIND_GRAPH_VIEW));
	}
}