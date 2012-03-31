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
	IJPAHelper jpa;

	private static final long serialVersionUID = 1L;

	public StockGraph() {
        super("Stock data.");
        jpa = JPAHelper.getInstance();
        List<StockPrices> stocksPrice = jpa.getAllStockPrices();
        
        StockPrices stock = stocksPrice.get(0);
        StockNames stockname = stock.getStockName();


        

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
        		"Stock price of "+ stockname.getName(), // title
        		"Date", // x-axis label
        		"Price Per Unit", // y-axis label
        		getStockHistory(stockname), // data
        		true, // create legend?
        		true, // generate tooltips?
        		false // generate URLs?
        );
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(700, 400));
        setContentPane(chartPanel);

    }
	
	/**
	 * 
	 * @param name to add timeline.
	 * @return
	 */
	private TimeSeriesCollection getStockHistory(StockNames name){
    	TimeSeries serie = new TimeSeries("Boliden B", Minute.class);
        List<StockPrices> priceList = jpa.getPricesForStock(name);
        for(StockPrices stock : priceList){
        	Date date = stock.getTime();
        	serie.addOrUpdate(new Minute(stock.getTime().getMinutes(), stock.getTime().getHours(), date.getDay(), date.getMonth(), date.getYear()+1900), FinancialLongConverter.toDouble(stock.getBuy()));
        }
    	TimeSeriesCollection dataset = new TimeSeriesCollection();
    	dataset.addSeries(serie);
    	
    	return dataset;
	}
    

	 public static void main(String[] args) {
	        StockGraph stock = new StockGraph();
	        stock.pack();
	        stock.setVisible(true);
	    }
}



