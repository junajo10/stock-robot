package controller.wizard.portfolio;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import model.wizard.WizardModel;
import view.wizard.portfolio.PortfolioFromNewPage;

public class WizardFromNewPageController {

	private PortfolioFromNewPage page;
	private WizardModel model;
	
	public WizardFromNewPageController(PortfolioFromNewPage page, WizardModel model) {		
		this.page = page;
		this.model = model;
	}
	
	public ItemListener getAlgorithmListener(){
		return new AlgorithmLitener();
	}
	
	class AlgorithmLitener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getStateChange() == ItemEvent.SELECTED){
				model.setFinish(true);
			}else if(e.getStateChange() == ItemEvent.DESELECTED){
				model.setFinish(false);
			}
		}
	}
	
	public PortfolioFromNewPage getPage() { return page; }
	public WizardModel getWizardModel() { return model; }
}
