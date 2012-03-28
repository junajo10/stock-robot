package robot;

import database.jpa.IJPAHelper;

// Interface to the framework from algorithms
public interface IRobot_Algorithms {
	/**
	 * Used when an algorithm want to say something to the user.
	 * @param message The message that the user will recive
	 * @return True when user has read the message
	 */
	boolean reportToUser(String message);

	IJPAHelper getJPAHelper();
}
