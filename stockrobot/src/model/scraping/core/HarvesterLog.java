package model.scraping.core;


import java.sql.Timestamp;
import java.util.Date;

import javax.swing.DefaultListModel;

/**
 * Class for controlling the Log in the Harvester GUI.
 * @author Erik
 *
 */
public class HarvesterLog {
	long totalLoops = 0;
	int connected = 0;
	DefaultListModel model;
	static HarvesterLog log;
	
	public static HarvesterLog getInstance(){
		if(log==null){
			log = new HarvesterLog();
			return log;
		}
		return log;
	}

	public HarvesterLog(){
		model = new DefaultListModel();
	}
	
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
		addToList("Parser started at 08:56.");
	}		
	
	public void parsingLoop(long timeElapsed){
		totalLoops++;
		addToList("Parsing loop finished in " + timeElapsed + " ms. ");
	}		
	
	public void stop(){
		addToList("Parser stopped at 08:59.");
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
	
	private void addToList(String input){
		Date date= new java.util.Date();
		String time = new Timestamp(date.getTime()) + "";
		model.addElement("[" + time.substring(11, 19) + "] - " + input);
	}
	
	public DefaultListModel getListModel(){
		return model;
	}
	
	public void clearListModel(){
		model.clear();
	}
}
