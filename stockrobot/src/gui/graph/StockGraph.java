package gui.graph;

import generic.FinancialLongConverter;

import java.util.Date;
import java.util.List;

import javax.swing.JPanel;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.util.Rotation;

import database.jpa.IJPAHelper;
import database.jpa.JPAHelper;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;

/**
 * Example of an simple JFreeChart time-graph of an fictional stock.
 * 
 * Uses JPA-system for data.
 * 
 * For learning JFreeChart only.
 * 
 * TODO: Split class into MVC-model.
 * 
 * @author Erik
 *
 */
public class StockGraph extends JFrame {
	private final int SIZE_X;
	private final int SIZE_Y;
	
	IJPAHelper jpa;
	TimeSeriesCollection dataset;

	private static final long serialVersionUID = 1L;


	public StockGraph(int size_x, int size_y) {
        super("Stock graph over time.");
        jpa = JPAHelper.getInstance();
        dataset = new TimeSeriesCollection();
    	this.SIZE_X = size_x;
    	this.SIZE_Y = size_y;
    }
	
	public void addStock(StockNames name){
    	TimeSeries serie = new TimeSeries(name.getName(), Minute.class);
        List<StockPrices> priceList = jpa.getPricesForStock(name);
        for(StockPrices stock : priceList){
        	Date date = stock.getTime();
        	serie.addOrUpdate(new Minute(date), FinancialLongConverter.toDouble(stock.getBuy()));
        }
		dataset.addSeries(serie);
	}
	
	public void activate(){
		
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
        		"Stock price of "+ " Volvo b", // title
        		"Date", // x-axis label
        		"Price Per Unit", // y-axis label
        		dataset, // data
        		true, // create legend?
        		true, // generate tooltips?
        		false // generate URLs?
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(SIZE_X, SIZE_Y));
        setContentPane(chartPanel);
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
	        
	        StockPrices stockNr2 = stocksPrice.get(0);
	        StockNames stocknameNr2 = stockNr2.getStockName();
	        
	        StockGraph stockGr = new StockGraph(700, 400);
	        
	        stockGr.addStock(stockname);
	        stockGr.addStock(stocknameNr2);

	        stockGr.activate();
	        stockGr.pack();
	        stockGr.setVisible(true);
	    }
}



