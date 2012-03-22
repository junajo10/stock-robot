package database.jpa;



import java.util.List;

import database.jpa.tables.AlgorithmsTable;
import database.jpa.tables.PortfolioTable;


public class MainBasicJPATest {
	public static void main(String[] args) {

		JPAHelper jpaHelper = new JPAHelper();
		
		jpaHelper.initJPASystem();
		
		
		List<AlgorithmsTable> algorithms = jpaHelper.getAllAlgorithms();
		
		if (algorithms.size() == 0) {
			jpaHelper.storeObject(new AlgorithmsTable("hej", "ho"));
			algorithms = jpaHelper.getAllAlgorithms();
		}
		
		for (AlgorithmsTable a : algorithms) {
			//a.setName(a.getName() + "1");
			//jpaHelper.updateObject(a);
			System.out.println(a);
			
		}
		
		
		List<PortfolioTable> portfolios = jpaHelper.getAllPortfolios();
		
		if (portfolios.size() == 0) {
			jpaHelper.storeObject(new PortfolioTable("portfolio 1"));
			portfolios = jpaHelper.getAllPortfolios();
		}
		
		for (PortfolioTable p : portfolios) {
			jpaHelper.investMoney(100, p);
			System.out.println(p);
		}
		
		
		jpaHelper.stopJPASystem();
		
	}

}
