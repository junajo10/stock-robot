package view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;
import javax.swing.table.TableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.PortfolioHistory;
import model.database.jpa.tables.StockPrices;
import model.robot.AlphaReceiver;
import utils.global.FinancialLongConverter;
import utils.global.Pair;

public class StockTableHistoryView extends JFrame {

	private static final long serialVersionUID = -7366514125495992396L;
	private JPanel contentPane;
	JComboBox comboBox = new JComboBox();
	private DefaultComboBoxModel cmb_hld_portfolio = new DefaultComboBoxModel();
	
	StockTableHistoryViewPanel panel_1 = new StockTableHistoryViewPanel();
	
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockTableHistoryView frame = new StockTableHistoryView(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	public void init() {
		setVisible(true);
	}
	/**
	 * Create the frame.
	 */
	public StockTableHistoryView(int i) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		
		//JPanel panel_1 = new JPanel();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
				.addComponent(panel_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
		);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setHorizontalAlignment(SwingConstants.LEFT);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(cmb_hld_portfolio);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(btnRefresh)
					.addPreferredGap(ComponentPlacement.RELATED, 285, Short.MAX_VALUE)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRefresh)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(1))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		
		updatePortfolios();
		
		//setVisible(true);
		
	}
	public void addChangePortfolioListener(ListDataListener listener){
		cmb_hld_portfolio.addListDataListener(listener);
		System.out.println("APA");
	}
	public void updatePortfolios(){
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				Object selected = cmb_hld_portfolio.getSelectedItem();
				
				List<PortfolioEntity> portfolios = JPAHelper.getInstance().getAllPortfolios();
				
				cmb_hld_portfolio.removeAllElements();
				for(int i = 0; i < portfolios.size(); i++){
					cmb_hld_portfolio.addElement(portfolios.get(i).getName());
				}
				if(portfolios.contains(selected))
					cmb_hld_portfolio.setSelectedItem(selected);
				else
					cmb_hld_portfolio.setSelectedItem(null);
			}
		});
	}
	public void setSelectedPortfolio(int i) {
		panel_1.setPortfolio(i);
	}
	
	class StockTableHistoryViewPanel extends JPanel implements PropertyChangeListener {
		
		private static final long serialVersionUID = -4930147798113095136L;
		
		private JTable table;
		private JScrollPane scroller;
		private TableModel model;

		private IJPAHelper jpaHelper = JPAHelper.getInstance();
		private PortfolioEntity portfolio = jpaHelper.getAllPortfolios().get(0);
		
		public StockTableHistoryViewPanel() {
			init();
		}
		public void setPortfolio(int portfolio) {
			this.portfolio = jpaHelper.getAllPortfolios().get(portfolio);
			init();
		}
		public StockTableHistoryViewPanel(PortfolioEntity portfolio) {
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
			
			removeAll();
			//Create table
			if (table != null)
				table.removeAll();
			
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
			pack();
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			// TODO Auto-generated method stub
			if( evt.getPropertyName().equals( AlphaReceiver.PRICES_UPDATED ) )
				updateInfo();
		}
	}
	
	
	
	
}
