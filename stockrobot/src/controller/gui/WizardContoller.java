package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;


import model.wizard.WizardModel;

import utils.WindowCloseAdapter;
import view.wizard.WizardView;


public class WizardContoller implements IController {
	
	public static final String CLASS_NAME = "WizardContoller";
	
	private WizardModel model;
	private WizardView view;
	
	private Map<String, EventListener> actions;
	
	public WizardContoller(){
		
		model = new WizardModel();
		view = new WizardView(model);
		model.addAddObserver(view);
		view.setEnableCancel(true);
		
		actions = new HashMap<String,EventListener>();
		actions.put(WizardView.GO_NEXT, getNextListener());
		actions.put(WizardView.GO_BACK, getBackListener());
		actions.put(WizardView.GO_CANCEL, getCancelListener());
		actions.put(WizardView.WINDOW_CLOSE, windowClose);
		view.addActions(actions);
	}
	
	public ActionListener getCancelListener(){
		CancelListener listener = new CancelListener(view);
		
		return listener;
	}
	
	public class CancelListener implements ActionListener{

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
	
	public ActionListener getBackListener(){
		
		ActionListener listener = new BackPageListener();
		
		return listener;
	}
	
	public class BackPageListener implements ActionListener{
		
		public BackPageListener() {
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(model.getBackPage() != null){
				model.goBackPage();
			}
		}	
	}
	
	public WizardView getView(){
		
		return view;
	}
	
	public ActionListener getNextListener(){
		
		ActionListener listener = new NextPageListener();
		
		return listener;
	}
	
	public class NextPageListener implements ActionListener{
		
		public NextPageListener() {
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(model.getNextPage() != null){
				model.goNextPage();
			}
		}	
	}

	WindowListener windowClose = new WindowCloseAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			cleanup();
			
		}
	};
	
	public WizardModel getModel() {
		
		return model;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(Object model) {
		
		view.setVisible(true);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, EventListener> getActionListeners() {
		
		return actions;
	}

	@Override
	public void addSubController(IController subController) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void defineSubControllers() {
		
	}

	@Override
	public String getName() {

		return CLASS_NAME;
	}
}
