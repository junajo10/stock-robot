package model.scraping.database;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import model.database.jpa.IJPAParser;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;
import model.scraping.model.ParserStock;



public class JPAInserter implements IInserter {
	
	private IJPAParser helper;
	
	
	private Map<String, StockPrices> latestMap = null;
	
	public JPAInserter() {
		
		//Instantiate helper
		helper = JPAHelper.getInstance();
	}
	public JPAInserter(IJPAParser jpaHelper) {
		
		//Instantiate helper
		helper = jpaHelper;
	}
	@Override
	public int insertStockData(List<ParserStock> stocks) {
		if (latestMap == null) {
			latestMap = helper.getLatestMap();
		}
		
		int newStockPrices = 0;
		List<StockPrices> newStocks = new LinkedList<StockPrices>();
		Stack<StockNames> newStockNames = new Stack<StockNames>();

		for (ParserStock s : stocks) {
			if (s.getBuy() != 0 && s.getSell() != 0) {
				if (!latestMap.containsKey(s.getName())) {
					//StockName doesn't exist.
					newStockNames.push(new StockNames(s.getName(), s.getMarket(), false));
					StockPrices sp = new StockPrices(newStockNames.peek(), s.getVolume(), s.getLastClose(), s.getBuy(), s.getSell(), s.getDate());
					newStocks.add(sp);
					newStockPrices++;
					latestMap.put(s.getName(), sp);
				}
				else {
					StockPrices latest = latestMap.get(s.getName());
					if (!latest.getTime().equals(s.getDate())) {
						StockPrices sp = new StockPrices(latest.getStockName(), s.getVolume(), s.getLastClose(), s.getBuy(), s.getSell(), s.getDate());
						newStocks.add(sp);
						latestMap.put(s.getName(), sp);
						newStockPrices++;
					}
				}
			}
		}
		boolean redoLatestMap = false;
		if (!newStockNames.isEmpty()) {
			try {
				helper.storeListOfObjects(newStockNames);
			} catch (Exception e) {
				// For some reason there is an identical stock name, so store those that are possible
				// using the slower store function.
				helper.storeListOfObjectsDuplicates(newStockNames);
				redoLatestMap = true;
			}
		}
		
		try {
			helper.storeListOfObjects(newStocks);
		} catch (Exception e) {
			// For some reason there is an identical stock price, so store those that are possible
			// using the slower store function.
			helper.storeListOfObjectsDuplicates(newStocks);
			redoLatestMap = true;
		}
		
		if (redoLatestMap) {
			latestMap = helper.getLatestMap();
		}
		
		return newStockPrices;
	}
	/*
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
	*/
}