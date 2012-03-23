package database.jpa;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class JPAHelperTest {
	static JPAHelper jpaHelper;
	@BeforeClass
	public static void beforeClass(){ //First of all
		jpaHelper = new JPAHelper("testdb");
		
	}
	@Test
	public void testDuplicateEntry() {
		
	}
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}