package gui.graph;

import generic.FinancialLongConverter;

import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.Rotation;

import database.jpa.IJPAHelper;
import database.jpa.JPAHelper;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;

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
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(size_x, size_y));
        setContentPane(chartPanel);
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



