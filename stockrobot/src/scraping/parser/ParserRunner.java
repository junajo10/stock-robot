package scraping.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import scraping.database.IInserter;
import scraping.database.JPAInserter;
import scraping.model.ParserStock;
import scraping.scheduler.IScheduler;
import scraping.scheduler.Scheduler;

/**
 * Uses class AvanzaParser to parse a URL and inserts it
 * using class Inserter.
 * <p>
 * This class will run as a Thread. Harvester calls method stopRunner, to stop this Thread.
 * Or stopParser, to stop the parsing.
 * <p>
 *  
 * @author Erik
 *
 */
public class ParserRunner implements IParserRunner {
	
	boolean run = false;
	boolean close = false;
	AvanzaParser parser;
	IScheduler scheduler;
	IInserter inserter;
	
	public ParserRunner(){
		parser = new AvanzaParser();
		inserter = new JPAInserter();
		scheduler = new Scheduler();
		
	}
	@Override
	public void run() {
		Map<URL, String> avanzaStockMarkets = new HashMap<URL, String>();
		try {
			avanzaStockMarkets.put(new URL("https://www.avanza.se/aza/aktieroptioner/kurslistor/kurslistor.jsp?cc=SE&lkey=LargeCap.SE"), "LargeCap");
			avanzaStockMarkets.put(new URL("https://www.avanza.se/aza/aktieroptioner/kurslistor/kurslistor.jsp?cc=SE&lkey=MidCap.SE"), "MidCap");
			avanzaStockMarkets.put(new URL("https://www.avanza.se/aza/aktieroptioner/kurslistor/kurslistor.jsp?cc=SE&lkey=SmallCap.SE"), "SmallCap");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		while(!close){
			while(run){
				
				//Should run right now?
				if( scheduler.shouldRun() ) {
					ArrayList<ParserStock> stockList = null;
					Long timeBefore = System.currentTimeMillis();
					for(URL url : avanzaStockMarkets.keySet()){
						stockList = parser.parse(url, avanzaStockMarkets.get(url));			
					    inserter.insertStockData(stockList);
					}
					Long timeElapsed = System.currentTimeMillis() - timeBefore;
					System.out.println("Parsing loop done in: " +timeElapsed + " ms.");
					try {
						Thread.sleep(60000-timeElapsed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			while(!run){
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	/**
	 *  Stops the Runner. 
	 *  It will end the parsing loop before stopping.
	 *  <p>
	 *  @return true is it was successfully close
	 *  Otherwise false.
	 */
	public boolean stopRunner() {
		if(!close){
			close = true;
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *  Stops the parser.
	 *  This method is thread-safe. So it wont stop immediately,
	 *  it will wait until parser finishes a loop and then it will stop.
	 *  
	 */
	public boolean stopParser() {
		if(run){
			run = false;
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *  Starts the parser. 
	 *  By default parser is not running.
	 *  
	 */
	public boolean startParser() {
		if(!run){
			run = true;
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 *  Checks if the parser is running.
	 *  <p>
	 *  @return True
	 *  if the parser it running and not closing.
	 *  <p>
	 *  Otherwise false.
	 */
	@Override
	public boolean status() {
		if(run && !close){
			return true;
		}
		else {
			return false;
		}
	}
	
}