package view.wizard.portfolio;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.portfolio.IPortfolio;
import model.portfolio.IPortfolioHandler;
import model.portfolio.wizard.ItemCmbPortfolio;
import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;
import view.wizard.WizardPage;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.FlowLayout;

public class PortfolioStartPage extends WizardPage {

	private JRadioButton rbtn_ClonePortfolio; 
	private JRadioButton rbtn_NewPortfolio;
	private JPanel pnl_PortfolioToClone;
	private JComboBox cmb_ClonePortfolioList;
	private IPortfolioHandler portfolioHandler;
	private ButtonGroup buttonGroup;
	private JTextField txtPortfolioName;
	private JLabel lblNameError;
	
	public static final String CREATE_FROM_NEW 		= "createFromNew";
	public static final String CREATE_FROM_CLONE 		= "createFromClone";
	public static final String CREATE_FROM_LISTENER 	= "createFromListener";
	public static final String NAME_INPUT_LISTENER 	= "nameInputListener";
	
	private DefaultComboBoxModel cmb_hld_ClonePortfolioList = new DefaultComboBoxModel();
	
	private static final long serialVersionUID = 1271972909341943446L;
	
	public PortfolioStartPage(WizardModel wizardModel, PortfolioWizardModel pageModel, IPortfolioHandler portfolioHandler) {
		super(wizardModel, pageModel);
		
		this.portfolioHandler = portfolioHandler;
		init();
	}

	public void init() {
				
		
		//============ INITIAL ============
		//pnl_PortfolioInfo = factory.getInvisibleContainer();
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		BoxLayout lou_PortfolioInfo = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		this.setLayout(lou_PortfolioInfo);
		this.setPreferredSize(new Dimension(697, 137));
		
		JPanel pnlContent = new JPanel();
		add(pnlContent);
		
		JPanel pnl_PortfolioName = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnl_PortfolioName.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		pnl_PortfolioName.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lbl_PortfolioName = new JLabel("Portfolio Name:");
		pnl_PortfolioName.add(lbl_PortfolioName);
		txtPortfolioName = new JTextField();
		txtPortfolioName.setColumns(20);
		txtPortfolioName.setPreferredSize(new Dimension(2000,20));
		pnl_PortfolioName.add(txtPortfolioName);
		
		//============ PORTFOLIO FROM ============
		JPanel pnl_PortfolioFrom = new JPanel();
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
		buttonGroup = new ButtonGroup();
		buttonGroup.add(rbtn_NewPortfolio);
		buttonGroup.add(rbtn_ClonePortfolio);
		GroupLayout gl_pnlContent = new GroupLayout(pnlContent);
		gl_pnlContent.setHorizontalGroup(
			gl_pnlContent.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlContent.createSequentialGroup()
					.addComponent(pnl_PortfolioFrom, GroupLayout.PREFERRED_SIZE, 389, GroupLayout.PREFERRED_SIZE)
					.addGap(232))
				.addComponent(pnl_PortfolioName, GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
		);
		gl_pnlContent.setVerticalGroup(
			gl_pnlContent.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlContent.createSequentialGroup()
					.addComponent(pnl_PortfolioName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnl_PortfolioFrom, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addGap(150))
		);
		
		lblNameError = new JLabel("A portfolio name must be set");
		lblNameError.setVisible(false);
		lblNameError.setForeground(new Color(255, 0, 0));
		pnl_PortfolioName.add(lblNameError);
		
		
		pnl_PortfolioToClone = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnl_PortfolioToClone.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		pnl_PortfolioFrom.add(pnl_PortfolioToClone);
		pnl_PortfolioToClone.setEnabled(false);
		pnl_PortfolioToClone.setAlignmentX(Component.LEFT_ALIGNMENT);
		//pnl_PortfolioToClone.setBorder(factory.getDefaultBorder());
		JLabel lbl_ClonePortfolio = new JLabel("Portfolio to clone:");
		pnl_PortfolioToClone.add(lbl_ClonePortfolio);
		
		cmb_ClonePortfolioList = new JComboBox();
		cmb_ClonePortfolioList.setModel(cmb_hld_ClonePortfolioList);
		pnl_PortfolioToClone.add(cmb_ClonePortfolioList);
		cmb_ClonePortfolioList.setPreferredSize(new Dimension(200,20));
		cmb_ClonePortfolioList.setEnabled(false);
		pnlContent.setLayout(gl_pnlContent);
		
		//DefaultComboBoxModel cmb_hld_ClonePortfolioList = new DefaultComboBoxModel();
		//========================================
	}
	
	public void updatePortfolios(){
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
			Object selected = cmb_hld_ClonePortfolioList.getSelectedItem();
			
			List<IPortfolio> portfolios = portfolioHandler.getPortfolios();
			cmb_hld_ClonePortfolioList.removeAllElements();
			for(int i = 0; i < portfolios.size(); i++){
				
				cmb_hld_ClonePortfolioList.addElement(new ItemCmbPortfolio(portfolios.get(i)));
			}
			if(portfolios.contains(selected))
				cmb_hld_ClonePortfolioList.setSelectedItem(selected);
			else
				cmb_hld_ClonePortfolioList.setSelectedItem(null);
			}
		});
	}
	
	public void setCreateFromNewListener(ItemListener listener){
		
		rbtn_NewPortfolio.addItemListener(listener); 
	}
	
	public void setCreateFromCloneListener(ItemListener listener){
		
		rbtn_ClonePortfolio.addItemListener(listener); 
	}
	
	public void setEnabledPanelClone(boolean enabled){
		
		cmb_ClonePortfolioList.setEnabled(enabled);
		
	}
	
	public void setPortfolioName(String name){
		
		txtPortfolioName.setText(name);
	}

	public String getPortfolioName(){
		return txtPortfolioName.getText();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	}

	@Override
	public void display(Object model) {
		// TODO Auto-generated method stub
		
	}
	
	public void setErrorName(boolean error){
		
		lblNameError.setVisible(error);
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {

		if(actions.get(CREATE_FROM_NEW) instanceof ItemListener){
			
			rbtn_NewPortfolio.addItemListener((ItemListener)actions.get(CREATE_FROM_NEW));
		}
		if(actions.get(CREATE_FROM_CLONE) instanceof ItemListener){
			
			rbtn_ClonePortfolio.addItemListener((ItemListener)actions.get(CREATE_FROM_CLONE));
		}
		if(actions.get(NAME_INPUT_LISTENER) instanceof KeyListener){
		
			txtPortfolioName.addKeyListener((KeyListener) actions.get(NAME_INPUT_LISTENER));
		}
	}
}
