package gui.controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.portfolio.wizard.PassablePortfolioBuilder;

import view.PortfolioWizardView;


public class PortfolioWizardContoller {

	private PortfolioWizardView view;
	private PassablePortfolioBuilder portfolioBuilder;
	
	private NextCreateFromNewListener nextFromNewListener;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					PassablePortfolioBuilder portfoloioBuilder = new PassablePortfolioBuilder(); 
					PortfolioWizardView frame = new PortfolioWizardView(portfoloioBuilder,null);
					PortfolioWizardContoller controller = new PortfolioWizardContoller(frame, portfoloioBuilder);

					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public PortfolioWizardContoller(PortfolioWizardView view, PassablePortfolioBuilder portfolioBuilder){
		
		this.portfolioBuilder = portfolioBuilder;
		this.view = view;
		nextFromNewListener = new NextCreateFromNewListener();
		
		hookGui();
		
	}
	
	public void hookGui(){
		view.setCancelListener(new CancelListener());
		view.getPageStart().setCreateFromNewListener(new FromNewListener());
		view.getPageStart().setCreateFromCloneListener(new FromCloneListener());
		this.portfolioBuilder.addAddObserver(view);
	}
	
	
	
	public class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			view.setVisible(false);
			view.dispose();
		}	
	}
	
	//======= Create portfolio from scratch ========
	public class FromNewListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.DESELECTED){
				view.removeNextListener(nextFromNewListener);
			}else if(e.getStateChange() == ItemEvent.SELECTED){
				view.addNextListener(nextFromNewListener);
			}
		}	
	}
	
	public class NextCreateFromNewListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			view.loadPage(PortfolioWizardView.PAGE_PORTFOLIO_NEW);
			//view.setTitle("herp");
		}
	}
	//==============================================
	
	//======= Create portfolio from scratch ========
	public class FromCloneListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if(e.getStateChange() == ItemEvent.DESELECTED){
				view.getPageStart().setEnabledPanelClone(false);
			}else if(e.getStateChange() == ItemEvent.SELECTED){
				view.getPageStart().setEnabledPanelClone(true);
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
