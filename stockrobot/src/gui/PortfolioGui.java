package gui;

import gui.mvc.Constants;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.Box;

import database.jpa.tables.PortfolioEntitys;

import portfolio.IPortfolio;
import portfolio.IPortfolioHandler;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: PortfolioGui.java
 * Description:
 * PortfolioGui is the primary gui for the ASTRO application
 * this gui is used as a hub to start for all the other gui windows.
 */
public class PortfolioGui extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = -8150305418187203103L;
	private JPanel contentPane;
	private JButton btn_BalanceHistory;
	private JButton btn_AlgorithmChange;
	private JComboBox cmb_portfolio;
	private DefaultComboBoxModel cmb_hld_portfolio = new DefaultComboBoxModel();
	private JLabel lbl_CashValue;
	
	//The model portfolio handler
	private IPortfolioHandler portfolioHandler;
	private IPortfolio currentPortfolio = null;
	
	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PortfolioGui frame = new PortfolioGui(null);
					new PortfolioController(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public PortfolioGui(IPortfolioHandler portfolioHandler) {
		
		this.portfolioHandler = portfolioHandler;
		
		setResizable(false);
		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
		setTitle("Portfolio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 281);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		JPanel pnl_BoxContainer = new JPanel();
		pnl_BoxContainer.setBackground(new Color(245, 245, 245));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		PortfolioContainer pnl_menu = new PortfolioContainer();
		pnl_menu.setMinimumSize(new Dimension(100, 35));
		pnl_menu.setPreferredSize(new Dimension(300, 35));
		pnl_menu.setMaximumSize(new Dimension(300,35));
		FlowLayout flowLayout_3 = (FlowLayout) pnl_menu.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		
		JLabel lbl_Portfolio = new JLabel("Portfolio");
		pnl_menu.add(lbl_Portfolio);
		
		cmb_portfolio = new JComboBox();
		cmb_portfolio.setModel(cmb_hld_portfolio);
		pnl_menu.add(cmb_portfolio);
		contentPane.add(pnl_menu);
		contentPane.add(pnl_BoxContainer);
		pnl_BoxContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		PortfolioContainer pnl_BalanceContainer = new PortfolioContainer();
		pnl_BoxContainer.add(pnl_BalanceContainer);
		pnl_BalanceContainer.setLayout(new BoxLayout(pnl_BalanceContainer, BoxLayout.Y_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(200);
		pnl_BalanceContainer.add(horizontalStrut);
		
		JPanel pnl_BalancePanel = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) pnl_BalancePanel.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		pnl_BalanceContainer.add(pnl_BalancePanel);
		
		JLabel lbl_Balance = new JLabel("Balance");
		lbl_Balance.setHorizontalAlignment(SwingConstants.LEFT);
		pnl_BalancePanel.add(lbl_Balance);
		pnl_BalancePanel.setBackground(null);
		
		JPanel pnl_CashPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnl_CashPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		pnl_BalanceContainer.add(pnl_CashPanel);
		pnl_CashPanel.setBackground(null);
		
		JLabel lbl_CashText = new JLabel("Cash: ");
		pnl_CashPanel.add(lbl_CashText);
		
		lbl_CashValue = new JLabel("NaN");
		pnl_CashPanel.add(lbl_CashValue);
		
		JPanel pnl_StockPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnl_StockPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		pnl_StockPanel.setBackground((Color) null);
		pnl_BalanceContainer.add(pnl_StockPanel);
		
		JLabel lbl_StockText = new JLabel("Stock: ");
		pnl_StockPanel.add(lbl_StockText);
		
		JLabel lbl_StockValue = new JLabel("NaN");
		pnl_StockPanel.add(lbl_StockValue);
		
		JPanel pnl_BalanceHistory = new JPanel();
		pnl_BalanceContainer.add(pnl_BalanceHistory);
		pnl_BalanceHistory.setBackground(null);
		
		btn_BalanceHistory = new JButton("History");
		btn_BalanceHistory.setForeground(Color.BLACK);
		pnl_BalanceHistory.add(btn_BalanceHistory);
		btn_BalanceHistory.setBackground(Color.WHITE);
		
		JButton button = new JButton("Change");
		button.setBackground(Color.WHITE);
		btn_BalanceHistory.setBackground(Color.WHITE);
		
		PortfolioContainer pnl_AlgorithmContainer = new PortfolioContainer();
		pnl_BoxContainer.add(pnl_AlgorithmContainer);
		pnl_AlgorithmContainer.setLayout(new BoxLayout(pnl_AlgorithmContainer, BoxLayout.Y_AXIS));
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(200);
		pnl_AlgorithmContainer.add(horizontalStrut_1);
		
		JPanel pnl_AlgorithmPanel = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) pnl_AlgorithmPanel.getLayout();
		flowLayout_5.setAlignment(FlowLayout.LEFT);
		pnl_AlgorithmPanel.setBackground((Color) null);
		pnl_AlgorithmContainer.add(pnl_AlgorithmPanel);
		
		JLabel lbl_Algorithm = new JLabel("Algorithms");
		lbl_Algorithm.setHorizontalAlignment(SwingConstants.LEFT);
		pnl_AlgorithmPanel.add(lbl_Algorithm);
		
		JPanel pnl_AlgorithmName = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) pnl_AlgorithmName.getLayout();
		flowLayout_6.setAlignment(FlowLayout.LEFT);
		pnl_AlgorithmContainer.add(pnl_AlgorithmName);
		pnl_AlgorithmName.setBackground((Color) null);
		
		JLabel lbl_AlgorithmName = new JLabel("None");
		lbl_AlgorithmName.setHorizontalAlignment(SwingConstants.LEFT);
		pnl_AlgorithmName.add(lbl_AlgorithmName);
		
		JPanel pnl_AlgorithmChange = new JPanel();
		pnl_AlgorithmContainer.add(pnl_AlgorithmChange);
		pnl_AlgorithmChange.setBackground((Color) null);
		
		btn_AlgorithmChange = new JButton("Change");
		btn_AlgorithmChange.setBackground(Color.WHITE);
		pnl_AlgorithmChange.add(btn_AlgorithmChange);
		
		updatePortfolios();
		
		this.setVisible(true);
	}

	/**
	 * Adds a listener that listens for presses on button Balance History
	 * 
	 * @param listener
	 */
	public void addBalanceHistoryListener(ActionListener listener){
		btn_BalanceHistory.addActionListener(listener);
	}
	
	/**
	 * Adds a listener that listens for presses on button change algorithm
	 * 
	 * @param listener
	 */
	public void addChangeAlgorithmListener(ActionListener listener){
		btn_AlgorithmChange.addActionListener(listener);
		
	}
	
	public void addChangePortfolioListener(ActionListener listener){
		cmb_portfolio.addActionListener(listener);
		
	}
	
	public void updatePortfolios(){
		
		Object selected = cmb_hld_portfolio.getSelectedItem();
		
		List<IPortfolio> portfolios = portfolioHandler.getPortfolios();
		cmb_hld_portfolio.removeAllElements();
		for(int i = 0; i < portfolios.size(); i++){
			
			cmb_hld_portfolio.addElement(new Item_cmb_Portfolio(portfolios.get(i)));
		}
		if(portfolios.contains(selected))
			cmb_hld_portfolio.setSelectedItem(selected);
		else
			cmb_hld_portfolio.setSelectedItem(null);
	}
	
	public void setPortfolio(IPortfolio portfolio){
		
		this.currentPortfolio = portfolio;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if(evt.getPropertyName() == Constants.EVENT_TYPE.BUY_STOCK || evt.getPropertyName() == Constants.EVENT_TYPE.SELL_STOCK){
			if(evt.getNewValue() instanceof PortfolioEntitys){
				
				PortfolioEntitys portfolio = (PortfolioEntitys) evt.getNewValue();
				
				//TODO make the comparison check with id instead of name. Did'nt work at the time im writing this
				if(portfolio.getName() == currentPortfolio.getName()){
					updateCash();
				}
			}
			
		}
	}
	
	public void updateCash(){
		
		if(currentPortfolio != null)
			lbl_CashValue.setText(""+currentPortfolio.getUnusedAmount());
		else{
			lbl_CashValue.setText("NaN");
		}
	}
	
	/**
	 * @author Mattias Markehed
	 * mattias.markehed@gmail.com
	 *
	 * filename: PortfolioGui.java
	 * Description:
	 * Item_cmb_Portfolio is used as an adapter for the mapping IPortfolios
	 * to the combo box containing portfolios
	 */
	protected class Item_cmb_Portfolio{
		
		private IPortfolio portfolio;
		
		public Item_cmb_Portfolio(IPortfolio portfolio){
			this.portfolio = portfolio;
		}
		
		public IPortfolio getPortfolio(){
			return portfolio;
		}
		
		@Override
		public String toString(){
			return portfolio.getName();
		}
	}
}
