package controller.wizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.wizard.WizardView;


public class WizardContoller {
	
	
	private WizardView view;

	public WizardContoller(WizardView view){
		
		this.view = view;
	}
	
	public ActionListener getCancelListener(){
		return new CancelListener();
	}
	
	public class CancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			view.setVisible(false);
			view.dispose();
		}	
	}
	
	
	
	
	
}
