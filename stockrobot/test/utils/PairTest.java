package utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import junit.framework.Assert;


import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import utils.global.Pair;

/**
 * Test the Pair class with some values to make sure it give's the expected objects back
 * 
 * @author kristian
 *
 */
@RunWith( value = Parameterized.class )
public class PairTest {
	
	private Object insertLeft;
	private Object insertRight;
	private Object compareLeft;
	private Object compareRight;
	private boolean shouldBeTrue;
	
	/**
	 * Constructor, send the test objects here 
	 * 
	 * @param insertLeft, what to add to left of a pair
	 * @param insertRight, what to add to left of a pair
	 * @param compareLeft, what to compare with the left
	 * @param compareRight, what to compare with the right
	 * @param shouldBeTrue, what the expected result should be
	 */
	public PairTest( Object insertLeft, Object insertRight, Object compareLeft, Object compareRight, boolean shouldBeTrue ) {
		
		this.insertLeft		= insertLeft;
		this.insertRight	= insertRight;
		this.compareLeft	= compareLeft;
		this.compareRight	= compareRight;
		this.shouldBeTrue	= shouldBeTrue;
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

		//Creating some dummy data
		StockNames stName = new StockNames( "ABB", "LargeCap", true );
		StockPrices prices = new StockPrices( stName, 3434343, 24242492, 244424242, 224229292, new Date(10101001));
		StockPrices pricesAnotherInstance = new StockPrices( stName, 3434343, 24242492, 244424242, 224229292, new Date(10101001)); //Same content as above
		
		//Creating a little tree structure
		Pair<Pair<String,Pair<String,Integer>>,String> treeStructure = new Pair<Pair<String, Pair<String, Integer>>, String>(
			
			new Pair<String,Pair<String,Integer>>("somethingElse",
					
						new Pair<String,Integer>("Hej",3)
					
					),
				
			"stringToTest"
		);
		
		Object[][] data = new Object[][] {

			//	_insertLeft, 	_insertRight,	_compareLeft,	_compareRight, 				_shouldBeTrue
				{23.323, 		43,				23.323,			43,							true	}, //Test a simple case with a float as left and an int as right
				{23.323, 		45.000001,		23.323,			45,							false	}, //Testing for roundoff errors
				{prices,		stName,			prices,			stName,						true	}, //Testing a more advanced data type than float
				{prices,		stName,			stName,			prices,						false	}, //Testing so that pair does not mix up left and right
				{stName,		prices,			stName,			pricesAnotherInstance,		false	}, //Same as above, but making sure pricesAnotherInstance instance won't get mixed up with prices
																									   //even though they have the same content
				{"str1",		"str2",			"str1",			"str1",						false	}, //Testing strings
				{"str14",		"1str2",		"str14",		"1str2",					true	}, //Testing strings
				{true,			false,			true,			false,						true	}, //Testing booleans
				
				//Testing the tree structure
				{treeStructure, treeStructure.getRight(), treeStructure, "stringToTest",	true	}, //Does the getRight work when another Pair is in the left?
				{treeStructure.getLeft().getLeft(), treeStructure.getLeft().getRight(), "somethingElse", treeStructure.getLeft().getRight(),	true	}, //Does the getRight work when another Pair is in the left?
				{treeStructure.getLeft().getRight().getLeft(), treeStructure.getLeft().getRight().getRight(), "Hej", 3,	true	}, //Picking something out from a sub-pair's subpair
				
		};

		return Arrays.asList( data );
	}
	
	/**
	 * test
	 * 
	 * <p>
	 * Check to see if (_insertLeft == _compareLeft && _insertRightm =_compareLeft) == _shouldBeTrue
	 * </p>
	 * 
	 */
	@Test
	public void test() {
		
		Pair<Object, Object> testPair = new Pair<Object, Object>( insertLeft, insertRight );
		
		Assert.assertTrue(
			( 	testPair.getLeft().equals( compareLeft ) && 
				testPair.getRight().equals( compareRight ) ) 
				== shouldBeTrue
		);
	}
}