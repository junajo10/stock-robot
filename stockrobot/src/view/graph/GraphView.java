package view.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.beans.PropertyChangeEvent;
import java.util.Date;
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
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;

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
	private JSlider sldYearRange;
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
	private final String rangeHourSelectListener 	= "rnghourSelectListener";
	public final String rangeValueListener  = "rngValueListener";
	public final String rangeYearSliderListener 	= "rngYearSliderListener";
	public final String rangeMonthSliderListener 	= "rngMonthSliderListener";
	public final String rangeDaySliderListener 	= "rngDaySliderListener";
	private JLabel lblYears;
	private JLabel labelMonths;
	private JSlider sldMonthRange;
	private JLabel lblDays;
	private JSlider sldDayRange;

	
	
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
        
        lblYears = new JLabel("Years");
        pnlRange.add(lblYears);
        
        sldYearRange = new JSlider();
        sldYearRange.setToolTipText("");
        sldYearRange.setMinorTickSpacing(1);
        sldYearRange.setSnapToTicks(true);
        sldYearRange.setMinimum(-20);
        sldYearRange.setPaintLabels(true);
        sldYearRange.setPaintTicks(true);
        sldYearRange.setMajorTickSpacing(2);
        sldYearRange.setMaximum(0);
        sldYearRange.setValue(20);
        pnlRange.add(sldYearRange);
        
        labelMonths = new JLabel("Months");
        pnlRange.add(labelMonths);
        
        sldMonthRange = new JSlider();
        sldMonthRange.setMinorTickSpacing(1);
        sldMonthRange.setSnapToTicks(true);
        sldMonthRange.setValue(20);
        sldMonthRange.setPaintTicks(true);
        sldMonthRange.setPaintLabels(true);
        sldMonthRange.setMinimum(-11);
        sldMonthRange.setMaximum(0);
        sldMonthRange.setMajorTickSpacing(1);
        pnlRange.add(sldMonthRange);
        
        lblDays = new JLabel("Days");
        pnlRange.add(lblDays);
        
        sldDayRange = new JSlider();
        sldDayRange.setMinorTickSpacing(1);
        sldDayRange.setSnapToTicks(true);
        sldDayRange.setValue(20);
        sldDayRange.setPaintTicks(true);
        sldDayRange.setPaintLabels(true);
        sldDayRange.setMinimum(-30);
        sldDayRange.setMaximum(0);
        sldDayRange.setMajorTickSpacing(2);
        pnlRange.add(sldDayRange);
        
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
        txtRangeValue.setText("1");
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
    
    public void updateRange(Date lower, Date upper){
    	
    	
    }
    
    public JSlider getYearSlider(){
    	
    	return sldYearRange;
    }
    
	public JSlider getMonthSlider(){
	    	
		return sldMonthRange;
	}

	public JSlider getDaySlider(){
	
		return sldDayRange;
	}
    
    public String getRangeValue(){
		return txtRangeValue.getText();
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
        
        renderer.setBaseItemLabelPaint(Color.white);
        renderer.setBaseLegendTextPaint(Color.white);
        renderer.setBaseFillPaint(Color.white);
        
        dateAxis = (DateAxis) plot.getDomainAxis();
        
        dateAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
        dateAxis.setVerticalTickLabels(true);
       
        
        plot.setRenderer( renderer );
                
        return chart;
    }

    public void setRange(Date dateLower, Date dateUpper){
    	
    	dateAxis.setRange(dateLower,dateUpper);
    	
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
		if(actions.get(rangeYearSliderListener) instanceof ChangeListener){
			sldYearRange.addChangeListener((ChangeListener)actions.get(rangeYearSliderListener));
		}
		if(actions.get(rangeMonthSliderListener) instanceof ChangeListener){
			sldMonthRange.addChangeListener((ChangeListener)actions.get(rangeMonthSliderListener));
		}
		if(actions.get(rangeDaySliderListener) instanceof ChangeListener){
			sldDayRange.addChangeListener((ChangeListener)actions.get(rangeDaySliderListener));
		}
		if(actions.get(rangeValueListener) instanceof KeyListener){
			
			txtRangeValue.addKeyListener((KeyListener) actions.get(rangeValueListener));
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {} //NOPMD
}