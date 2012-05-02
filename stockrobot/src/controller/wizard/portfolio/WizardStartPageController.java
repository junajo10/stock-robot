package controller.wizard.portfolio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import model.wizard.WizardModel;


import view.wizard.WizardView;
import view.wizard.portfolio.PortfolioPages;
import view.wizard.portfolio.PortfolioStartPage;

public class WizardStartPageController {

	private PortfolioStartPage page;
	private WizardView wizard;
	private WizardModel model;
	
	public WizardStartPageController(PortfolioStartPage page, WizardView wizard, WizardModel model) {
		this.page = page;
		this.wizard = wizard;
		this.model = model;
	}
	
	//======= Create portfolio from scratch ========
	public ItemListener getFromNewListener(){
		
		ItemListener listener = new FromNewListener();
		
		
		return listener;
	}
	
	public class FromNewListener implements ItemListener{

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
}
