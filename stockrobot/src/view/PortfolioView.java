package view;

import javax.swing.ComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListDataListener;

import utils.global.FinancialLongConverter;

import model.portfolio.IPortfolio;
import model.portfolio.IPortfolioHandler;
import model.trader.ITrader;
import java.awt.event.ActionEvent;

public class PortfolioView extends JFrame implements IView {

	private static final long serialVersionUID = -1857650100977127973L;
	public static final String WINDOW_CLOSE 				= "windowClose";
	public static final String CREATE_PORTFOLIO			= "createPortfolio";	
	public static final String MANAGE_ALGORITHMS			= "manageAlgorithms";
	public static final String HISTORY					= "History";
	private JPanel contentPane;
	private IPortfolioHandler portfolios;
	private JComboBox cmbSelectPortfolio;
	private ITrader trader;
	
	private JLabel lblStockValueVar;
	private JLabel lblBalanceVar;
	private JLabel lblTotalVar;
	
	private JButton btnSeeStock;
	private JButton btnHistory;
	private JButton btnNewPortfolio;
	private JButton btnManageAlgortihmSettings;
	
	private IPortfolio selectedPortfolio;
	
	private PortfoliosCmbModel cmbModel;
	
	/**
	 * Create the frame.
	 */
	public PortfolioView(ITrader trader, IPortfolioHandler portfolios) {
		
		this.portfolios = portfolios; 
		this.trader = trader;
		trader.addAddObserver(this);
		setTitle("Portfolio");
		
		setBounds(100, 100, 441, 194);
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		JPanel pnlSelectPortfolio = new JPanel();
		
		JPanel pnlBalanceContainer = new JPanel();
		pnlBalanceContainer.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel pnlViewsHolder = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnlSelectPortfolio, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
						.addComponent(pnlBalanceContainer, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
						.addComponent(pnlViewsHolder, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(pnlSelectPortfolio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlBalanceContainer, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlViewsHolder, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(11, Short.MAX_VALUE))
		);
		pnlViewsHolder.setLayout(new BoxLayout(pnlViewsHolder, BoxLayout.X_AXIS));
		
		btnSeeStock = new JButton("Stock");
		btnSeeStock.setEnabled(false);
		pnlViewsHolder.add(btnSeeStock);
		
		btnHistory = new JButton("History");
		pnlViewsHolder.add(btnHistory);
		
		btnNewPortfolio = new JButton("New Portfolio");
		pnlViewsHolder.add(btnNewPortfolio);
		pnlBalanceContainer.setLayout(new BoxLayout(pnlBalanceContainer, BoxLayout.Y_AXIS));
		
		JPanel pnlBalance = new JPanel();
		FlowLayout fl_pnlBalance = (FlowLayout) pnlBalance.getLayout();
		fl_pnlBalance.setAlignment(FlowLayout.LEFT);
		pnlBalanceContainer.add(pnlBalance);
		
		JLabel lblBalance = new JLabel("Balance:");
		pnlBalance.add(lblBalance);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		pnlBalance.add(horizontalStrut);
		
		lblBalanceVar = new JLabel("0");
		pnlBalance.add(lblBalanceVar);
		
		JPanel pnlStockValue = new JPanel();
		FlowLayout fl_pnlStockValue = (FlowLayout) pnlStockValue.getLayout();
		fl_pnlStockValue.setAlignment(FlowLayout.LEFT);
		pnlBalanceContainer.add(pnlStockValue);
		
		JLabel lblStockValue = new JLabel("Stock value:");
		pnlStockValue.add(lblStockValue);
		
		lblStockValueVar = new JLabel("0");
		pnlStockValue.add(lblStockValueVar);
		
		JPanel pnlTotal = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlTotal.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		pnlBalanceContainer.add(pnlTotal);
		
		JLabel lblTotal = new JLabel("Total:");
		pnlTotal.add(lblTotal);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(40);
		pnlTotal.add(horizontalStrut_1);
		
		lblTotalVar = new JLabel("0");
		pnlTotal.add(lblTotalVar);
		
		JLabel lblSelectPortfolio = new JLabel("Portfolio");
		lblSelectPortfolio.setHorizontalAlignment(SwingConstants.LEFT);
		
		cmbSelectPortfolio = new JComboBox();
		pnlSelectPortfolio.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		pnlSelectPortfolio.add(lblSelectPortfolio);
		pnlSelectPortfolio.add(cmbSelectPortfolio);
		contentPane.setLayout(gl_contentPane);
		
		cmbModel = new PortfoliosCmbModel(portfolios);
		cmbSelectPortfolio.setModel(cmbModel);
		
		btnManageAlgortihmSettings = new JButton("Algortihm Settings");
		btnManageAlgortihmSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		pnlSelectPortfolio.add(btnManageAlgortihmSettings);
		if(cmbModel.getSize() > 0){
			cmbSelectPortfolio.setSelectedIndex(0);
		}
	}

	@Override
	public void display(Object model) {
		
		this.setVisible(true);
	}

	@Override
	public void cleanup() {
		
		trader.removeObserver(this);
	}
	
	/**
	 * 
	 * Added this window to be able to read the current selected value from the controller
	 * 
	 * TOOD: Maybe create a better way to read the selected value?
	 * 
	 * @return
	 */
	public Object getSelectedItem() {
		
		return cmbModel.getSelectedItem();
	}
	
	public void setSelectedPortfolio(IPortfolio portfolio){
		
		selectedPortfolio = portfolio;
	}
	
	@Override
	public void addActions(Map<String, EventListener> actions) {

		if(actions.get(CREATE_PORTFOLIO) instanceof ActionListener) {
			btnNewPortfolio.addActionListener((ActionListener) actions.get(CREATE_PORTFOLIO));
		}
		
		//Connect the algorthm settings button to it's corresponding ActionListener
		if(actions.get(MANAGE_ALGORITHMS) instanceof ActionListener) {
			btnManageAlgortihmSettings.addActionListener( (ActionListener) actions.get(MANAGE_ALGORITHMS) );
		}
		//Connect the history button to it's corresponding ActionListener
		if(actions.get(HISTORY) instanceof ActionListener) {
			btnHistory.addActionListener( (ActionListener) actions.get(HISTORY) );
		}
	}
	
	private class PortfoliosCmbModel implements ComboBoxModel {

		private IPortfolioHandler portfolios;
		private String selected;
		
		public PortfoliosCmbModel(IPortfolioHandler portfolios){
			
			this.portfolios = portfolios;
		}
		
		@Override
		public int getSize() {

			return portfolios.getPortfolios().size();
		}

		@Override
		public Object getElementAt(int index) {

			setSelectedPortfolio(portfolios.getPortfolios().get(index));
			updateValues();
			return selectedPortfolio.getName();
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			
			// TODO Mattias: Do something here?
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			
			// TODO Mattias: Do something here?
		}

		@Override
		public void setSelectedItem(Object anItem) {
			
			selected = (String) anItem;
		}

		@Override
		public Object getSelectedItem() {

			return selected;
		}
	}

	public void updateValues(){
		
		if(selectedPortfolio != null){
			lblBalanceVar.setText(FinancialLongConverter.toStringTwoDecimalPoints(selectedPortfolio.getUnusedAmount()));
			lblStockValueVar.setText(FinancialLongConverter.toStringTwoDecimalPoints(selectedPortfolio.getCurrentWorth()));
			lblTotalVar.setText(FinancialLongConverter.toStringTwoDecimalPoints(selectedPortfolio.getCurrentWorth()+selectedPortfolio.getUnusedAmount()));
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if(evt.getPropertyName().equals(ITrader.BUY_STOCK)){
			updateValues();
		}else if(evt.getPropertyName().equals(ITrader.SELL_STOCK)){
			updateValues();
		}
	}
	
	public IPortfolio getSelectedPortfolio() {
		return selectedPortfolio;
	}
}