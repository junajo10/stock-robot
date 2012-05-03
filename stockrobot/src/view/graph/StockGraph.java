package view.graph;


import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import javax.swing.JFrame;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

import utils.global.FinancialLongConverter;
import javax.swing.JMenuItem;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



/**
 * Example of an simple JFreeChart time-graph of a stock.
 * <p>
 * Uses JPA-system for data.
 * <p>
 * For learning JFreeChart.
 * <p>
 * TODO: Split class into MVC-model.
 * <p>
 * @author Erik
 *
 */
public class StockGraph extends JFrame {

	IJPAHelper jpa;
	TimeSeriesCollection dataset;
	Map<String, TimeSeries> timeSeries;

	private static final long serialVersionUID = 1L;


	@SuppressWarnings("deprecation")
	public StockGraph(int size_x, int size_y, boolean plotted) {
        super("Stock graph over time.");
        jpa = JPAHelper.getInstance();
        dataset = new TimeSeriesCollection();
        timeSeries = new HashMap<String, TimeSeries>();
    	
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
        		"Stock price over time.", // title
        		"Date", // x-axis label
        		"Price Per Unit", // y-axis label
        		dataset, // data
        		true, // create legend?
        		true, // generate tooltips?
        		false // generate URLs?
        );
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);
        
        JMenuItem mntmSetDate = new JMenuItem("Set date");
        mnFile.add(mntmSetDate);
        
        JMenuItem mntmClose = new JMenuItem("Close");
        mntmClose.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		System.exit(0);
        	}
        });
        mnFile.add(mntmClose);
        
        JMenu mnView = new JMenu("View");
        menuBar.add(mnView);
        
        JRadioButtonMenuItem rdbtnmntmNormal = new JRadioButtonMenuItem("Normal");
        rdbtnmntmNormal.setSelected(true);
        mnView.add(rdbtnmntmNormal);
        
        JRadioButtonMenuItem rdbtnmntmCandlesticks = new JRadioButtonMenuItem("CandleSticks");
        rdbtnmntmCandlesticks.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseReleased(MouseEvent arg0) {
        		System.out.println("Candlesticks view!");
        	}


        });
        mnView.add(rdbtnmntmCandlesticks);
        
        JRadioButtonMenuItem rdbtnmntmHighlow = new JRadioButtonMenuItem("High-Low");
        mnView.add(rdbtnmntmHighlow);
        
        JRadioButtonMenuItem rdbtnmntmMovingAverage = new JRadioButtonMenuItem("Moving Average");
        mnView.add(rdbtnmntmMovingAverage);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setZoomAroundAnchor(true);
        chartPanel.setRefreshBuffer(true);
        chartPanel.setFillZoomRectangle(false);
        chartPanel.setEnforceFileExtensions(false);
        chartPanel.setPreferredSize(new Dimension(797, 448));
        setContentPane(chartPanel);
        GroupLayout gl_chartPanel = new GroupLayout(chartPanel);
        gl_chartPanel.setHorizontalGroup(
        	gl_chartPanel.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 867, Short.MAX_VALUE)
        );
        gl_chartPanel.setVerticalGroup(
        	gl_chartPanel.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 401, Short.MAX_VALUE)
        );
        chartPanel.setLayout(gl_chartPanel);
        if (plotted) {
	        XYPlot plot = (XYPlot) chart.getPlot();
	        plot.setBackgroundPaint(Color.lightGray);
	        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
	        plot.setDomainGridlinePaint(Color.white);
	        plot.setRangeGridlinePaint(Color.white);
	        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
	        renderer.setShapesVisible(true);
	        renderer.setShapesFilled(true);
        }

    }
	
	/**
	 * Adds a stock to the graph.
	 * <p>
	 * Can be called multiple times to add several stocks to the same graph.
	 * <p>
	 * @param name stock to be added.
	 */
	public void addStock(StockNames name){
    	@SuppressWarnings("deprecation")
		TimeSeries serie = new TimeSeries(name.getName(), Minute.class);
        List<StockPrices> priceList = jpa.getPricesForStock(name);
        for(StockPrices stock : priceList){
        	Date date = stock.getTime();
        	serie.addOrUpdate(new Minute(date), FinancialLongConverter.toDouble(stock.getBuy()));
        }
        timeSeries.put(name.getName(), serie);
		dataset.addSeries(serie);
	}
	
	/**
	 * Removes a stock from the stock-graph.
	 * <p>
	 * @param name stock to be removed.
	 */
	public void removeStock(StockNames name){
		dataset.removeSeries(timeSeries.get(name.getName()));
		timeSeries.remove(name);
	}
	
	
    
	/**
	 * Testing main for StockGraph.
	 * 
	 * Good example for learning how to use it.
	 * 
	 * Creates a StockGraph containing 2 stocks.
	 * 
	 * @param args
	 */
	 public static void main(String[] args) {
	        
	        List<StockPrices> stocksPrice = JPAHelper.getInstance().getAllStockPrices();
	        
	        StockPrices stock = stocksPrice.get(0);
	        StockNames stockname = stock.getStockName();
	        
	        StockPrices stockNr2 = stocksPrice.get(1);
	        StockNames stocknameNr2 = stockNr2.getStockName();
	        
	        StockGraph stockGr = new StockGraph(700, 400, true);
	        
	        stockGr.addStock(stockname);
	        stockGr.addStock(stocknameNr2);
	        stockGr.removeStock(stocknameNr2);
	        stockGr.removeStock(stocknameNr2);

	        stockGr.pack();
	        stockGr.setVisible(true);
	        

	    }
}



