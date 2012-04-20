package model.utils;

import java.util.HashMap;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: Log.java
 * Description:
 * Log will be used take care of outprints to the terminal. Different filter values can be set
 * if the user only wants to see a specific type of messages
 */
public class Log {

	private static Log log = null;
	
	public static enum TAG{
		NORMAL,
		VERBOSE,
		VERY_VERBOSE,
		DEBUG,
		ERROR,
	}
	
	private HashMap<TAG, Boolean> filter;
	private HashMap<TAG, String> shortenerMap;
	
	public Log() {
		initialize();
	}
	
	public void log(TAG tag, String message){
	
		if(filter.get(tag)){
			System.out.print("[" + shortenerMap.get(tag) + "] " );
			System.out.print(message);
			System.out.println();
		}
	}
	
	private void initialize(){
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
	public void setFilter(Log.TAG tag, boolean shouldPrint){
		
		if(tag == TAG.VERY_VERBOSE&& shouldPrint){
			filter.put(TAG.VERBOSE, true);
			filter.put(TAG.VERY_VERBOSE, true);
		}else if(tag == TAG.VERBOSE&& !shouldPrint){
			filter.put(TAG.VERBOSE, false);
			filter.put(TAG.VERY_VERBOSE, false);
		}else{
			filter.put(tag, shouldPrint);
		}
	}
	
	public static Log instance(){
		
		if(log == null){
			synchronized(Log.class){
				if(log == null){
					log = new Log();
				}
			}
		}
		
		return log;
	}
	
	
}
