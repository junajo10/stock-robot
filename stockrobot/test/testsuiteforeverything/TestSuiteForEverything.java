package testsuiteforeverything;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import controller.gui.MainMenuControllerTest;
import controller.gui.SimulationControllerTest;
import controller.wizard.WizardContoller;
import controller.wizard.WizardControllerTest;

import portfolio.PortfolioHandlerTest;
import portfolio.PortfolioTest;

import robot.AstroRecieverTest;
import robot.RobotSchedulerTest;
import robot.TraderTest;
import scraping.ParserStockTest;
import scraping.parser.SimulationRunnerTest;
import simulation.SimulationHandlerTest;
import utils.FinancialLongConverterTest;
import utils.PairTest;
import view.components.ItemCmbPortfolioTest;

import database.jpa.JPATest;
import database.jpa.MainBasicJPATest;

/**
 * Class to wrap other unit test classes, so we can test everything we want to in one single run
 * 
 * <p>
 * 
 * TODO: Right now JPA needs to have the -javaagent 
 * -javaagent:/PATH-TO-YOUR-OPENJPA2/stock-robot/stockrobot/jar/openjpa-all-2.2.0.jar
 * 
 * </p>
 * 
 * @author kristian
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	FinancialLongConverterTest.class,
	PairTest.class,
	MainMenuControllerTest.class,
	SimulationControllerTest.class,
	JPATest.class,
	MainBasicJPATest.class,
	SimulationHandlerTest.class,
	SimulationRunnerTest.class,
	ItemCmbPortfolioTest.class,
	AstroRecieverTest.class,
	ParserStockTest.class,
	PortfolioHandlerTest.class,
	TraderTest.class,
	PortfolioTest.class,
	RobotSchedulerTest.class,
	WizardControllerTest.class,
})

public class TestSuiteForEverything {
// nothing goes here	 
}