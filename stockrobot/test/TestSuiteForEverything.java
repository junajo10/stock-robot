import generic.FinancialLongConverterTest;
import generic.PairTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

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
})

public class TestSuiteForEverything {
// nothing goes here	 
}