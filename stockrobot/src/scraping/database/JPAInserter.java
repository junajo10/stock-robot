package scraping.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.print.attribute.IntegerSyntax;

import database.jpa.JPAHelper;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;

import scraping.model.ParserStock;

public class JPAInserter implements IInserter {
	
	EntityManagerFactory factory;
	EntityManager em;
	JPAHelper helper;
	
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
		helper.initJPASystem();
	}
	
	@Override
	public boolean insertStockData(List<ParserStock> s) {
		
		//Create entityFactory
		Map<Object,Object> factoryMap 	= new HashMap<Object, Object>(); 
		
		//Create manager
		factory							= Persistence.createEntityManagerFactory( "astroportfolio", factoryMap );
		em 								= factory.createEntityManager();
		
		//For all stock data sent here
		for( ParserStock stock : s ) {
			
			//Get a list of all stocknames so we can see if the current
			List<StockNames> allStockNames = helper.getAllStockNames();
			
			boolean foundMatch = false;
			
			//Is the currently processed stock already in the database?
			for( StockNames stockFromDB : allStockNames ) {
				
				// A match has been found
				if( stockFromDB.getName().equals( stock.getName() ) ) {
					
					//Create a new stock for the price
					StockPrices priceTable = new StockPrices(	stockFromDB,
																stock.getVolume(),
																(int)stock.getLastClose(),
																(long)stock.getBuy(),
																(long)stock.getSell(),
																stock.getDate()
															);
					
					try {
						
						helper.updateObject(stockFromDB);
                        
					} catch( Exception e) {
					
						e.printStackTrace();
					}
                    
					try {
						
						//Create transaction for inserting a new stock price
						helper.storeObject( priceTable );
						
					} catch( Exception e ) {
						
						e.printStackTrace();
						System.out.println( "JPAInserter: insert stockData: Trouble adding a new price" );
					}
					
					foundMatch = true;
					
					break; //Jump out of this loop
				}
			}
			
			//If not, we have to add it to the database
			if( !foundMatch ) {
			
				//Create a new stock in the nametable and a new price
				StockNames newNameTable = new StockNames( stock.getName(), stock.getMarket() );
				StockPrices priceTable  = new StockPrices( 
					
					newNameTable,
					stock.getVolume(),
					(int)stock.getLastClose(),
					(long)stock.getBuy(),
					(long)stock.getSell(),
					stock.getDate()
				);
				
				//Add the new stock and a price to DB
				try {
					
					em.getTransaction().begin();
					em.persist(newNameTable);
					em.persist(priceTable);
					em.getTransaction().commit();
				
				} catch( Exception e ) {
					
					//e.printStackTrace();
					System.out.println( "JPAInserter: insert stockData: Trouble adding a new stock" );
				}
			}
		}
		
		em.close();
		
		factory.close();
		
		//Everything seems to have worked
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