package view.wizard.portfolio;

import java.awt.Component;
import java.awt.event.ItemListener;
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
	private JComboBox cmbAlgorithms;
	private JLabel lblBalanceError;
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
		
		lblBalanceError = new JLabel("Error");
		lblBalanceError.setVisible(false);
		lblBalanceError.setForeground(Color.RED);
		pnl_balance.add(lblBalanceError);
		
		List<String> algorithms = PluginAlgortihmLoader.getInstance().getAlgorithmNames();
		
		JPanel pnlAlgorithm = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlAlgorithm.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(pnlAlgorithm);
		JLabel lblAlgorithmName = new JLabel("Algorithm Name:");
		pnlAlgorithm.add(lblAlgorithmName);
		cmbAlgorithms = new JComboBox(algorithms.toArray());
		pnlAlgorithm.add(cmbAlgorithms);
		
		lblErrorAlgorithm = new JLabel("Error");
		lblErrorAlgorithm.setForeground(Color.RED);
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

	@Override
	public void display(Object model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {
		// TODO Auto-generated method stub
		
	}
	
	
}