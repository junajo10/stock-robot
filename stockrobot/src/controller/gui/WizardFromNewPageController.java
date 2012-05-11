package controller.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;
import view.wizard.WizardPage;
import view.wizard.portfolio.PortfolioFromNewPage;

public class WizardFromNewPageController extends WizardPageController {

	private PortfolioFromNewPage page;
	private WizardModel model;
	private PortfolioWizardModel pageModel;
	
	public WizardFromNewPageController(WizardModel model, PortfolioWizardModel pageModel) {		
		
		this.name = WizardFromNewPageController.class.getName();
		this.page = new PortfolioFromNewPage(model, pageModel);
		this.model = model;
		this.pageModel = pageModel;
		page.setAlgorithmListener(getAlgorithmListener());
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

	public boolean canFinish(){
		
		boolean finish = false;
		
		if(pageModel.canFinish()){
			finish = true;
		}
		
		return finish;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(Object model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, EventListener> getActionListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSubController(IController subController) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defineSubControllers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WizardPage getView() {
		return page;
	}
}
