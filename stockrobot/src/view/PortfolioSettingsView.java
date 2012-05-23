package view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.image.ImageObserver;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;

import utils.global.FinancialLongConverter;

import model.portfolio.IPortfolio;

public class PortfolioSettingsView extends JFrame implements IView {
	private static final long serialVersionUID = -4740806344418118901L;
	private JPanel contentPane;
	public final static String OKPRESSED = "Ok pressed";
	public final static String CANCELPRESSED = "Cancel pressed";
	public final static String WINDOWCLOSE = "Window Close";
	public final static String INVESTEXTRAXT = "Invest/Extract";
	public final static String SETTINGS = "Settings";
	
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel panel = new JPanel();
	private final JPanel panel_1 = new JPanel();
	private final JRadioButton rdbtnInvest = new JRadioButton("Invest Money");
	private final JButton btnOk = new JButton("OK");
	private final JButton btnCancel = new JButton("Cancel");
	private final JTextField txtAmount = new JTextField();
	private final JTextField textCurrentAmount = new JTextField();
	private final JLabel label = new JLabel("Amount:");
	private final JLabel label_1 = new JLabel("Current Amount:");
	private final JRadioButton rdbtnExtractMoney = new JRadioButton("Extract Money");
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JCheckBox chckbxStopBuying = new JCheckBox("Stop buying");
	private final JCheckBox chckbxStopSelling = new JCheckBox("Stop selling");
	private IPortfolio portfolio;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PortfolioSettingsView frame = new PortfolioSettingsView();
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
	public PortfolioSettingsView() {
		textCurrentAmount.setEditable(false);
		textCurrentAmount.setColumns(10);
		txtAmount.setColumns(10);
		buttonGroup.add(rdbtnInvest);
		rdbtnInvest.setSelected(true);
		setResizable(false);
		setTitle("Invest/Extract money");

		setBounds(100, 100, 330, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnCancel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnOk)
							.addContainerGap())
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 306, Short.MAX_VALUE)))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnOk)
						.addComponent(btnCancel))
					.addContainerGap())
		);
		
		tabbedPane.addTab(INVESTEXTRAXT, null, panel, null);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(rdbtnInvest, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
						.addComponent(rdbtnExtractMoney, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
						.addComponent(textCurrentAmount, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
						.addComponent(txtAmount))
					.addContainerGap(90, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnInvest)
					.addComponent(rdbtnExtractMoney)
					.addGap(8)
					.addComponent(label_1)
					.addGap(4)
					.addComponent(textCurrentAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(78, Short.MAX_VALUE))
		);
		buttonGroup.add(rdbtnExtractMoney);
		panel.setLayout(gl_panel);
		
		tabbedPane.addTab(SETTINGS, null, panel_1, null);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxStopSelling)
						.addComponent(chckbxStopBuying))
					.addContainerGap(183, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(26)
					.addComponent(chckbxStopBuying)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxStopSelling)
					.addContainerGap(182, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		contentPane.setLayout(gl_contentPane);
		
		tabbedPane.setSelectedIndex(0);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}

	@Override
	public void display(Object model) {
		this.portfolio = (IPortfolio) model;
		
		//TODO: Update amount
		textCurrentAmount.setText(FinancialLongConverter.toStringTwoDecimalPoints(portfolio.getUnusedAmount()));
		
		
		chckbxStopBuying.setSelected(portfolio.isStopBuyingFlagSet());
		chckbxStopSelling.setSelected(portfolio.isStopSellingFlagSet());
		
		setVisible(true);
	}

	@Override
	public void cleanup() {
		for (ActionListener action : btnCancel.getActionListeners()) {
			btnCancel.removeActionListener(action);
		}
		for (ActionListener action : btnOk.getActionListeners()) {
			btnOk.removeActionListener(action);
		}
		for (WindowListener action : this.getWindowListeners()) {
			removeWindowListener(action);
		}
		setVisible(false);
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {
		btnCancel.addActionListener((ActionListener) actions.get(CANCELPRESSED));
		btnOk.addActionListener((ActionListener) actions.get(OKPRESSED));
		addWindowListener((WindowListener) actions.get(WINDOWCLOSE));
	}
	
	public int getAmountEntered() {
		int amount = 0;
		try {
			amount = Integer.parseInt(txtAmount.getText());
			
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Couldent parse input data", "Error", ImageObserver.ERROR);
		}
		
		if (amount < 0) {
			JOptionPane.showMessageDialog(null, "input has to be larger than 0", "Error", ImageObserver.ERROR);
			amount = 0;
		}

		if (!rdbtnInvest.isSelected() && rdbtnExtractMoney.isSelected()) {
			amount = -amount;
		}
		return amount;
	}
	public String getSelectedPane() {
		return tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
	}
	public boolean isStopBuyingFlagSet() {
		return chckbxStopBuying.isSelected();
	}
	public boolean isStopSellingFlagSet() {
		return chckbxStopSelling.isSelected();
	}
}
