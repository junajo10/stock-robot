package controller;

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
import view.wizard.WizardPage;
import view.wizard.portfolio.PortfolioFromNewPage;

public class WizardFromNewPageController extends WizardPageController {

	private static final String CLASS_NAME = "WizardFromNewPageController";
	
	private final PortfolioFromNewPage page;
	private final WizardModel model;
	private final  PortfolioWizardModel pageModel;
	
	private final Map<String, EventListener> actions;
	
	public WizardFromNewPageController(WizardModel model, PortfolioWizardModel pageModel) {		
		
		this.page = new PortfolioFromNewPage(model, pageModel);
		this.model = model;
		this.pageModel = pageModel;
		
		actions = new HashMap<String, EventListener>();
		actions.put(PortfolioFromNewPage.BALANCE_INPUT_LISTENER, new BalanceListener());
		actions.put(PortfolioFromNewPage.ALGORITHM_LISTENER, new AlgorithmListener());
		
		pageModel.setAlgorithm(page.getSelectedAlgorithm());
		
		page.addActions(actions);
	}
	
	public ItemListener getAlgorithmListener(){
		return new AlgorithmListener();
	}
	
	public void updateFinnish(){
		
		if(pageModel.canFinish()){
			page.setErrorAlgorithms(false);
			page.setErrorBalance(false);
			model.setFinish(true);
			
		}
		else{
			page.setErrorAlgorithms(false);
			if(pageModel.getAlgorithm() == null){
				page.setErrorAlgorithms(true);
			}else{
				page.setErrorAlgorithms(false);
			}if(page.getBalance() > 0){
				page.setErrorBalance(false);
			}
			else{
				page.setErrorBalance(true);
			}
			model.setFinish(false);
		}
	}
	
	class AlgorithmListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getStateChange() == ItemEvent.SELECTED){
				pageModel.setAlgorithm(page.getSelectedAlgorithm());
			}
			
			updateFinnish();
		}
	}
	
	
	
	class BalanceListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {} //NOPMD

		@Override
		public void keyPressed(KeyEvent e) {} //NOPMD

		@Override
		public void keyReleased(KeyEvent e) {
			
			if(page.getBalance() > 0){
				pageModel.setBalance(page.getBalance());
				page.setErrorBalance(false);
			}else{
				pageModel.setBalance(-1);
			}
			updateFinnish();
		}
	}
	
	public PortfolioFromNewPage getPage() { return page; }
	public WizardModel getWizardModel() { return model; }
	
	@Override
	public void display(Object model) { //NOPMD
		
		//TODO Mattias, display?
	}

	@Override
	public void cleanup() {
		
		page.cleanup();
	}

	@Override
	public WizardPage getView() {
		return page;
	}
}