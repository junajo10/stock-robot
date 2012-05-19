package view.wizard.portfolio;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import model.portfolio.IPortfolio;
import model.portfolio.IPortfolioHandler;
import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;
import view.wizard.WizardPage;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.FlowLayout;

/**
 * 
 * @author Mattias
 *
 */
public class PortfolioStartPage extends WizardPage {

	private JRadioButton rbtnClonePortfolio; 
	private JRadioButton rbtnNewPortfolio;
	private JPanel pnlPortfolioToClone;
	private JComboBox cmbClonePortfolioList;
	private IPortfolioHandler portfolioHandler;
	private ButtonGroup buttonGroup;
	private JTextField txtPortfolioName;
	private JLabel lblNameError;
	
	public static final String CREATE_FROM_NEW 		= "createFromNew";
	public static final String CREATE_FROM_CLONE 		= "createFromClone";
	public static final String CREATE_FROM_LISTENER 	= "createFromListener";
	public static final String NAME_INPUT_LISTENER 	= "nameInputListener";
		
	private static final long serialVersionUID = 1271972909341943446L;
	
	public PortfolioStartPage(WizardModel wizardModel, PortfolioWizardModel pageModel, IPortfolioHandler portfolioHandler) {
		super(wizardModel, pageModel);
		
		this.portfolioHandler = portfolioHandler;
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
		
		JPanel pnlPortfolioName = new JPanel();
		FlowLayout fl_pnlPortfolioName = (FlowLayout) pnlPortfolioName.getLayout();
		fl_pnlPortfolioName.setAlignment(FlowLayout.LEFT);
		pnlPortfolioName.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JLabel lbl_PortfolioName = new JLabel("Portfolio Name:");
		pnlPortfolioName.add(lbl_PortfolioName);
		txtPortfolioName = new JTextField();
		txtPortfolioName.setColumns(20);
		txtPortfolioName.setPreferredSize(new Dimension(2000,20));
		pnlPortfolioName.add(txtPortfolioName);
		
		//============ PORTFOLIO FROM ============
		JPanel pnlPortfolioFrom = new JPanel();
		pnlPortfolioFrom.setPreferredSize(new Dimension(300,200));
		BoxLayout bl_pnlPortfolioFrom = new BoxLayout(pnlPortfolioFrom, BoxLayout.PAGE_AXIS);
		pnlPortfolioFrom.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnlPortfolioFrom.setLayout(bl_pnlPortfolioFrom);
		//pnl_PortfolioFrom.getLayout();
					
		// New portfolio
		rbtnNewPortfolio = new JRadioButton();
		rbtnNewPortfolio.setText("Create new portfolio");
		rbtnNewPortfolio.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		pnlPortfolioFrom.add(rbtnNewPortfolio);
		
		// Clone portfolio
		rbtnClonePortfolio = new JRadioButton();
		rbtnClonePortfolio.setEnabled(true);
		rbtnClonePortfolio.setText("Clone existing portfolio");
		rbtnClonePortfolio.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnlPortfolioFrom.add(rbtnClonePortfolio);
		buttonGroup = new ButtonGroup();
		buttonGroup.add(rbtnNewPortfolio);
		buttonGroup.add(rbtnClonePortfolio);
		GroupLayout gl_pnlContent = new GroupLayout(pnlContent);
		gl_pnlContent.setHorizontalGroup(
			gl_pnlContent.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlContent.createSequentialGroup()
					.addComponent(pnlPortfolioFrom, GroupLayout.PREFERRED_SIZE, 389, GroupLayout.PREFERRED_SIZE)
					.addGap(232))
				.addComponent(pnlPortfolioName, GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
		);
		gl_pnlContent.setVerticalGroup(
			gl_pnlContent.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlContent.createSequentialGroup()
					.addComponent(pnlPortfolioName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlPortfolioFrom, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addGap(150))
		);
		
		lblNameError = new JLabel("A portfolio name must be set");
		lblNameError.setVisible(false);
		lblNameError.setForeground(new Color(255, 0, 0));
		pnlPortfolioName.add(lblNameError);
		
		
		pnlPortfolioToClone = new JPanel();
		FlowLayout fl_pnlPortfolioToClone = (FlowLayout) pnlPortfolioToClone.getLayout();
		fl_pnlPortfolioToClone.setAlignment(FlowLayout.LEFT);
		pnlPortfolioFrom.add(pnlPortfolioToClone);
		pnlPortfolioToClone.setEnabled(false);
		pnlPortfolioToClone.setAlignmentX(Component.LEFT_ALIGNMENT);
		//pnl_PortfolioToClone.setBorder(factory.getDefaultBorder());
		JLabel lblClonePortfolio = new JLabel("Portfolio to clone:");
		pnlPortfolioToClone.add(lblClonePortfolio);
		
		cmbClonePortfolioList = new JComboBox();
		cmbClonePortfolioList.setPreferredSize(new Dimension(200,20));
		cmbClonePortfolioList.setEnabled(false);
		
		pnlPortfolioToClone.add(cmbClonePortfolioList);
		updatePortfolios();
		pnlContent.setLayout(gl_pnlContent);
		
		//========================================
	}
	
	public void updatePortfolios(){
		
		cmbClonePortfolioList.removeAllItems();
		for(IPortfolio p : portfolioHandler.getPortfolios()){
			cmbClonePortfolioList.addItem(p.getName());
		}
		
		cmbClonePortfolioList.validate();
		cmbClonePortfolioList.repaint();

	}
	
	public void setCreateFromNewListener(ItemListener listener){
		
		rbtnNewPortfolio.addItemListener(listener); 
	}
	
	public void setCreateFromCloneListener(ItemListener listener){
		
		rbtnClonePortfolio.addItemListener(listener); 
	}
	
	public void setEnabledPanelClone(boolean enabled){
		
		cmbClonePortfolioList.setEnabled(enabled);
	}
	
	public void setPortfolioName(String name){
		
		txtPortfolioName.setText(name);
	}

	public String getPortfolioName(){
		return txtPortfolioName.getText();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {} //NOPMD

	@Override
	public void display(Object model) {} //NOPMD
	
	public void setErrorName(boolean error){
		
		lblNameError.setVisible(error);
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {

		if(actions.get(CREATE_FROM_NEW) instanceof ItemListener){
			
			rbtnNewPortfolio.addItemListener((ItemListener)actions.get(CREATE_FROM_NEW));
		}
		if(actions.get(CREATE_FROM_CLONE) instanceof ItemListener){
			
			rbtnClonePortfolio.addItemListener((ItemListener)actions.get(CREATE_FROM_CLONE));
		}
		if(actions.get(NAME_INPUT_LISTENER) instanceof KeyListener){
		
			txtPortfolioName.addKeyListener((KeyListener) actions.get(NAME_INPUT_LISTENER));
		}
		if(actions.get(CREATE_FROM_LISTENER) instanceof ItemListener){
			
			cmbClonePortfolioList.addItemListener((ItemListener) actions.get(CREATE_FROM_LISTENER));
		}
	}
}
