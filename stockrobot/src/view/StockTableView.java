package view;


import gui.mvc.Constants;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

//import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;

import utils.global.FinancialLongConverter;
import utils.global.Pair;

import controller.gui.IController;
import controller.gui.StockTableController;




/**
 * Wrapping a JTable, fetching data from the price DB and inserting it.
 * Includes a scroll bar.
 * 
 * @author kristian
 *
 */
public class StockTableView extends JPanel implements PropertyChangeListener {
	
	private static final long serialVersionUID = -4930147798113095136L;
	
	private JTable table;
	private JScrollPane scroller;
	private TableModel model;
	
	/*
	//Keeping to make it easy to test this class
	public static void main( String[] args ) {
		
		StockTableView view = new StockTableView();
		
		JFrame frame = new JFrame();
		frame.setBounds( 100, 100, 700, 700 );
		frame.add( view );
		frame.show();
		
		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		view.updateInfo();
	}
	*/
	
	public StockTableView() {
		init();
	}
	
	/**
	 * Populate
	 * 
	 * Take all stockNames, and all data that needs to be shown in the JTable, and put them
	 * arrays that can be sent to the JTable class. Then return them as a tuple
	 * 
	 * @return
	 */
	public Pair<Object[][], Object[]> populate() {
		
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
	
	/**
	 * For now, just get all data and push it to the table
	 */
	public void init() {
		
		Pair<Object[][], Object[]> dataAndNames = populate();
		
		init( dataAndNames.getLeft(), dataAndNames.getRight() );
	}
	
	/**
	 * Supposed to be called by an event listener (property changer) 
	 */
	public void updateInfo() {
		
		Pair<Object[][], Object[]> dataAndNames = populate();
		
		//Iterate through all table cells and update them according to the new data
		for( int i = 0; i < dataAndNames.getLeft().length; i ++ )
			for( int j = 0; j < dataAndNames.getRight().length; j ++ )
				model.setValueAt(dataAndNames.getLeft()[i][j], i, j);
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
		model = table.getModel();
		
		//Create scroll bar
		scroller = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroller.setPreferredSize(new Dimension( 800, 600 ));
		add( scroller );
		
		repaint();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
		if( evt.getPropertyName().equals( Constants.EVENT_TYPE.PRICES_UPDATED ) )
			updateInfo();
	}
}