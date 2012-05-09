package model.scraping.parser;

import java.beans.PropertyChangeSupport;

/**
 * @author Erik
 *
 * This interface will be the base of the running parser.
 * The class extending IParser should run as a thread.
 */
public interface IParserRunner extends Runnable {
	
	public void run();
	public boolean stopParser();
	public boolean stopRunner();
	public boolean startParser();
	public boolean status();
	public void setPropertyChangeSupport(PropertyChangeSupport pcs);
}
