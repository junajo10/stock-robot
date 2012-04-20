package generic;

import java.util.Random;

import junit.framework.Assert;


import org.junit.Test;

import utils.global.Pair;


public class PairTest {
	Random rand = new Random(System.currentTimeMillis());
	// Just a stupid little test of the Pair
	@Test
	public void test() {
		int left = rand.nextInt();
		int right = rand.nextInt();
		Pair<Integer, Integer> testPair = new Pair<Integer, Integer>(left, right);
		
		Assert.assertEquals(left, (int)testPair.getLeft());
		Assert.assertEquals(right, (int)testPair.getRight());
	}

}
