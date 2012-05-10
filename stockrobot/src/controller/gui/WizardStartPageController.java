package controller.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import model.portfolio.PortfolioHandler;
import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;


import utils.global.Log;
import view.wizard.WizardPage;
import view.wizard.portfolio.PortfolioPages;
import view.wizard.portfolio.PortfolioStartPage;

public class WizardStartPageController extends WizardPageController {

	private PortfolioStartPage page;
	private WizardModel model;
	private PortfolioWizardModel pageModel;
	private Map<String, EventListener> actions;
	
	private static final int MIN_SIZE_NAME = 5;
	
	private String createFromSelected = null;
	
	public WizardStartPageController(final WizardModel model, final PortfolioWizardModel pageModel) {
		
		this.model = model;
		this.pageModel = pageModel; 
		
		page = new PortfolioStartPage(model, pageModel, PortfolioHandler.getInstance());
		actions = new HashMap<String, EventListener>();
		actions.put(PortfolioStartPage.CREATE_FROM_NEW, getFromNewListener());
		actions.put(PortfolioStartPage.CREATE_FROM_CLONE, getFromCloneListener());
		actions.put(PortfolioStartPage.NAME_INPUT_LISTENER, new PortfolioNameLitesner());
		page.addActions(actions);
			
		
	}
	
	public WizardPage getView(){
		return page;
	}
	
	//======= Create portfolio from scratch ========
	public ItemListener getFromNewListener(){
		
		ItemListener listener = new FromNewListener();
			
		return listener;
	}
	
	public class FromNewListener implements ItemListener{


		public FromNewListener(){
			
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getStateChange() == ItemEvent.DESELECTED){
				model.removeNextPage();
				//createFromSelected = null;
			}else if(e.getStateChange() == ItemEvent.SELECTED){
				createFromSelected = page.CREATE_FROM_NEW;
				checkNext();
			}
		}	
	}
			
	//======= Create portfolio from scratch ========
	public ItemListener getFromCloneListener(){
		return new FromCloneListener();
	}
	
	public class FromCloneListener implements ItemListener{

		@Override 
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getStateChange() == ItemEvent.DESELECTED){
				page.setEnabledPanelClone(false);
				//createFromSelected = null;
			}else if(e.getStateChange() == ItemEvent.SELECTED){
				page.setEnabledPanelClone(true);
				createFromSelected = page.CREATE_FROM_CLONE;
			}
		}	
	}
	
	private boolean checkNext(){
		
		boolean canNext = false;
		if(page.getPortfolioName().length() > MIN_SIZE_NAME){
			page.showNameError(null);
			if(createFromSelected != null && createFromSelected.equals(page.CREATE_FROM_NEW)){
				model.setNextPage(PortfolioPages.PAGE_CREATE_FROM_NEW);
				canNext = true;
			}
		}else{
			page.showNameError("Minimum 5 chars");
		}
		
		if(!canNext && model.haveNext()){
			model.removeNextPage();
		}
		
		return canNext;
	}
		
	public class PortfolioNameLitesner implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {

			checkNext();
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
	
	//==============================================

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(Object model) {
		
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
}
