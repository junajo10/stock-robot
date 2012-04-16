package gui.controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.view.PortfolioWizardView;

public class PortfolioWizardContoller {

	private PortfolioWizardView view;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PortfolioWizardView frame = new PortfolioWizardView();
					PortfolioWizardContoller controller = new PortfolioWizardContoller(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public PortfolioWizardContoller(PortfolioWizardView view){
		
		this.view = view;
		hookGui();
	}
	
	public void hookGui(){
		view.setCancelListener(new CancelListener());
	}
	
	public class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			view.setVisible(false);
			view.dispose();
		}	
	}
	
}
