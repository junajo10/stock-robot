package gui;

import generic.FinancialLongConverter;
import gui.components.GUIFactory;
import gui.components.IGUIFactory;
import gui.controller.IController;
import gui.controller.MainMenuController;
import gui.mvc.Constants;
import gui.view.IView;
import gui.view.MainMenuView;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

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

import database.jpa.tables.PortfolioEntity;

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
	
	private JButton btn_ShowStock;
	
	//The model portfolio handler
	private IPortfolioHandler portfolioHandler;
	private IPortfolio currentPortfolio = null;
	
	private IGUIFactory guiFactory = new GUIFactory();
	

	public static void main(String[] args){
		
		PortfolioGui gui = new PortfolioGui(null);
	}
	
	/**
	 * Create the frame.
	 */
	public PortfolioGui(IPortfolioHandler portfolioHandler) {
		
		this.portfolioHandler = portfolioHandler;
		
		guiFactory.modifyDefaultWinow(this);
		setResizable(true);
		
		setTitle("Portfolio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 260, 499);
		contentPane = guiFactory.getMainContainer();
		
		setContentPane(contentPane);
		
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		// ====== Top Container ======
		JPanel pnl_menu = guiFactory.getDefaultContainer();
		pnl_menu.setMinimumSize(new Dimension(100, 35));
		pnl_menu.setPreferredSize(new Dimension(300, 35));
		pnl_menu.setMaximumSize(new Dimension(300,35));
		FlowLayout flowLayout_3 = (FlowLayout) pnl_menu.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		
		JLabel lbl_Portfolio = guiFactory.getTitleLabel("Portfolio");
		pnl_menu.add(lbl_Portfolio);
		
		cmb_portfolio = new JComboBox();
		cmb_portfolio.setModel(cmb_hld_portfolio);
		pnl_menu.add(cmb_portfolio);
		contentPane.add(pnl_menu);
		// ===========================
		
		JPanel pnl_BoxContainer = guiFactory.getInvisibleContainer();
		contentPane.add(pnl_BoxContainer);
		
		// ======Balance Container ======
		JPanel pnl_BalanceContainer = guiFactory.getDefaultContainer();
		pnl_BoxContainer.add(pnl_BalanceContainer);
		pnl_BalanceContainer.setLayout(new BoxLayout(pnl_BalanceContainer, BoxLayout.Y_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(200);
		pnl_BalanceContainer.add(horizontalStrut);
		
		JPanel pnl_BalancePanel = guiFactory.getInvisibleContainer();
		pnl_BalanceContainer.add(pnl_BalancePanel);
		
		JLabel lbl_Balance = guiFactory.getTitleLabel("Balance");
		pnl_BalancePanel.add(lbl_Balance);
		
		JPanel pnl_CashPanel = guiFactory.getInvisibleContainer();
		pnl_BalanceContainer.add(pnl_CashPanel);
		
		JLabel lbl_CashText = guiFactory.getDefaultLabel("Cash: ");
		pnl_CashPanel.add(lbl_CashText);
		
		lbl_CashValue = guiFactory.getDefaultLabel("NaN");
		pnl_CashPanel.add(lbl_CashValue);
		
		JPanel pnl_StockPanel = guiFactory.getInvisibleContainer();
		pnl_BalanceContainer.add(pnl_StockPanel);
		
		JLabel lbl_StockText = guiFactory.getDefaultLabel("Stock: ");
		pnl_StockPanel.add(lbl_StockText);
		
		JLabel lbl_StockValue = guiFactory.getDefaultLabel("NaN");
		pnl_StockPanel.add(lbl_StockValue);
		
		JPanel pnl_BalanceHistory = guiFactory.getInvisibleContainer();
		pnl_BalanceContainer.add(pnl_BalanceHistory);
		
		btn_BalanceHistory = guiFactory.getDefaultButton("History");
		btn_BalanceHistory.setEnabled(false);
		pnl_BalanceHistory.add(btn_BalanceHistory);
		// =============================
		
		// ======Algorithm Container======
		JPanel pnl_AlgorithmContainer = guiFactory.getDefaultContainer();
		pnl_BoxContainer.add(pnl_AlgorithmContainer);
		pnl_AlgorithmContainer.setLayout(new BoxLayout(pnl_AlgorithmContainer, BoxLayout.Y_AXIS));
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(200);
		pnl_AlgorithmContainer.add(horizontalStrut_1);
		
		JPanel pnl_AlgorithmPanel = guiFactory.getInvisibleContainer();
		pnl_AlgorithmContainer.add(pnl_AlgorithmPanel);
		
		JLabel lbl_Algorithm = guiFactory.getTitleLabel("Algorithms");
		lbl_Algorithm.setHorizontalAlignment(SwingConstants.LEFT);
		pnl_AlgorithmPanel.add(lbl_Algorithm);
		
		JPanel pnl_AlgorithmName = guiFactory.getInvisibleContainer();
		pnl_AlgorithmContainer.add(pnl_AlgorithmName);
		
		JLabel lbl_AlgorithmName = guiFactory.getDefaultLabel("None");
		pnl_AlgorithmName.add(lbl_AlgorithmName);
		
		JPanel pnl_AlgorithmChange = guiFactory.getInvisibleContainer();
		pnl_AlgorithmContainer.add(pnl_AlgorithmChange);
		
		btn_AlgorithmChange = guiFactory.getDefaultButton("Change");
		btn_AlgorithmChange.setEnabled(false);
		pnl_AlgorithmChange.add(btn_AlgorithmChange);
		// ===============================
		
		// ======Stock Container======
		JPanel pnl_StockContainer = guiFactory.getDefaultContainer();
		pnl_BoxContainer.add(pnl_StockContainer);
		pnl_StockContainer.setLayout(new BoxLayout(pnl_StockContainer, BoxLayout.Y_AXIS));
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(200);
		pnl_StockContainer.add(horizontalStrut_2);
		
		
		JPanel pnl_StockName = guiFactory.getInvisibleContainer();
		pnl_StockContainer.add(pnl_StockName);
		
		JLabel lbl_stocks = guiFactory.getTitleLabel("Stock");
		pnl_StockName.add(lbl_stocks);
		
		JPanel pnl_ShowStock = guiFactory.getInvisibleContainer();
		pnl_StockContainer.add(pnl_ShowStock);
		
		btn_ShowStock = guiFactory.getDefaultButton("Stock");
		btn_ShowStock.setEnabled(true);
		pnl_ShowStock.add(btn_ShowStock);
		// ===========================
		
		// ======Main menu ==============
		IView pnl_MainMenu = new MainMenuView();
		IController menuController = new MainMenuController();
		pnl_MainMenu.registerController(menuController);
		pnl_MainMenu.init();
		pnl_BoxContainer.add( (MainMenuView)pnl_MainMenu );
		
		if(portfolioHandler != null){
			updatePortfolios();
		}
			
		if (cmb_portfolio.getItemCount() > 0) {
			cmb_portfolio.setSelectedIndex(0);
			currentPortfolio = portfolioHandler.getPortfolios().get(0);
			updateCash();
		}
		
		
		this.setVisible(true);
	}
	
	/**
	 * Adds a listener that listens for presses on button button
	 * 
	 * @param listener
	 */
	public void addStockListener(ActionListener listener){
		btn_ShowStock.addActionListener(listener);
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
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
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
		});
	}
	
	public void setPortfolio(IPortfolio portfolio){
		
		this.currentPortfolio = portfolio;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if(evt.getPropertyName() == Constants.EVENT_TYPE.BUY_STOCK || evt.getPropertyName() == Constants.EVENT_TYPE.SELL_STOCK){
			if(evt.getNewValue() instanceof PortfolioEntity){
				
				PortfolioEntity portfolio = (PortfolioEntity) evt.getNewValue();
				
				//TODO make the comparison check with id instead of name. Did'nt work at the time im writing this
				if(currentPortfolio != null && portfolio.getName() == currentPortfolio.getName()){
					updateCash();
				}
			}
			
		}
	}
	
	public void updateCash(){
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				if(currentPortfolio != null)
					lbl_CashValue.setText(FinancialLongConverter.toStringTwoDecimalPoints(currentPortfolio.getUnusedAmount()));
				else{
					lbl_CashValue.setText("NaN");
				}
			}
		});
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
