package database.jpa;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class JPAHelperTest {

	@BeforeClass
	public static void beforeClass(){ //First of all
	System.out.println("Before class");
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}

/*
import static org.junit.Assert.*;
//Possible add this row
@Test
public void testAdd() {
List l = new List();
l.add(1);
assertTrue(l.getLength() == 1); // The logical check
}
*/