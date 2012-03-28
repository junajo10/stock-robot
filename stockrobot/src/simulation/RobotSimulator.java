package simulation;

import database.jpa.IJPAHelper;
import robot.IRobot_Algorithms;

/**
 * @author Daniel
 *
 */
public class RobotSimulator implements IRobot_Algorithms {
	private IJPAHelper jpaHelper;
	
	public RobotSimulator(IJPAHelper jpaHelper) {
		this.jpaHelper = jpaHelper;
	}
	
	@Override
	public boolean reportToUser(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IJPAHelper getJPAHelper() {
		return jpaHelper;
	}

	

}
