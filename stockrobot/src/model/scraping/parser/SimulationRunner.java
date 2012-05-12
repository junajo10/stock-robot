package model.scraping.parser;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import model.database.jpa.IJPAParser;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;
import model.scraping.connect.Connector;
import model.scraping.connect.IConnector;
import model.scraping.database.IInserter;
import model.scraping.database.JPAInserter;
import model.scraping.scheduler.IScheduler;
import model.scraping.scheduler.Scheduler;

/**
 * 
 * @author Daniel
 */
public class SimulationRunner implements IParserRunner {

	boolean run = false;
	boolean close = false;

	IScheduler scheduler;
	IInserter inserter;
	IConnector connector;
	IJPAParser jpaHelper = JPAHelper.getInstance();
	private List<StockNames> simulatedStocks = new ArrayList<StockNames>();
	Random rand = new Random(System.currentTimeMillis());
	private PropertyChangeSupport pcs;
	
	public SimulationRunner(int port){
		this.connector = new Connector(port);
		inserter = new JPAInserter();
		scheduler = new Scheduler();
	}
	@Override
	public void run() {
		boolean alreadyExists = false;
		
		for (StockNames s : jpaHelper.getAllStockNames()) {
			if (s.getName().contains("sim stock")) {
				alreadyExists = true;
				simulatedStocks.add(s);
			}
		}
		if (!alreadyExists) {
			for (int i = 1; i <= 10; i++) {
				simulatedStocks.add(new StockNames("sim stock" + i, "Market" + i%3, true));
				jpaHelper.storeObject(simulatedStocks.get(i-1));
			}
		}
		
		while(!close) {
			Long timeBefore = System.currentTimeMillis();
			for (StockNames sn : simulatedStocks) {
				StockPrices sp = new StockPrices(sn, rand.nextInt(100000000), rand.nextInt(100000000), rand.nextInt(100000000), rand.nextInt(100000000), new Date(System.currentTimeMillis()));
				jpaHelper.storeObjectIfPossible(sp);
			}
			
			connector.sendDataAvailable(10);
			Long timeElapsed = System.currentTimeMillis() - timeBefore;
			pcs.firePropertyChange("Parsing done.", null, timeElapsed);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
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
			connector.shutdown();
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
			connector.shutdown();
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
	@Override
	public void setPropertyChangeSupport(PropertyChangeSupport pcs) {
		this.pcs = pcs;
	}
	
}
