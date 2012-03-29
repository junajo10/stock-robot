package database.jpa;

import static org.junit.Assert.*;

import org.junit.Test;

import database.jpa.tables.AlgorithmSetting;
import database.jpa.tables.AlgorithmSettings;

public class testGenericJPA {

	@Test
	public void test() {
		IJPAHelper jpaHelper = JPAHelper.getInstance();
		
		
		AlgorithmSettings as = new AlgorithmSettings(jpaHelper.getAllPortfolios().get(0), jpaHelper.getAllAlgorithms().get(0));
		jpaHelper.storeObject(as);
		
		AlgorithmSetting<Integer> int1 = new AlgorithmSetting<Integer>(as, 123, "apa");
		jpaHelper.storeObject(int1);
		
		AlgorithmSetting<String> str1 = new AlgorithmSetting<String>(as, "asd", "apa");
		jpaHelper.storeObject(str1);

		System.out.println("apa");
		for (AlgorithmSetting set : jpaHelper.getSettings(as)) {
			System.out.println(set);
		}
		jpaHelper.remove(int1);
		jpaHelper.remove(str1);
		jpaHelper.remove(as);
	}


}