package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;


import model.portfolio.PortfolioHandler;
import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;


import view.wizard.WizardPage;
import view.wizard.portfolio.PortfolioPages;
import view.wizard.portfolio.PortfolioStartPage;

public class WizardStartPageController extends WizardPageController {

	private PortfolioStartPage page;
	private WizardModel model;
	private PortfolioWizardModel pageModel;
	private Map<String, EventListener> actions;
	
	public WizardStartPageController(final WizardModel model, final PortfolioWizardModel pageModel) {
		
		this.model = model;
		this.pageModel = pageModel; 
		
		page = new PortfolioStartPage(model, pageModel, PortfolioHandler.getInstance());
		actions = new HashMap<String, EventListener>();
		actions.put(PortfolioStartPage.CREATE_FROM_NEW, getFromNewListener());
		actions.put(PortfolioStartPage.CREATE_FROM_CLONE, getFromCloneListener());
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
			}else if(e.getStateChange() == ItemEvent.SELECTED){
				model.setNextPage(PortfolioPages.PAGE_CREATE_FROM_NEW);
			}
		}	
	}
	
	public class NextCreateFromNewListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			model.setNextPage(PortfolioPages.PAGE_CREATE_FROM_NEW);
			model.goNextPage();
		}
	}
	
	//==============================================
		
	//======= Create portfolio from scratch ========
	public ItemListener getFromCloneListener(){
		return new FromCloneListener();
	}
	
	public class FromCloneListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getStateChange() == ItemEvent.DESELECTED){
				page.setEnabledPanelClone(false);
			}else if(e.getStateChange() == ItemEvent.SELECTED){
				page.setEnabledPanelClone(true);
			}
		}	
	}
		
	public class NextCreateFromCloneListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
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
