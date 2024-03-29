package model.scraping.parser;

import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.scraping.connect.Connector;
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
	
	private boolean run = false;
	private boolean close = false;
	private int portNr;
	
	private AvanzaParser parser;
	private IScheduler scheduler;
	private IInserter inserter;
	private IConnector connector;
	
	private boolean skipScheduler = false;
	private PropertyChangeSupport pcs;
	
	public ParserRunner(int PORT_NR, PropertyChangeSupport pcs){
		this.portNr = PORT_NR;
		this.pcs = pcs;
		//this.server = new HarvesterServer(port);
		parser = new AvanzaParser();
		inserter = new JPAInserter();
		scheduler = new Scheduler();
	}
	
	/**
	 * This method is here only for unit testing purposes, skipping the shouldRun call
	 */
	public void setSkipScheduler() {
		
		skipScheduler = true;
	}
	
	@Override
	public void run() {
		Map<URL, String> avanzaStockMarkets = new HashMap<URL, String>();
		Long timeElapsed = null;
		int progress = 13000;
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
				if( scheduler.shouldRun() || skipScheduler ) {
					if(timeElapsed!=null){
						progress = (15000-timeElapsed.intValue());
						pcs.firePropertyChange("Parsing Progress.", null,progress);

					}
					else {
						pcs.firePropertyChange("Parsing Progress.", null, 5000);
					}
					ArrayList<ParserStock> stockList = new ArrayList<ParserStock>();
					Long timeBefore = System.currentTimeMillis();
					
					for(URL url : avanzaStockMarkets.keySet()){
						stockList.addAll(parser.parse(url, avanzaStockMarkets.get(url)));
						progress = progress + 1500;
						pcs.firePropertyChange("Parsing Progress.", null, progress);
					}
					int newRows = inserter.insertStockData(stockList);
					
					timeElapsed = System.currentTimeMillis() - timeBefore;
					/* Send a message to the robot saying that new data is available.
					 * Reciever on robot not implemented yet, uncomment when. */
					connector.sendDataAvailable(newRows);

					pcs.firePropertyChange("Parsing Progress.", null, 20000);
					pcs.firePropertyChange("Parsing done.", null , timeElapsed);
					if(timeElapsed < 20000){
						try {
							Thread.sleep(500);
							pcs.firePropertyChange("Parsing Progress.", null, 0);
							Thread.sleep(500);
							pcs.firePropertyChange("Parsing Progress.", null, 100);
							Thread.sleep(4000);
							pcs.firePropertyChange("Parsing Progress.", null, 5000);
							Thread.sleep(1000);
							pcs.firePropertyChange("Parsing Progress.", null, 6000);
							Thread.sleep(15000-timeElapsed);
							
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				else {
					try {
						String SleepTime = "Sleep for: " + scheduler.timeUntilNextParse()/1000/60/60 + "hours and " + ((scheduler.timeUntilNextParse()/1000/60)%60) + "minutes";
						pcs.firePropertyChange("Text.", null, SleepTime);
						Thread.sleep(scheduler.timeUntilNextParse());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			while(!run){
				try {
					Thread.sleep(3000);
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
	@Override
	public boolean stopRunner() {
		if(!close){
			close = true;
			connector.shutdown();
			while(connector.isRunning());
			connector = null;
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
	@Override
	public boolean stopParser() {
		if(run){
			run = false;
			connector.shutdown();
			while(connector.isRunning()){
				
			}
			connector = null;
			pcs.firePropertyChange("Stopped successfull.", null, null);
			connector = null;
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
	@Override
	public boolean startParser() {
		if(!run){
			run = true;
			connector = new Connector(portNr, pcs);
			connector.startConnector();
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
		if(run && !close && connector.isRunning()){
			return true;
		}
		else {
			return false;
		}
	}
	
}