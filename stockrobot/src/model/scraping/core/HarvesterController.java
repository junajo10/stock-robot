package model.scraping.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for Harvester.
 * @author Erik
 *
 */
public class HarvesterController {
	
	private Harvester model;
	private HarvesterView view;
	private Logger log;



	public HarvesterController(Harvester model, HarvesterView view) {
		this.model = model;
		this.view = view;
		this.log = new Logger();
		
	    view.addbtnStartParserListener(new StartBtnListener());
	    view.addbtnStopParserListener(new StopBtnListener());
	    
	}
	
	private class Logger {
		
		public Logger(){
			
		}
		
		public void start(){
			view.addLogItem("Parser started at 08:56.");
		}		
		
		public void stop(){
			view.addLogItem("Parser stopped at 08:59.");
		}
	}

	private class StartBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				int port = Integer.parseInt(view.getPortTextbox());
				model.setPort(port);
				System.out.println("*** Server port set to: " + port);
				log.start();
				
				if(view.simulateStocksChecked()){
					model.startSimulation();
				}
				else {
					model.startParser();
				}
			} catch (NumberFormatException e) {
				System.out.println("*** Malformed portnumber");
			}
		}
	}
	
	private class StopBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.stopParser();
			log.stop();
		}
	}
	
	private class StatusBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.stopParser();
		}
	}

	
	private class ClearLogBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.stopParser();
		}
	}
    

}
