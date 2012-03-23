package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JSpinner;
import javax.swing.JScrollBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class PortfolioGui extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PortfolioGui frame = new PortfolioGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PortfolioGui() {
		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
		setTitle("Portfolio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 262, 270);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		
		JPanel pnl_BoxContainers = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) pnl_BoxContainers.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		pnl_BoxContainers.setBackground(null);
		
		PortfolioContainer pnl_menu = new PortfolioContainer();
		FlowLayout flowLayout_3 = (FlowLayout) pnl_menu.getLayout();
		flowLayout_3.setAlignment(FlowLayout.RIGHT);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(pnl_menu, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
				.addComponent(pnl_BoxContainers, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(pnl_menu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnl_BoxContainers, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
		);
		
		JLabel lbl_Portfolio = new JLabel("Portfolio");
		pnl_menu.add(lbl_Portfolio);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Portfolio1", "Portfolio2", "Portfolio3"}));
		pnl_menu.add(comboBox);
		
		PortfolioContainer pnl_BalanceContainer = new PortfolioContainer();
		pnl_BoxContainers.add(pnl_BalanceContainer);
		pnl_BalanceContainer.setLayout(new BoxLayout(pnl_BalanceContainer, BoxLayout.Y_AXIS));
		
		JPanel pnl_BalancePanel = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) pnl_BalancePanel.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		pnl_BalanceContainer.add(pnl_BalancePanel);
		
		JLabel lbl_Balance = new JLabel("Balance");
		pnl_BalancePanel.add(lbl_Balance);
		pnl_BalancePanel.setBackground(null);
		
		JPanel pnl_CashPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnl_CashPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		pnl_BalanceContainer.add(pnl_CashPanel);
		pnl_CashPanel.setBackground(null);
		
		JLabel lbl_CashText = new JLabel("Cash: ");
		pnl_CashPanel.add(lbl_CashText);
		
		JLabel lbl_CashValue = new JLabel("NaN");
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
		
		JButton btn_balanceHistory = new JButton("History");
		pnl_BalanceHistory.add(btn_balanceHistory);
		btn_balanceHistory.setBackground(Color.WHITE);
		contentPane.setLayout(gl_contentPane);
	}
}
