package controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import model.portfolio.IPortfolio;
import model.portfolio.IPortfolioHandler;
import model.portfolio.PortfolioHandler;
import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;

import view.wizard.WizardPage;
import view.wizard.portfolio.PortfolioPages;
import view.wizard.portfolio.PortfolioStartPage;

/**
 * 
 * @author Mattias
 *
 */
public class WizardStartPageController extends WizardPageController {

	private static final String CLASS_NAME = "WizardStartPageController";
	
	private PortfolioStartPage page;
	private WizardModel model;
	private PortfolioWizardModel pageModel;
	private Map<String, EventListener> actions;
	
	private String createFromSelected = null;
	
	public WizardStartPageController(final WizardModel model, final PortfolioWizardModel pageModel) {
		
		this.model = model;
		this.pageModel = pageModel; 
		
		page = new PortfolioStartPage(model, pageModel, PortfolioHandler.getInstance());
		page.init();
		actions = new HashMap<String, EventListener>();
		actions.put(PortfolioStartPage.CREATE_FROM_NEW, new FromNewListener());
		actions.put(PortfolioStartPage.CREATE_FROM_CLONE, new FromCloneListener());
		actions.put(PortfolioStartPage.NAME_INPUT_LISTENER, new PortfolioNameLitesner());
		actions.put(PortfolioStartPage.CREATE_FROM_LISTENER, new CloneListener());
		page.addActions(actions);
	}
	
	@Override
	public WizardPage getView(){
		return page;
	}

	public class FromNewListener implements ItemListener {


		public FromNewListener() {
			
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getStateChange() == ItemEvent.DESELECTED) {
				model.removeNextPage();
				//createFromSelected = null;
			}else if(e.getStateChange() == ItemEvent.SELECTED) {
				createFromSelected = PortfolioStartPage.CREATE_FROM_NEW;
				checkNext();
			}
		}	
	}
			
	public class FromCloneListener implements ItemListener{

		@Override 
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getStateChange() == ItemEvent.DESELECTED){
				page.setEnabledPanelClone(false);
				//createFromSelected = null;
			}else if(e.getStateChange() == ItemEvent.SELECTED){
				page.setEnabledPanelClone(true);
				createFromSelected = PortfolioStartPage.CREATE_FROM_CLONE;
			}
		}	
	}
	
	private boolean checkNext() {
		
		boolean canNext = false;
		if(pageModel.getProperties().get(PortfolioWizardModel.PROPERTY_NAME) == true) {
			page.setErrorName(false);
			if(createFromSelected != null && createFromSelected.equals(PortfolioStartPage.CREATE_FROM_NEW)) {
				model.setNextPage(PortfolioPages.PAGE_CREATE_FROM_NEW);
				canNext = true;
			}
		}else {
			page.setErrorName(true);
		}
		
		if(!canNext && model.haveNext()) {
			model.removeNextPage();
		}
		
		return canNext;
	}
	
	class CloneListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getStateChange() == ItemEvent.SELECTED){
				IPortfolioHandler portfolios = PortfolioHandler.getInstance();
				
				IPortfolio selectedPortfolio = null;
				for(IPortfolio p : portfolios.getPortfolios()){
					if (p.getName().equals(e.getItem())) {
						selectedPortfolio = p;
						break;
					}
				}
				if(selectedPortfolio != null){
					pageModel.setBalance(selectedPortfolio.getUnusedAmount());
					pageModel.setAlgorithm(selectedPortfolio.getAlgorithm().getName());
					model.setFinish(true);	
				}
				
				
			}else if(e.getStateChange() == ItemEvent.DESELECTED){
				pageModel.setBalance(-1);
				pageModel.setAlgorithm(null);
				model.setFinish(false);
			}
		}
	}
		
	public class PortfolioNameLitesner implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {} //NOPMD

		@Override
		public void keyPressed(KeyEvent e) {} //NOPMD

		@Override
		public void keyReleased(KeyEvent e) {
			pageModel.setName(page.getPortfolioName());
			checkNext();
		}

	}
	
	//==============================================

	@Override
	public void propertyChange(PropertyChangeEvent evt) {} //NOPMD

	@Override
	public void display(Object model) {} //NOPMD

	@Override
	public void cleanup() {
		
		page.cleanup();
		
		page = null;
	}

	/**
	 * Get map of all eventlisteners this controller uses
	 */
	@Override
	public Map<String, EventListener> getActionListeners() {
		
		return actions;
	}

	/**
	 * Define Subcontrollers
	 * 
	 * No subcontrollers defined yet
	 */
	@Override
	public void defineSubControllers() {} //NOPMD

	/**
	 * Get this classes unique name
	 */
	@Override
	public String getName() {
		
		return CLASS_NAME;
	}
}
