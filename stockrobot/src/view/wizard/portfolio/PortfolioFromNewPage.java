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


import model.portfolio.AlgortihmLoader;
import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;

import view.wizard.WizardPage;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;

/**
 * 
 * @author Mattias
 *
 */
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
		
		JPanel pnlBalance = new JPanel();
		FlowLayout fl_pnlBalance = (FlowLayout) pnlBalance.getLayout();
		fl_pnlBalance.setAlignment(FlowLayout.LEFT);
		JLabel lbl_balance = new JLabel("Balance");
		pnlBalance.add(lbl_balance);
		txtBalance = new JTextField();
		txtBalance.setColumns(15);
		pnlBalance.add(txtBalance);
		this.add(pnlBalance);
		
		lblErrorBalance = new JLabel("Balance must be set ");
		lblErrorBalance.setVisible(false);
		lblErrorBalance.setForeground(Color.RED);
		pnlBalance.add(lblErrorBalance);
		
		List<String> algorithms = AlgortihmLoader.getInstance().getAlgorithmNames();
		
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
	
	public String getSelectedAlgorithm(){
		
		return cmbAlgorithms.getSelectedItem().toString();
	}
	
	public void setErrorAlgorithms(boolean error){
		
		lblErrorAlgorithm.setVisible(error);
	}

	@Override
	public void display(Object model) {} //NOPMD

	@Override
	public void addActions(Map<String, EventListener> actions) {
				
		if(actions.get(BALANCE_INPUT_LISTENER) instanceof KeyListener) {
			txtBalance.addKeyListener((KeyListener) actions.get(BALANCE_INPUT_LISTENER));
		}
		if(actions.get(ALGORITHM_LISTENER) instanceof ItemListener){
			cmbAlgorithms.addItemListener((ItemListener) actions.get(ALGORITHM_LISTENER));
		}
	}
}