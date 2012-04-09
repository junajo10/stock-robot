package gui.view;

import generic.Pair;
import gui.controller.IController;
import gui.controller.StockTableController;

import java.awt.Dimension;

//import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;


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
	private TableModel model;
	private IController controller;
	
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
	
	public StockTableView() {}
	
	/**
	 * For now, just get all data and push it to the table
	 */
	public void init() {
		
		Pair<Object[][], Object[]> dataAndNames = ((StockTableController) controller).populate();
		
		init( dataAndNames.getLeft(), dataAndNames.getRight() );
	}
	
	/**
	 * Set a controller
	 * 
	 * @param controller
	 */
	public void registerController( IController controller ) {
		
		this.controller = controller;
	}
	
	/**
	 * Supposed to be called by an event listener (property changer) 
	 */
	public void updateInfo() {
		
		Pair<Object[][], Object[]> dataAndNames = ((StockTableController) controller).populate();
		
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
}