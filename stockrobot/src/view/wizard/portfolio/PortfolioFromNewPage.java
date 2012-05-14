package view.wizard.portfolio;

import java.awt.Component;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import model.algorithms.loader.PluginAlgortihmLoader;
import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;

import view.wizard.WizardPage;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;

public class PortfolioFromNewPage extends WizardPage {

	private static final long serialVersionUID = 20596476598729363L;
	
	public static final String BALANCE_INPUT_LISTENER 	= "balanceInputListener";
	public static final String ALGORITHM_LISTENER 		= "algorithmInputListener";
	
	private JComboBox cmbAlgorithms;
	private JLabel lblErrorBalance;
	private JLabel lblErrorAlgorithm;
	private JTextField txtBalance;
	
	public PortfolioFromNewPage(WizardModel wizardModel, PortfolioWizardModel pageModel) {
		super(wizardModel, pageModel);
							
		//============ INITIAL ============
		//pnl_PortfolioInfo = factory.getInvisibleContainer();
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		BoxLayout lou_PortfolioInfo = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		this.setLayout(lou_PortfolioInfo);
		this.setPreferredSize(new Dimension(541, 81));
		
		JPanel pnl_balance = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnl_balance.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		JLabel lbl_balance = new JLabel("Balance");
		pnl_balance.add(lbl_balance);
		txtBalance = new JTextField();
		txtBalance.setColumns(15);
		pnl_balance.add(txtBalance);
		this.add(pnl_balance);
		
		lblErrorBalance = new JLabel("Balance must be set ");
		lblErrorBalance.setVisible(false);
		lblErrorBalance.setForeground(Color.RED);
		pnl_balance.add(lblErrorBalance);
		
		List<String> algorithms = PluginAlgortihmLoader.getInstance().getAlgorithmNames();
		
		JPanel pnlAlgorithm = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlAlgorithm.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(pnlAlgorithm);
		JLabel lblAlgorithmName = new JLabel("Algorithm:");
		pnlAlgorithm.add(lblAlgorithmName);
		cmbAlgorithms = new JComboBox(algorithms.toArray());
		pnlAlgorithm.add(cmbAlgorithms);
		
		lblErrorAlgorithm = new JLabel("An algorithm must be set");
		lblErrorAlgorithm.setForeground(Color.RED);
		lblErrorAlgorithm.setVisible(false);
		pnlAlgorithm.add(lblErrorAlgorithm);
		//========================================
		
		
	}
	
	public void addAlgorithmListener(ItemListener listener){
		cmbAlgorithms.addItemListener(listener);
	}
	
	public void setAlgorithmListener(ItemListener listener){
		removeAlgorithmListeners();
		addAlgorithmListener(listener);
	}

	public void removeAlgorithmListeners(){
		for(ItemListener l : cmbAlgorithms.getItemListeners()){
			cmbAlgorithms.removeItemListener(l);
		}
	}
	
	public long getBalance(){
		
		long balance = -1;
		
		try{ 
			balance = Long.valueOf(txtBalance.getText());
		}catch (NumberFormatException e) {
			balance = -1;
		}		
		
		return balance;
	}
	
	public void setBalance(long balance){
		
		txtBalance.setText("" + balance);
	}
	
	public void setErrorBalance(boolean error){
		
		lblErrorBalance.setVisible(error);
	}
	
	public void setErrorAlgorithms(boolean error){
		
		lblErrorAlgorithm.setVisible(error);
	}

	@Override
	public void display(Object model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {
		
		if(actions.get(BALANCE_INPUT_LISTENER) instanceof KeyListener) {
			txtBalance.addKeyListener((KeyListener) actions.get(BALANCE_INPUT_LISTENER));
		}
		if(actions.get(BALANCE_INPUT_LISTENER) instanceof ItemListener){
			cmbAlgorithms.addItemListener((ItemListener) actions.get(ALGORITHM_LISTENER));
		}
	}
	
	
}