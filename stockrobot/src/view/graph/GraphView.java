package view.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.sql.Date;
import java.util.EventListener;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import view.IView;

import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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
	private DateAxis dateAxis;
	private JPanel pnlRange;
	private JSlider sldRange;
	private JPanel pnlTimeRange;
	private JRadioButton rdbtnRangeYears;
	private JRadioButton rdbtnRangeMonths;
	private JRadioButton rdbtnRangeDays;
	private JRadioButton rdbtnRangeHours;
	private JTextField txtRangeValue;
	private ButtonGroup rangeSelectGroup;
	
	public final String rangeYearSelectListener 	= "rngYearSelectListener";
	public final String rangeMonthSelectListener 	= "rngMonthrSelectListener";
	public final String rangeDaySelectListener 	= "rngDaySelectListener";
	public final String rangeHourSelectListener 	= "rnghourSelectListener";
	public final String rangeValueListener  = "rngValueListener";
	public final String rangeSliderListener = "rngSliderListener";

	
	
    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public GraphView( final String title ) {

        super( title );
        setBackground(Color.WHITE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
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
        scrollPanel.setViewportView(scrollPanelContainer);
        
        pnlRange = new JPanel();
        getContentPane().add(pnlRange);
        pnlRange.setLayout(new BoxLayout(pnlRange, BoxLayout.Y_AXIS));
        
        sldRange = new JSlider();
        sldRange.setValue(100);
        pnlRange.add(sldRange);
        
        pnlTimeRange = new JPanel();
        pnlRange.add(pnlTimeRange);
        
        rdbtnRangeYears = new JRadioButton("Years");
        pnlTimeRange.add(rdbtnRangeYears);
        
        rdbtnRangeMonths = new JRadioButton("Months");
        pnlTimeRange.add(rdbtnRangeMonths);
        
        rdbtnRangeDays = new JRadioButton("Days");
        pnlTimeRange.add(rdbtnRangeDays);
        
        rdbtnRangeHours = new JRadioButton("Hours");
        pnlTimeRange.add(rdbtnRangeHours);
        
        rangeSelectGroup = new ButtonGroup();
        rangeSelectGroup.add(rdbtnRangeYears);
        rangeSelectGroup.add(rdbtnRangeMonths);
        rangeSelectGroup.add(rdbtnRangeDays);
        rangeSelectGroup.add(rdbtnRangeHours);
        
        txtRangeValue = new JTextField();
        pnlTimeRange.add(txtRangeValue);
        txtRangeValue.setColumns(10);
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
        dateAxis = (DateAxis) plot.getDomainAxis();
        
        dateAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
        dateAxis.setVerticalTickLabels(true);
       
        plot.setRenderer( renderer );
                
        return chart;
    }

    public void setRange(Date dateLower, Date dateUpper){
    	
    	dateAxis.setAutoRange(false);
   
    	dateAxis.setRange(dateLower,dateUpper);
    }
    
    public void autoRange(){
    	dateAxis.setAutoRange(true);
    }
    
	@Override
	public void display(Object model) {} //NOPMD

	@Override
	public void cleanup() {} //NOPMD

	@Override
	public void addActions(Map<String, EventListener> actions) {
		
		if(actions.get(rangeYearSelectListener) instanceof ItemListener){
			rdbtnRangeYears.addItemListener((ItemListener)actions.get(rangeYearSelectListener));
		}
		if(actions.get(rangeMonthSelectListener) instanceof ItemListener){
			rdbtnRangeMonths.addItemListener((ItemListener)actions.get(rangeMonthSelectListener));
		}
		if(actions.get(rangeDaySelectListener) instanceof ItemListener){
			rdbtnRangeDays.addItemListener((ItemListener)actions.get(rangeDaySelectListener));
		}
		if(actions.get(rangeHourSelectListener) instanceof ItemListener){
			rdbtnRangeHours.addItemListener((ItemListener)actions.get(rangeHourSelectListener));
		}
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {} //NOPMD
}