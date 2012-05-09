package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import view.HarvesterViewMVC;

import model.scraping.core.Harvester;

/**
 * Controller for Harvester.
 * @author Erik
 *
 */
public class HarvesterControllerMVC implements IController {
	
	private Harvester model;
	private HarvesterViewMVC view;
	private Logger log;

	
	
	private class StartBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("apa");
			try {
				
				int port = Integer.parseInt(view.getPortTextbox());
				model.setPort(port);
				System.out.println("*** Server port set to: " + port);

				
				if(view.simulateStocksChecked()){
					if(model.startSimulation()){
						log.start();
						view.setStartInactive();
						view.setStopActive();
					}
					else {
						log.failStart();
					}
				}
				else {
					if(model.startParser()){
						log.start();
						view.setStartInactive();
						view.setStopActive();
					}
					else {
						log.failStart();
					}
				}
			} catch (NumberFormatException e) {
				System.out.println("*** Malformed portnumber");
			}
		}
	}
	
	private class StopBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("apa");
			if(view.forceStopChecked()){
				model.stopParser();
			}
			else {
				model.forceStop();
			}
			log.stop();
			view.setStopInactive();
			view.setStartActive();
		}
	}
	
	private class StatusBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("apa");
			log.printStatus(model.status());
		}
	}

	
	private class ClearLogBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.clearLog();
		}
	}

	public HarvesterControllerMVC() {
		this.log = new Logger();
		this.view = new HarvesterViewMVC();
		this.model = new Harvester(45000);
	}
	
	private class Logger {
		
		public Logger(){
			
		}
		
		public void printStatus(boolean status){
			if(status){
				view.addLogItem("Parser is up and running.");
			}
			else {
				view.addLogItem("Parser closed,crashed or shutting down.");
			}
		}
		public void start(){
			view.addLogItem("Parser started at 08:56.");
		}		
		
		public void stop(){
			view.addLogItem("Parser stopped at 08:59.");
		}

		public void failStart() {
			view.addLogItem("Parser failed to start. Already started or crashed.");
		}
	}




	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
	}


	@Override
	public void display(Object model) {
		view.addActions(getActionListeners());
		view.display(this.model);
	}


	@Override
	public void cleanup() {

	}


	@Override
	public Map<String, EventListener> getActionListeners() {
		Map<String, EventListener> actions = new HashMap<String,EventListener>();
		actions.put(HarvesterViewMVC.START_PARSER, new StartBtnListener());
		actions.put(HarvesterViewMVC.STOP_PARSER, new StopBtnListener());
		actions.put(HarvesterViewMVC.PRINT_STATUS, new StatusBtnListener());
		actions.put(HarvesterViewMVC.CLEAR_LOG, new ClearLogBtnListener());

		return actions;
	}


	@Override
	public void addSubController(IController subController) {
		
	}


	@Override
	public void defineSubControllers() {
		
	}


	@Override
	public String getName() {
		return "Harvester Controller";
	}
    

}
