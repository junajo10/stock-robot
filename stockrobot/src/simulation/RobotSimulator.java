package simulation;

import database.jpa.IJPAHelper;
import database.jpa.JPAHelperSimulator;
import robot.IRobot_Algorithms;
import trader.ITrader;

/**
 * @author Daniel
 *
 */
public class RobotSimulator implements IRobot_Algorithms {
	private IJPAHelper jpaSimHelper;
	private ITrader trader;
	
	public RobotSimulator() {
		jpaSimHelper = new JPAHelperSimulator();
		trader = new TraderSimulator2(jpaSimHelper);
	}
	
	@Override
	public boolean reportToUser(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IJPAHelper getJPAHelper() {
		return jpaSimHelper;
	}

	@Override
	public ITrader getTrader() {
		return trader;
	}

	

}
