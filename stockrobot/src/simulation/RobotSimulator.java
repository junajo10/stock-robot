package simulation;

import robot.IRobot_Algorithms;

/**
 * @author Daniel
 *
 */
public class RobotSimulator implements IRobot_Algorithms {
	private static IRobot_Algorithms instance = null;
	
	public static IRobot_Algorithms getInstance() {
		if (instance == null) {
			synchronized (RobotSimulator.class) {
				if (instance == null)
					instance = new RobotSimulator();
			}
		}
		return instance;
	}
	
	@Override
	public boolean reportToUser(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
