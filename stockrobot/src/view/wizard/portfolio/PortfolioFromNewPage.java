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

public class PortfolioFromNewPage extends WizardPage {

	private static final long serialVersionUID = 20596476598729363L;
	private JComboBox cmbAlgorithms;
	
	public PortfolioFromNewPage(WizardModel wizardModel, PortfolioWizardModel pageModel) {
		super(wizardModel, pageModel);
							
		//============ INITIAL ============
		//pnl_PortfolioInfo = factory.getInvisibleContainer();
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		BoxLayout lou_PortfolioInfo = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		this.setLayout(lou_PortfolioInfo);
		this.setPreferredSize(new Dimension(287, 81));
		
		JPanel pnl_balance = new JPanel();
		JLabel lbl_balance = new JLabel("Balance");
		pnl_balance.add(lbl_balance);
		JTextField txt_balance = new JTextField();
		txt_balance.setColumns(15);
		pnl_balance.add(txt_balance);
		this.add(pnl_balance);
		
		List<String> algorithms = PluginAlgortihmLoader.getInstance().getAlgorithmNames();
		
		JPanel pnlAlgorithm = new JPanel();
		add(pnlAlgorithm);
		JLabel lblAlgorithmName = new JLabel("Algorithm Name:");
		pnlAlgorithm.add(lblAlgorithmName);
		cmbAlgorithms = new JComboBox(algorithms.toArray());
		pnlAlgorithm.add(cmbAlgorithms);
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