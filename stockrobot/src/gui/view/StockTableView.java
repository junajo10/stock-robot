package gui.view;

import generic.FinancialLongConverter;
import generic.Pair;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import database.jpa.IJPAHelper;
import database.jpa.JPAHelper;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;

/**
 * Wrapping a JTable, fetching data from the price DB and inserting it.
 * Includes a scroll bar.
 * 
 * @author kristian
 *
 */
public class StockTableView extends JPanel {
	
	private static final long serialVersionUID = -4930147798113095136L;
	
	private JTable table;
	private JScrollPane scroller;
	
	/*
	//Keeping to make it easy to test this class
	public static void main( String[] args ) {
		
		StockTableView view = new StockTableView();
		
		JFrame frame = new JFrame();
		frame.setBounds( 100, 100, 700, 700 );
		frame.add( view );
		frame.show();
	}
	*/
	
	/**
	 * For now, just get all data and push it to the table
	 * 
	 */
	public StockTableView() {
		
		Pair<Object[][], Object[]> dataAndNames = populate();
		init( dataAndNames.getLeft(), dataAndNames.getRight() );
	}
	
	public void updateInfo() {
		
		Pair<Object[][], Object[]> dataAndNames = populate();
		
		System.out.println( "UPDATREEE!" );
		
		scroller.remove(table);
		remove(scroller);
		revalidate();
		repaint();
		
		init( dataAndNames.getLeft(), dataAndNames.getRight() );
	}
	
	/**
	 * Construct the JTable instance and feed it data received from populate
	 * 
	 * @param data
	 * @param names
	 */
	private void init( Object[][] data, Object[] names ) {
		
		//Create table
		table = new JTable( data, names );
		table.revalidate();
		table.setRowHeight( 30 );
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(6).setPreferredWidth(200);
		
		//Create scroll bar
		scroller = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroller.setPreferredSize(new Dimension( 800, 600 ));
		add( scroller );
		
		repaint();
	}
	
	/**
	 * Populate
	 * 
	 * Take all stockNames, and all data that needs to be shown in the JTable, and put them
	 * arrays that can be sent to the JTable class. Then return them as a tuple
	 * 
	 * @return
	 */
	private Pair<Object[][], Object[]> populate() {
		
		//Database connection
		IJPAHelper jpa = JPAHelper.getInstance();
		List<Pair<StockNames,List<StockPrices>>> stockInfo = jpa.getStockInfo(1);
		
		//Define column names
		String[] tableColumnNames = {"Name","Market","Buy","Sell","Latest","Volume","Time"};
		
		//Create storage for row data
		Object[][] rows = new Object[stockInfo.size()][tableColumnNames.length];
		
		//Collect row data from DB
		for( int i = 0; i < stockInfo.size(); i ++ ) {
			
			rows[ i ][ 0 ] = stockInfo.get( i ).getLeft().getName();
			rows[ i ][ 1 ] = stockInfo.get( i ).getLeft().getMarket();
			
			rows[ i ][ 2 ] = FinancialLongConverter.toStringTwoDecimalPoints( stockInfo.get( i ).getRight().get(0).getBuy() );
			rows[ i ][ 3 ] = FinancialLongConverter.toStringTwoDecimalPoints( stockInfo.get( i ).getRight().get(0).getSell() );
			rows[ i ][ 4 ] = FinancialLongConverter.toStringTwoDecimalPoints( stockInfo.get( i ).getRight().get(0).getLatest() );
			rows[ i ][ 5 ] = stockInfo.get( i ).getRight().get(0).getVolume();
			rows[ i ][ 6 ] = stockInfo.get( i ).getRight().get(0).getTime();
		}
		
		//Return row data and column names
		return new Pair<Object[][], Object[]>(rows, tableColumnNames);
	}
}