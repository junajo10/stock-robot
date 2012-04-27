package controller.wizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.wizard.WizardModel;

import view.wizard.WizardView;


public class WizardContoller {
	
	
	public static ActionListener getCancelListener(WizardView view){
		
		CancelListener listener = new CancelListener(view);
		
		return listener;
	}
	
	public static class CancelListener implements ActionListener{

		private WizardView view;
		
		public CancelListener(WizardView view) {
			
			this.view = view;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			view.setVisible(false);
			view.dispose();
		}	
	}
	
	public static ActionListener getBackListener(WizardModel model){
		
		ActionListener listener = new BackPageListener(model);
		
		return listener;
	}
	
	public static class BackPageListener implements ActionListener{

		private WizardModel model;
		
		public BackPageListener(WizardModel model) {
			
			this.model 	= model;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(model.getBackPage() != null){
				model.goBackPage();
			}
		}	
	}
	
	public static ActionListener getNextListener(WizardModel model){
		
		ActionListener listener = new NextPageListener(model);
		
		return listener;
	}
	
	public static class NextPageListener implements ActionListener{

		private WizardModel model;
		
		public NextPageListener(WizardModel model) {
			
			this.model 	= model;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(model.getNextPage() != null){
				model.goNextPage();
			}
		}	
	}
}
