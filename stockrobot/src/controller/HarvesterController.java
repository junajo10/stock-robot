package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


import java.sql.Timestamp;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ListModel;


import utils.AbstractWindowCloseAdapter;
import view.HarvesterView;

import model.scraping.core.Harvester;
import model.scraping.core.HarvesterLog;

/**
 * Controller for Harvester.
 * @author Erik
 *
 */
public class HarvesterController implements IController {
	
	private final Harvester model;
	private final HarvesterView view;
	private final Logger log;	//NOPMD
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
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
				log.failPortNumber(view.getPortTextbox());
			}
		}
	}
	
	private class StopBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			log.stop();
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
			log.printStatus(model.status());
		}
	}

	private class ExportLogBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			log.exportLog();
		}
	}
	private class ClearLogBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			view.clearLog();
		}
	}

	public HarvesterController() {
		this.log = new Logger();
		this.view = new HarvesterView();
		this.model = new Harvester(45000);
		model.setPropertyChangeSupport(pcs);
		pcs.addPropertyChangeListener(this);
	}
	
	private class Logger {
		long totalLoops = 0;
		int connected = 0;

		public void failPortNumber(String portTextbox) {
			addToList(portTextbox + " is not a valid port-number. ");
		}

		public void printStatus(boolean status){
			if(status){
				addToList("Parser is up and running. ");
				addToList("Total parsing loops done: " + totalLoops);
				addToList("Number of connected to server: " + connected);
			}
			else {
				addToList("Parser closed,crashed or shutting down.");
			}
		}
		
		public void start(){
			addToList("Parser initializing.");
		}		
		
		public void serverUp(){
			addToList("Server is up and accepting connections.");
		}	
		
		public void parsingLoop(long timeElapsed){
			totalLoops++;
			addToList("Parsing loop finished in " + timeElapsed + " ms. ");
		}		
		
		public void stop(){
			addToList("Parser starting to shutdown.");
		}
		
		public void finishStopped(){
			addToList("Parser shutdown complete.");
		}
		
		public void showDownServer(){
			addToList("Shutting down server.");
		}

		public void failStart() {
			addToList("Parser failed to start. Already started or crashed.");
		}
		
		public void connected(String hostname) {
			connected++;
			addToList(hostname + " has connected to Harvester.");
		}
		
		public void disconnected(String hostname) {
			connected--;
			addToList(hostname + " has disconnected from Harvester.");
		}
		
		public void addText(String text) {
			addToList(text);
		}
		
		private void addToList(String input){
			Date date= new java.util.Date();
			String time = new Timestamp(date.getTime()) + "";
			view.addLogItem("[" + time.substring(11, 19) + "] - " + input);
		}
		
		public void exportLog(){
			File logTxtFile = view.openChooseDirectory();
			if (logTxtFile != null) {
				ListModel model = view.getLogModel();
				PrintStream out = null;
				try {
					out = new PrintStream(new FileOutputStream(logTxtFile));
			        int len = model.getSize(); 
			        for(int i = 0; i < len; i++) { 
			        	out.println(model.getElementAt(i).toString()); 
			        } 
					addToList("Log exported to "+logTxtFile.getAbsolutePath());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}  finally {
					if (out != null)
						out.close();
				}
			}
        } 
	}
	

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals(HarvesterLog.PARSING_DONE)){
			log.parsingLoop((Long) event.getNewValue());
		}
		
		if(event.getPropertyName().equals(HarvesterLog.PARSING_PROGRESS)){
			view.setParserBarProgress((Integer) event.getNewValue());
		}
		
		if(event.getPropertyName().equals(HarvesterLog.CONNECTED)){
			log.connected((String) event.getNewValue());
		}
		
		if(event.getPropertyName().equals(HarvesterLog.DISCONNECTED)){
			log.disconnected((String) event.getNewValue());
		}
		
		if(event.getPropertyName().equals(HarvesterLog.TEXT)){
			log.addText((String) event.getNewValue());
		}
		
		if(event.getPropertyName().equals(HarvesterLog.SHUTDOWN)){
			log.showDownServer();
		}
		
		if(event.getPropertyName().equals(HarvesterLog.SERVER_UP)){
			log.serverUp();
		}
		
		if(event.getPropertyName().equals(HarvesterLog.SERVER_DOWN)){
			log.finishStopped();
		}
	}

	@Override
	public void display(Object model) {
		view.addActions(getActionListeners());
		view.display(this.model);
	}

	@Override
	public void cleanup() {
		model.stopParser();
		view.cleanup();
	} 

	@Override
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

	@Override
	public void defineSubControllers() {} //NOPMD

	@Override
	public String getName() {
		return "Harvester Controller";
	}
}