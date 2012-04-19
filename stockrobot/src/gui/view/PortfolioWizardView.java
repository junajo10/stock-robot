package gui.view;

import gui.components.GUIFactory;
import gui.components.IGUIFactory;
import gui.components.WizardGui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

public class PortfolioWizardView extends WizardGui {

	private static final long serialVersionUID = -2566592533727865685L;

	public static final int SCREEN_PORTFOLIO_INFO 	= 0;
	private static final String TITLE = "Portfolo Wizard";
	private static final String SUBTITLE_INFO = "info";
	
	private JPanel pnl_PortfolioInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PortfolioWizardView frame = new PortfolioWizardView();
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
	public PortfolioWizardView() {
		super();
		
		IGUIFactory factory = new GUIFactory();
		
		this.setTitle(TITLE);
		
		
		//============ INITIAL ============
		pnl_PortfolioInfo = factory.getInvisibleContainer();
		pnl_PortfolioInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
		BoxLayout lou_PortfolioInfo = new BoxLayout(pnl_PortfolioInfo, BoxLayout.PAGE_AXIS);
		pnl_PortfolioInfo.setLayout(lou_PortfolioInfo);
		pnl_PortfolioInfo.setPreferredSize(null);
		
		JPanel pnl_PortfolioName = factory.getInvisibleContainer();
		pnl_PortfolioName.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lbl_PortfolioName = factory.getDefaultLabel("Portfolio Name:");
		pnl_PortfolioName.add(lbl_PortfolioName);
		JTextField txt_PortfolioName = new JTextField();
		txt_PortfolioName.setColumns(20);
		txt_PortfolioName.setPreferredSize(new Dimension(2000,20));
		pnl_PortfolioName.add(txt_PortfolioName);
		pnl_PortfolioInfo.add(pnl_PortfolioName);
		
		//============ PORTFOLIO FROM ============
		JPanel pnl_PortfolioFrom = factory.getInvisibleContainer();
		pnl_PortfolioFrom.setPreferredSize(new Dimension(300,200));
		BoxLayout lou_PortfolioFrom = new BoxLayout(pnl_PortfolioFrom, BoxLayout.PAGE_AXIS);
		pnl_PortfolioFrom.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnl_PortfolioFrom.setLayout(lou_PortfolioFrom);
		//pnl_PortfolioFrom.getLayout();
		
		JRadioButtonMenuItem mnu_PortfolioFrom = new JRadioButtonMenuItem();
		
		// New portfolio
		JRadioButton rbtn_NewPortfolio = new JRadioButton();
		rbtn_NewPortfolio.setText("Create new portfolio");
		rbtn_NewPortfolio.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnl_PortfolioFrom.add(rbtn_NewPortfolio);
		
		// Clone portfolio
		JRadioButton rbtn_ClonePortfolio = new JRadioButton();
		rbtn_ClonePortfolio.setEnabled(true);
		rbtn_ClonePortfolio.setText("Clone existing portfolio");
		rbtn_ClonePortfolio.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnl_PortfolioFrom.add(rbtn_ClonePortfolio);
		
		JPanel pnl_PortfolioToClone = factory.getInvisibleContainer();
		pnl_PortfolioToClone.setEnabled(false);
		pnl_PortfolioToClone.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnl_PortfolioToClone.setBorder(factory.getDefaultBorder());
		JLabel lbl_ClonePortfolio = factory.getDefaultLabel("Portfolio to clone:");
		pnl_PortfolioToClone.add(lbl_ClonePortfolio);
		
		JComboBox cmb_ClonePortfolioList = new JComboBox();
		DefaultComboBoxModel cmb_hld_ClonePortfolioList = new DefaultComboBoxModel();
		cmb_ClonePortfolioList.setModel(cmb_hld_ClonePortfolioList);
		pnl_PortfolioToClone.add(cmb_ClonePortfolioList);
		cmb_ClonePortfolioList.setPreferredSize(new Dimension(200,20));
		pnl_PortfolioFrom.add(pnl_PortfolioToClone);
		
		pnl_PortfolioInfo.add(pnl_PortfolioFrom);
		//========================================
				
		loadScreen(pnl_PortfolioInfo);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		
	}
}
