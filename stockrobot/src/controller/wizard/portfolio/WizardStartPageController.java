package controller.wizard.portfolio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

import controller.gui.IController;

import model.wizard.WizardModel;
import model.wizard.portfolio.PortfolioWizardModel;


import view.wizard.WizardView;
import view.wizard.portfolio.PortfolioPages;
import view.wizard.portfolio.PortfolioStartPage;

public class WizardStartPageController implements IController {

	private PortfolioStartPage page;
	private WizardView wizard;
	private WizardModel model;
	
	public WizardStartPageController(PortfolioStartPage page, WizardView wizard, WizardModel model) {
		this.wizard = wizard;
		this.model = model;
	}
	
	//======= Create portfolio from scratch ========
	public ItemListener getFromNewListener(){
		
		ItemListener listener = new FromNewListener();
		
		
		return listener;
	}
	
	public class FromNewListener implements ItemListener{

		@SuppressWarnings("unused") //Suppressed this warning in preparation for the meeting tomorrow 
		private NextCreateFromNewListener nextBtnListener; 
		
		public FromNewListener(){
			
			nextBtnListener = new NextCreateFromNewListener();
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
			
			wizard.loadScreen(PortfolioPages.PAGE_CREATE_FROM_NEW);
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
		
		if(model instanceof PortfolioWizardModel){
			
		}
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
}
