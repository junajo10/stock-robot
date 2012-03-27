package simulation;

import database.jpa.IJPAHelper;
import database.jpa.JPAHelperForSimulator;
import robot.IRobot_Algorithms;

/**
 * @author Daniel
 *
 */
public class RobotSimulator implements IRobot_Algorithms {
	private JPAHelperForSimulator jpaHelper;
	
	public RobotSimulator(JPAHelperForSimulator jpaHelper) {
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
