package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import utils.AbstractWindowCloseAdapter;
import view.HarvesterView;
import model.scraping.core.Harvester;


/**
 * Controller for Harvester.
 * @author Erik
 *
 */
public class HarvesterController implements IController {
	
	private final Harvester model;
	private final HarvesterView view;
	private boolean shutdownOnClose = true;
	
	WindowListener windowClose = new AbstractWindowCloseAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			cleanup();
		}
	};
	
	private class StartBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				
				int port = Integer.parseInt(view.getPortTextbox());
				model.setPort(port);
				
				if(view.simulateStocksChecked()){
					if(model.startSimulation()){
						view.start();
						view.setStartInactive();
						view.setStopActive();
					}
					else {
						view.failStart();
					}
				}
				else {
					if(model.startParser()){
						view.start();
						view.setStartInactive();
						view.setStopActive();
					}
					else {
						view.failStart();
					}
				}
			} catch (NumberFormatException e) {
				view.failPortNumber(view.getPortTextbox());
			}
		}
	}
	
	private class StopBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.stop();
			if(!view.forceStopChecked()){
				model.stopParser();
			}
			else {
				model.forceStop();
			}
			view.setStopInactive();
			view.setStartActive();
		}
	}
	
	private class StatusBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.printStatus(model.status());
		}
	}

	private class ExportLogBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.exportLog();
		}
	}
	private class ClearLogBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.clearLog();
		}
	}

	public HarvesterController() {
		this.view = new HarvesterView();
		this.model = new Harvester(45000);
	}
	
	@Override
	public void display(Object model) {
		if (model != null) {
			shutdownOnClose  = false;
		}
		view.addActions(getActionListeners());
		view.display(this.model);
	}

	@Override
	public void cleanup() {
		if (shutdownOnClose) {
			model.stopParser();
			view.cleanup();
		}
	} 

	public Map<String, EventListener> getActionListeners() {
		Map<String, EventListener> actions = new HashMap<String,EventListener>();
		actions.put(HarvesterView.START_PARSER, new StartBtnListener());
		actions.put(HarvesterView.STOP_PARSER, new StopBtnListener());
		actions.put(HarvesterView.PRINT_STATUS, new StatusBtnListener());
		actions.put(HarvesterView.CLEAR_LOG, new ClearLogBtnListener());
		actions.put(HarvesterView.EXPORT_LOG, new ExportLogBtnListener());
		actions.put(HarvesterView.WINDOW_CLOSE, windowClose);

		return actions;
	}
}