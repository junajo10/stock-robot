package controller.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;
import utils.global.Log;
import view.wizard.WizardPage;
import view.wizard.portfolio.PortfolioFromNewPage;

public class WizardFromNewPageController extends WizardPageController {

	private PortfolioFromNewPage page;
	private WizardModel model;
	private PortfolioWizardModel pageModel;
	
	private Map<String, EventListener> actions;
	
	public WizardFromNewPageController(WizardModel model, PortfolioWizardModel pageModel) {		
		
		this.name = WizardFromNewPageController.class.getName();
		this.page = new PortfolioFromNewPage(model, pageModel);
		this.model = model;
		this.pageModel = pageModel;
		
		actions = new HashMap<String, EventListener>();
		actions.put(PortfolioFromNewPage.BALANCE_INPUT_LISTENER, new BalanceListener());
		actions.put(PortfolioFromNewPage.ALGORITHM_LISTENER, new AlgorithmLitener());
		
		page.addActions(actions);
	}
	
	public ItemListener getAlgorithmListener(){
		return new AlgorithmLitener();
	}
	
	class AlgorithmLitener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			Log.log(Log.TAG.DEBUG, "Algorithm selcted");
			
			if(e.getStateChange() == ItemEvent.SELECTED){
				model.setFinish(true);
				page.setErrorAlgorithms(false);
			}else if(e.getStateChange() == ItemEvent.DESELECTED){
				model.setFinish(false);
				page.setErrorAlgorithms(true);
			}
		}
	}
	
	class BalanceListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			
			Log.log(Log.TAG.DEBUG, "balance");
			
			if(page.getBalance() > 0){
				pageModel.setBalance(page.getBalance());
				page.setErrorBalance(false);
			}
			else{
				page.setErrorBalance(true);
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public PortfolioFromNewPage getPage() { return page; }
	public WizardModel getWizardModel() { return model; }

	public boolean canFinish(){
		
		boolean finish = false;
		
		if(pageModel.canFinish()){
			
			finish = true;
		}else{
			
			//if(page.){
				
			//}
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
