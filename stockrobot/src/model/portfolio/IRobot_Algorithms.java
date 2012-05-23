package model.portfolio;

import model.database.jpa.IJPAHelper;
import model.trader.ITrader;

/**
 * Interface to the robot from an algorithm
 * @author Daniel
 */
public interface IRobot_Algorithms {
	/**
	 * Gets the current IJPAHelper
	 * @return the current IJPAHelper
	 */
	IJPAHelper getJPAHelper();
	
	/**
	 * Gets the current ITrader
	 * @return the current ITrader
	 */
	ITrader getTrader();
}
