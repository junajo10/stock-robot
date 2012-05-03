
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import robot.AstroRecieverTest;
import simulation.SimulationHandlerTest;
import utils.FinancialLongConverterTest;
import utils.PairTest;
import view.components.Item_cmb_PortfolioTest;

import database.jpa.JPATest;
import database.jpa.MainBasicJPATest;

/**
 * Class to wrap other unit test classes, so we can test everything we want to in one single run
 * 
 * @author kristian
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	FinancialLongConverterTest.class,
	PairTest.class,
	JPATest.class,
	MainBasicJPATest.class,
	SimulationHandlerTest.class,
	//SimulationRunnerTest.class
	Item_cmb_PortfolioTest.class,
	AstroRecieverTest.class
})

public class TestSuiteForEverything {
// nothing goes here	 
}