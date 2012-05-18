package utils.global;

import java.util.HashMap;

import javax.swing.DefaultListModel;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: Log.java
 * Description:
 * Log will be used take care of outprints to the terminal. Different filter values can be set
 * if the user only wants to see a specific type of messages
 */
public final class Log {

	private static Log log = new Log();
	
	public static enum TAG{
		NORMAL,
		VERBOSE,
		VERY_VERBOSE,
		DEBUG,
		ERROR,
	}
	
	private HashMap<TAG, Boolean> filter;
	private HashMap<TAG, String> shortenerMap;
	private DefaultListModel listModel;
	
	private Log() {
		initialize();
	}
	
	public static void log(TAG tag, String message){
	
		instance().addLogMessage(message);
		/*if(log.filter.get(tag)){
			System.out.print("[" + log.shortenerMap.get(tag) + "] " ); //NOPMD
			System.out.print(message); //NOPMD
			System.out.println(); //NOPMD
		}*/
	}
	
	private void addLogMessage(String message) {
		listModel.addElement(message);
	}
	
	public DefaultListModel getModel(){
		return listModel;
	}

	private void initialize(){
		listModel = new DefaultListModel();
		filter=new HashMap<Log.TAG, Boolean>();
		filter.put(TAG.NORMAL, true);
		filter.put(TAG.VERBOSE, false);
		filter.put(TAG.VERY_VERBOSE, false);
		filter.put(TAG.DEBUG, false);
		filter.put(TAG.ERROR, true);
		
		shortenerMap=new HashMap<Log.TAG, String>();
		shortenerMap.put(TAG.NORMAL, "N");
		shortenerMap.put(TAG.VERBOSE, "V");
		shortenerMap.put(TAG.VERY_VERBOSE, "VV");
		shortenerMap.put(TAG.DEBUG, "D");
		shortenerMap.put(TAG.ERROR, "E");
	}
	
	/**
	 * Set filter lets the user set which messages should be printed
	 * 
	 * @param tag the tag to be effected
	 * @param shouldPrint true if it should be printed else false
	 */
	public static void setFilter(Log.TAG tag, boolean shouldPrint){
		
		if(tag == TAG.VERY_VERBOSE&& shouldPrint){
			log.filter.put(TAG.VERBOSE, true);
			log.filter.put(TAG.VERY_VERBOSE, true);
		}else if(tag == TAG.VERBOSE&& !shouldPrint){
			log.filter.put(TAG.VERBOSE, false);
			log.filter.put(TAG.VERY_VERBOSE, false);
		}else if (tag == TAG.NORMAL && shouldPrint){
			log.filter.put(TAG.VERBOSE, false);
			log.filter.put(TAG.VERY_VERBOSE, false);
		}else if (tag == TAG.DEBUG && shouldPrint){
			log.filter.put(TAG.DEBUG, true);
			log.filter.put(TAG.NORMAL, true);
			log.filter.put(TAG.ERROR, true);
		}else {
			log.filter.put(tag, true);
		}
		
	}

	public static Log instance(){
		
		return log;
	}
	
	
}
