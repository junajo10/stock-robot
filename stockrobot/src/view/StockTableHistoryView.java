package view;


import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.StockPrices;
import model.robot.AlphaReceiver;

import utils.global.FinancialLongConverter;
import utils.global.Pair;

/**
 * Cloned from StockTableView
 * 
 * @author Daniel
 */
public class StockTableHistoryView extends JPanel implements PropertyChangeListener {
	
	private static final long serialVersionUID = -4930147798113095136L;
	
	private JTable table;
	private JScrollPane scroller;
	private TableModel model;

	private PortfolioEntity portfolio;
	private IJPAHelper jpaHelper = JPAHelper.getInstance();
	
	
	//Keeping to make it easy to test this class
	public static void main( String[] args ) {
		StockTableHistoryView view = new StockTableHistoryView(JPAHelper.getInstance().getAllPortfolios().get(1));
		
		JFrame frame = new JFrame();
		frame.setBounds( 100, 100, 1000, 700 );
		frame.add( view );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		view.updateInfo();
	}
	
	public StockTableHistoryView(PortfolioEntity portfolio) {
		this.portfolio = portfolio;
		init();
	}
	public Pair<Object[][], Object[]> populate() {
		List<PortfolioHistory> history = new ArrayList<PortfolioHistory>();
		history.addAll(portfolio.getHistory());
		
		String[] tableColumnNames = {"Name","Market","Bought for","Sold for", "Profit %","BuyDate","SellDate"};
		Object[][] rows = new Object[history.size()][tableColumnNames.length];
		
		for (int i = 0; i < history.size(); i++) {
			StockPrices sp = history.get(i).getStockPrice();
			rows[i][0] = sp.getStockName().getName();
			rows[i][1] = sp.getStockName().getMarket();
			rows[i][2] = FinancialLongConverter.toStringTwoDecimalPoints(history.get(i).getAmount()*sp.getBuy());
			rows[i][5] = sp.getTime();
			if (history.get(i).getSoldDate() != null) {
				StockPrices soldStockPrice = jpaHelper.getPricesForStockPeriod(sp.getStockName(), history.get(i).getSoldDate(), history.get(i).getSoldDate()).get(0);
				
				rows[i][4] = (double)(history.get(i).getAmount()*soldStockPrice.getSell())/(history.get(i).getAmount()*sp.getBuy());
				rows[i][6] = history.get(i).getSoldDate();
				rows[i][3] = FinancialLongConverter.toStringTwoDecimalPoints(history.get(i).getAmount()*soldStockPrice.getSell());
			}
		}
		
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
		
		if( evt.getPropertyName().equals( AlphaReceiver.PRICES_UPDATED ) )
			updateInfo();
	}
}