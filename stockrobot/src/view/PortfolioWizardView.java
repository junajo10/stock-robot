package view;

import gui.components.Item_cmb_Portfolio;
import gui.components.GUIFactory;
import gui.components.IGUIFactory;
import gui.components.wizard.WizardGui;
import gui.components.wizard.WizardPage;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.portfolio.IPortfolio;
import model.portfolio.PortfolioHandler;
import model.portfolio.wizard.PassablePortfolioBuilder;


public class PortfolioWizardView extends WizardGui {

	private static final String TITLE = "Portfolo Wizard";
	@SuppressWarnings("unused")
	private static final String SUBTITLE_INFO = "info";
	
	public static final String PAGE_START = "start_page";
	public static final String PAGE_PORTFOLIO_NEW = "portfolio_from_new_page";

	private PassablePortfolioBuilder portfolioBuilder;
	private PortfolioPage startPage;
	private PortfolioNewPage newPortfiolioPage;
	private PortfolioHandler portfolioHandler;
	
	private DefaultComboBoxModel cmb_hld_ClonePortfolioList = new DefaultComboBoxModel();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PortfolioWizardView frame = new PortfolioWizardView(new PassablePortfolioBuilder(),null);
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
	public PortfolioWizardView(PassablePortfolioBuilder portfolioBuilder, PortfolioHandler portfolioHandler) {
		super();
		
		this.portfolioBuilder = portfolioBuilder;
		this.portfolioHandler = portfolioHandler;
		this.setTitle(TITLE);
		this.setSubTitle(SUBTITLE_INFO);
		
		startPage = new PortfolioPage();
		this.addPage(PAGE_START, startPage);
		this.loadPage(PAGE_START);
		
		newPortfiolioPage = new PortfolioNewPage();
		this.addPage(PAGE_PORTFOLIO_NEW, newPortfiolioPage);
		
		//loadScreen(pnl_PortfolioInfo);
	}
	
	public PortfolioPage getPageStart(){
		return startPage;
	}
	
	public PortfolioNewPage getPagenewPortfolio(){
		return newPortfiolioPage;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
	}
	
	public class PortfolioPage extends WizardPage{

		//private JPanel pnl_PortfolioInfo;
		JRadioButton rbtn_ClonePortfolio;
		JRadioButton rbtn_NewPortfolio;
		JPanel pnl_PortfolioToClone;
		JComboBox cmb_ClonePortfolioList;
		
		public PortfolioPage() {
					
			
			IGUIFactory factory = new GUIFactory();
			
			//============ INITIAL ============
			//pnl_PortfolioInfo = factory.getInvisibleContainer();
			this.setAlignmentX(Component.LEFT_ALIGNMENT);
			BoxLayout lou_PortfolioInfo = new BoxLayout(this, BoxLayout.PAGE_AXIS);
			this.setLayout(lou_PortfolioInfo);
			this.setPreferredSize(null);
			
			JPanel pnl_PortfolioName = factory.getInvisibleContainer();
			pnl_PortfolioName.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			JLabel lbl_PortfolioName = factory.getDefaultLabel("Portfolio Name:");
			pnl_PortfolioName.add(lbl_PortfolioName);
			JTextField txt_PortfolioName = new JTextField();
			txt_PortfolioName.setColumns(20);
			txt_PortfolioName.setPreferredSize(new Dimension(2000,20));
			pnl_PortfolioName.add(txt_PortfolioName);
			this.add(pnl_PortfolioName);
			
			//============ PORTFOLIO FROM ============
			JPanel pnl_PortfolioFrom = factory.getInvisibleContainer();
			pnl_PortfolioFrom.setPreferredSize(new Dimension(300,200));
			BoxLayout lou_PortfolioFrom = new BoxLayout(pnl_PortfolioFrom, BoxLayout.PAGE_AXIS);
			pnl_PortfolioFrom.setAlignmentX(Component.LEFT_ALIGNMENT);
			pnl_PortfolioFrom.setLayout(lou_PortfolioFrom);
			//pnl_PortfolioFrom.getLayout();
						
			// New portfolio
			rbtn_NewPortfolio = new JRadioButton();
			rbtn_NewPortfolio.setText("Create new portfolio");
			rbtn_NewPortfolio.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			pnl_PortfolioFrom.add(rbtn_NewPortfolio);
			
			// Clone portfolio
			rbtn_ClonePortfolio = new JRadioButton();
			rbtn_ClonePortfolio.setEnabled(true);
			rbtn_ClonePortfolio.setText("Clone existing portfolio");
			rbtn_ClonePortfolio.setAlignmentX(Component.LEFT_ALIGNMENT);
			pnl_PortfolioFrom.add(rbtn_ClonePortfolio);
			
			ButtonGroup buttonGroup = new ButtonGroup();
			buttonGroup.add(rbtn_NewPortfolio);
			buttonGroup.add(rbtn_ClonePortfolio);
			
			
			pnl_PortfolioToClone = factory.getInvisibleContainer();
			pnl_PortfolioToClone.setEnabled(false);
			pnl_PortfolioToClone.setAlignmentX(Component.LEFT_ALIGNMENT);
			pnl_PortfolioToClone.setBorder(factory.getDefaultBorder());
			JLabel lbl_ClonePortfolio = factory.getDefaultLabel("Portfolio to clone:");
			pnl_PortfolioToClone.add(lbl_ClonePortfolio);
			
			cmb_ClonePortfolioList = new JComboBox();
			DefaultComboBoxModel cmb_hld_ClonePortfolioList = new DefaultComboBoxModel();
			cmb_ClonePortfolioList.setModel(cmb_hld_ClonePortfolioList);
			pnl_PortfolioToClone.add(cmb_ClonePortfolioList);
			cmb_ClonePortfolioList.setPreferredSize(new Dimension(200,20));
			cmb_ClonePortfolioList.setEnabled(false);
			cmb_ClonePortfolioList.setModel(cmb_hld_ClonePortfolioList);
			pnl_PortfolioFrom.add(pnl_PortfolioToClone);
			
			this.add(pnl_PortfolioFrom);
			//========================================
		}
		
		public void updatePortfolios(){
			SwingUtilities.invokeLater(new Runnable() {
				public void run(){
				Object selected = cmb_hld_ClonePortfolioList.getSelectedItem();
				
				List<IPortfolio> portfolios = portfolioHandler.getPortfolios();
				cmb_hld_ClonePortfolioList.removeAllElements();
				for(int i = 0; i < portfolios.size(); i++){
					
					cmb_hld_ClonePortfolioList.addElement(new Item_cmb_Portfolio(portfolios.get(i)));
				}
				if(portfolios.contains(selected))
					cmb_hld_ClonePortfolioList.setSelectedItem(selected);
				else
					cmb_hld_ClonePortfolioList.setSelectedItem(null);
				}
			});
		}
		/*
		// New portfolio
		JRadioButton rbtn_NewPortfolio = new JRadioButton();
		rbtn_NewPortfolio.setText("Create new portfolio");
		rbtn_NewPortfolio.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnl_PortfolioFrom.add(rbtn_NewPortfolio);
		*/
		
		@Override
		protected void load() {
			// TODO Auto-generated method stub
			
		}
		
		public void setCreateFromNewListener(ItemListener listener){
			
			rbtn_NewPortfolio.addItemListener(listener); 
		}
		
		public void setCreateFromCloneListener(ItemListener listener){
			
			rbtn_ClonePortfolio.addItemListener(listener); 
		}
		
		public void setEnabledPanelClone(boolean enabled){
			
			//pnl_PortfolioToClone.setEnabled(enabled);
			cmb_ClonePortfolioList.setEnabled(enabled);
			//cmb_ClonePortfolioList.setVisible(false);
		}
	}
	
	public class PortfolioNewPage extends WizardPage{

		
		public PortfolioNewPage() {
					
			
			IGUIFactory factory = new GUIFactory();
			
			//============ INITIAL ============
			//pnl_PortfolioInfo = factory.getInvisibleContainer();
			this.setAlignmentX(Component.LEFT_ALIGNMENT);
			BoxLayout lou_PortfolioInfo = new BoxLayout(this, BoxLayout.PAGE_AXIS);
			this.setLayout(lou_PortfolioInfo);
			this.setPreferredSize(null);
			JLabel lbl_PortfolioName = factory.getDefaultLabel("Portfolio Name:");
			this.add(lbl_PortfolioName);
			//========================================
		}
		
		@Override
		protected void load() {
			// TODO Auto-generated method stub
			
		}
	}
	
}
