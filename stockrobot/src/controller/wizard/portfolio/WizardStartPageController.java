package controller.wizard.portfolio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


import view.wizard.portfolio.PortfolioStartPage;

public class WizardStartPageController {

	private PortfolioStartPage page;
	
	public WizardStartPageController(PortfolioStartPage page) {
		this.page = page;
	}
	
	//======= Create portfolio from scratch ========
	public ItemListener getFromNewListener(){
		return new FromNewListener();
	}
	
	public class NextCreateFromNewListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class FromNewListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getStateChange() == ItemEvent.DESELECTED){
			}else if(e.getStateChange() == ItemEvent.SELECTED){
			}
			
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
