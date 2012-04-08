import generic.FinancialLongConverterTest;
import generic.PairTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import database.jpa.JPAHelperForUnderstanding;
import database.jpa.JPATest;
import database.jpa.MainBasicJPATest;
import database.jpa.testJPAPortfolioSettings;
import database.jpa.testJPASlice;

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
	JPAHelperForUnderstanding.class,
	JPATest.class,
	MainBasicJPATest.class,
	testJPAPortfolioSettings.class,
	testJPASlice.class,
})

public class TestSuiteForEverything {
// nothing goes here	 
}