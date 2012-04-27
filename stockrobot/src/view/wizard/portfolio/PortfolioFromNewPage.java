package view.wizard.portfolio;

import java.awt.Component;
import java.beans.PropertyChangeEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;

import view.components.GUIFactory;
import view.components.IGUIFactory;
import view.wizard.WizardPage;

public class PortfolioFromNewPage extends WizardPage<PortfolioWizardModel> {

	private static final long serialVersionUID = 20596476598729363L;

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
		//========================================
	}

	
}