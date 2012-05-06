package model.simulation;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelperSimulator;
import model.portfolio.IRobot_Algorithms;
import model.trader.ITrader;

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
	public IJPAHelper getJPAHelper() {
		return jpaSimHelper;
	}

	@Override
	public ITrader getTrader() {
		return trader;
	}

	

}
