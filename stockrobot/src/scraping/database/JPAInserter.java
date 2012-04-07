package scraping.database;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import database.jpa.IJPAHelper;
import database.jpa.IJPAParser;
import database.jpa.JPAHelper;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;

import scraping.model.ParserStock;

public class JPAInserter implements IInserter {
	
	EntityManagerFactory factory;
	EntityManager em;
	IJPAHelper helper;
	
	/*
	public static void main( String[] args ) {
		
		JPAInserter ins 		= new JPAInserter();
		List<ParserStock> test 	= new ArrayList<ParserStock>();
		
		ParserStock ps			= new ParserStock("apa");
		ps.setDate( new Date(293333292) );
		//ps.setDate( "2012-02-02 20:20:21" );
		
		test.add( ps );
		ins.insertStockData( test );
	}*/
	
	public JPAInserter() {
		
		//Instantiate helper
		helper = JPAHelper.getInstance();
	}
	
	@Override
	public boolean insertStockData(List<ParserStock> stocks) {
		IJPAParser jpaParserHelper = JPAHelper.getInstance();
		
		
		Map<String, StockPrices> latestMap = jpaParserHelper.getLatestMap(); 
		int newStockPrices = 0;
		List<StockPrices> newStocks = new LinkedList<StockPrices>();
		Stack<StockNames> newStockNames = new Stack<StockNames>();
		
		for (ParserStock s : stocks) {
			if (s.getBuy() != 0 && s.getSell() != 0) {
				if (!latestMap.containsKey(s.getName())) {
					//StockName doesn't exist.
					newStockNames.push(new StockNames(s.getName(), s.getMarket()));
					newStocks.add(new StockPrices(newStockNames.peek(), s.getVolume(), s.getLastClose(), s.getBuy(), s.getSell(), s.getDate()));
					newStockPrices++;

				}
				else {
					StockPrices latest = latestMap.get(s.getName());

					if (!latest.getTime().equals(s.getDate())) {
						newStocks.add(new StockPrices(latest.getStockName(), s.getVolume(), s.getLastClose(), s.getBuy(), s.getSell(), s.getDate()));
						newStockPrices++;
					}
				}
			}
		}
		
		if (!newStockNames.isEmpty()) {
			try {
				jpaParserHelper.storeListOfObjects(newStockNames);
			} catch (Exception e) {
				// For some reason there is an identical stock name, so store those that are possible
				// using the slower store function.
				jpaParserHelper.storeListOfObjectsDuplicates(newStockNames);
			}
		}
		
		try {
			jpaParserHelper.storeListOfObjects(newStocks);
		} catch (Exception e) {
			// For some reason there is an identical stock price, so store those that are possible
			// using the slower store function.
			jpaParserHelper.storeListOfObjectsDuplicates(newStocks);
		}
		
		return true;
	}
	
	@Override
	public boolean updateAllMarkets(List<ParserStock> s) {
		
		//Create entityFactory
		Map<Object,Object> factoryMap 	= new HashMap<Object, Object>(); 
				
		//Create manager
		factory							= Persistence.createEntityManagerFactory( "astropricelist", factoryMap );
		em 								= factory.createEntityManager();
		
		//Get a list of all current stock names
		List<StockNames> allStockNames = helper.getAllStockNames();
		
		for( StockNames stockName : allStockNames ) {
			
			//If something should replace this one:
			for( ParserStock ps : s ) {
				
				if( ps.getMarket().equals( stockName.getMarket() ) ) {
					
					stockName.setMarket( ps.getMarket() );
					
					//Replace the market in stockName with the market in ps
					helper.updateObject( stockName );
				}
			}
		}
		
		return true;
	}	
}