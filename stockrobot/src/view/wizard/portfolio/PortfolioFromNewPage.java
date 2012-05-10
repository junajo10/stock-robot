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

import view.components.GUIFactory;
import view.components.IGUIFactory;
import view.wizard.WizardPage;

public class PortfolioFromNewPage extends WizardPage {

	private static final long serialVersionUID = 20596476598729363L;
	private JComboBox cmb_algorithms;
	
	public PortfolioFromNewPage(WizardModel wizardModel, PortfolioWizardModel pageModel) {
		super(wizardModel, pageModel);
					
		IGUIFactory factory = new GUIFactory();
		
		//============ INITIAL ============
		//pnl_PortfolioInfo = factory.getInvisibleContainer();
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		BoxLayout lou_PortfolioInfo = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		this.setLayout(lou_PortfolioInfo);
		this.setPreferredSize(null);
		JLabel lbl_PortfolioName = factory.getDefaultLabel("Portfolio Name:");
		this.add(lbl_PortfolioName);
		
		JPanel pnl_balance = factory.getInvisibleContainer();
		JLabel lbl_balance = factory.getDefaultLabel("Balance");
		pnl_balance.add(lbl_balance);
		JTextField txt_balance = factory.getDefaultTextField();
		txt_balance.setColumns(15);
		pnl_balance.add(txt_balance);
		this.add(pnl_balance);
		
		List<String> algorithms = PluginAlgortihmLoader.getInstance().getAlgorithmNames();
		cmb_algorithms = new JComboBox(algorithms.toArray());
		
		this.add(cmb_algorithms);
		//========================================
		
		
	}
	
	public void addAlgorithmListener(ItemListener listener){
		cmb_algorithms.addItemListener(listener);
	}
	
	public void setAlgorithmListener(ItemListener listener){
		removeAlgorithmListeners();
		addAlgorithmListener(listener);
	}

	public void removeAlgorithmListeners(){
		for(ItemListener l : cmb_algorithms.getItemListeners()){
			cmb_algorithms.removeItemListener(l);
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