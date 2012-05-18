package utils;

import java.util.Arrays;
import java.util.Collection;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import utils.global.FinancialLongConverter;

/**
 * Test class for the financial long converter
 * 
 * @author kristian
 *
 */
@RunWith( value = Parameterized.class )
public class FinancialLongConverterTest {
	
	private double 	convertToLong;
	private long 	testAgainst;
	private boolean expectedResult;
	
	/**
	 * Constructor, OMG!
	 * 
	 * @param convertToLong		Some string that should be converted to a long
	 * @param testAgainst		What we want to test the converted long against 
	 * @param expectedResult	What we expect the result to be
	 */
	public FinancialLongConverterTest( double convertToLong, long testAgainst, boolean expectedResult  ) {
		
		this.convertToLong 	= convertToLong;
		this.testAgainst 	= testAgainst;
		this.expectedResult = expectedResult;
	}
	
	/**
	 * These parameters will create new instances of this class, 
	 * the good thing is we can test a lot of different values without any need
	 * for multiple copy-pasted methods where the only difference is the numbers! 
	 * 
	 * @return
	 */
	@Parameters
	public static Collection<Object[]> data() {

		Object[][] data = new Object[][] {

			//	convertToLong, 	testAgainst,	expectedResult
				{23.32, 		23320000,		true	}, //Just convering a simple number
				{23.32, 		24244444,		false	}, //Clearly not right, should be false
				{231.233223, 	231233223,		true	}, //Testing a number with more decimals
				{0, 			0,				true	}, //Zero should not be changed to something else
				{-10, 			10000000,		false	}, //A negative number should not become positive
		};

		return Arrays.asList( data );
	}
	
	@Test
	public void test() {
		
		long converted = FinancialLongConverter.fromDouble( convertToLong );
		Assert.assertTrue( (testAgainst == converted) == expectedResult );
	}
}