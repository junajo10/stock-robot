package gui.mvc;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: Constants.java
 * Description:
 * The constants class holds constats that is used by the gui
 */
public class Constants {

	//The type of messages that can be sent by a IEvent
	public class EVENT_TYPE{
		
		//Stocks
		public static final String BUY_STOCK 			= "buyStock";
		public static final String SELL_STOCK 			= "sellStock";
		
		//Socket connections
		public static final String PRICES_UPDATED 		= "pricesUpdated";
	}
}
