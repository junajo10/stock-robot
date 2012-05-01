package model.scraping.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.scraping.connect.Connector;
import model.scraping.connect.HarvesterServer;
import model.scraping.connect.IConnector;
import model.scraping.database.IInserter;
import model.scraping.database.JPAInserter;
import model.scraping.model.ParserStock;
import model.scraping.scheduler.IScheduler;
import model.scraping.scheduler.Scheduler;


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
	IConnector connector;
	HarvesterServer server;
	
	public ParserRunner(int port){
		this.server = new HarvesterServer(port);
		parser = new AvanzaParser();
		inserter = new JPAInserter();
		scheduler = new Scheduler();
		connector = new Connector();
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
					ArrayList<ParserStock> stockList = new ArrayList<ParserStock>();
					Long timeBefore = System.currentTimeMillis();
					for(URL url : avanzaStockMarkets.keySet()){
						stockList.addAll(parser.parse(url, avanzaStockMarkets.get(url)));
					}
					int newRows = inserter.insertStockData(stockList);
					
					Long timeElapsed = System.currentTimeMillis() - timeBefore;
					/* Send a message to the robot saying that new data is available.
					 * Reciever on robot not implemented yet, uncomment when. */
					connector.sendDataAvailable(newRows);
					System.out.println("Parsing loop done in: " +timeElapsed + " ms.");
					if(timeElapsed < 20000){
						try {
							Thread.sleep(20000-timeElapsed);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				else {
					try {
						System.out.println("Sleep for: " + scheduler.timeUntilNextParse()/1000/60/60 + "hours and " + ((scheduler.timeUntilNextParse()/1000/60)%60) + "minutes");
						Thread.sleep(scheduler.timeUntilNextParse());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			while(!run){
				try {
					Thread.sleep(30000);
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