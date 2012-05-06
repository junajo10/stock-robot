package model.portfolio;

import model.database.jpa.IJPAHelper;
import model.trader.ITrader;

// Interface to the framework from algorithms
public interface IRobot_Algorithms {
	IJPAHelper getJPAHelper();
	
	ITrader getTrader();
}
