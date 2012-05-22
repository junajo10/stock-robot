package testsuiteforeverything;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import database.jpa.JPATest;
import database.jpa.MainBasicJPATest;
import controller.wizard.portfolio.WizardFromNewPageControllerTest;
import portfolio.PortfolioHandlerTest;
import portfolio.PortfolioTest;
import robot.TraderTest;
import simulation.SimulationHandlerTest;
import utils.FinancialLongConverterTest;
import utils.PairTest;
import view.components.ItemCmbPortfolioTest;
import model.scraping.ParserStockTest;
import model.scraping.SchedulerTest;
import model.scraping.parser.AvanzaParserTest;
import model.wizard.WizardModelTest;
import model.wizard.WizardPageModelTest;

/**
 * Class to wrap other unit test classes, so we can test everything we want to in one single run
 * 
 * <p>
 * 
 * Right now JPA needs to have the -javaagent 
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
	//SimulationControllerTest.class, //TODO: Kristian: fix
	//GraphControllerTest.class,  //TODO: Kristian: fix
	JPATest.class,
	MainBasicJPATest.class,
	ItemCmbPortfolioTest.class,
	//AstroRecieverTest.class, //TODO: Kristian: fix
	ParserStockTest.class,
	PortfolioHandlerTest.class,
	TraderTest.class,
	PortfolioTest.class,
	//RobotSchedulerTest.class, //TODO: this test fails since we have changed the model.
	WizardFromNewPageControllerTest.class,
	WizardModelTest.class,
	WizardPageModelTest.class,
	SchedulerTest.class,
	AvanzaParserTest.class,
	//ParserRunnerTest.class,  //TODO: Kristian: fix
	//SimulationRunnerTest.class,  //TODO: Kristian: fix neverending loop
	SimulationHandlerTest.class,
})

public class TestSuiteForEverything {
	//All glory to the Hypnotoad!
}