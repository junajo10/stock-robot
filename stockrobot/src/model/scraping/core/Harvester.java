package model.scraping.core;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import model.scraping.parser.IParserRunner;
import model.scraping.parser.ParserRunner;
import model.scraping.parser.SimulationRunner;

/**
 * Harvester model.
 * <p>
 * Takes commands from a controller.
 * <p>
 * @param args
 * @author Erik
 */
public class Harvester {
	private Thread parserThread;
	private IParserRunner parserRunner;
	private int serverPort = 45000;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	
	public Harvester(int portNumber){
		serverPort = portNumber;
	}
		
	public boolean startSimulation() {
		if(!status()){
			parserRunner = new SimulationRunner(serverPort, pcs);
			parserThread = new Thread(parserRunner);
			parserThread.start();
			parserRunner.startParser();
			return true;
		}
		return false;

	}

	/**
	 * Force a start of the parser thread.
	 * @return True if thread started.
	 * <p>
	 * Otherwise false.
	 */
	public boolean startParser(){
		if(!status()){
			parserRunner = new ParserRunner(serverPort, pcs);
			parserThread = new Thread(parserRunner);
			parserThread.start();
			parserRunner.startParser();
			return true;
		}
		return false;
	}
	
	
	/**
	 * Force the parser to stop, without waiting 
	 * for it to complete a parsing loop.
	 * <p>
	 * @return True if the parser was running and was successfully stopped.
	 * <p>
	 * Otherwise false.
	 */
	@SuppressWarnings("deprecation")
	public boolean forceStop(){
		if(parserThread==null){
			return false;
		}
		else if(parserThread.isAlive()){
			parserThread.stop();
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Stops the parser temporarily.
	 * <p>
	 * @return True if the parser was running and was successfully stopped.
	 * <p>
	 * Otherwise false.
	 */
	public boolean stopParser(){
		if(status()){
				
			if(parserThread==null){
				return false;
			}
			else if(parserThread != null){
				parserRunner.stopParser();
				return true;
			}
			else{
				return false;
			}

		}
		return false;
	}
	
	/**
	 * Checks if the parser is running.
	 * <p>
	 * @return True if the parser is running
	 * <p>
	 * Otherwise false.
	 */
	public boolean status(){
		
		if(parserThread==null){
			return false;
		}
		else if(parserThread != null && parserRunner != null && parserThread.isAlive()){
			return parserRunner.status();
		}
		else{
			return false;
		}
	}
	
	/**
	 * Sets the given port of the Connector.
	 * <p>
	 * Warning! A restart of the parser must be done in order
	 * for the port change to take effect.
	 * <p>
	 * @param port number to use.
	 */
	public boolean setPort(int port) {
		if(port>2000 && port<65536){
			serverPort = port;
			return true;
		}
		return false;
	}


	public void stopRunner() {
		parserRunner.stopRunner();
	}

	public void addObserver(PropertyChangeListener observer) {
		 pcs.addPropertyChangeListener(observer);
	}
	
	public PropertyChangeSupport getObservers(){
		return pcs;
	}

}
