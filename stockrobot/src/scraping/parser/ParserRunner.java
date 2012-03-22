package scraping.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import scraping.database.IInserter;
import scraping.database.Inserter;
import scraping.model.ParserStock;
import scraping.scheduler.IScheduler;

/**
 * Parser runner.
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
		inserter = new Inserter();
		//scheduler = new Scheduler();
		
	}
	@Override
	public void run() {
		ArrayList<URL> avanzaURLList = new ArrayList<URL>();
		try {
			avanzaURLList.add(new URL("https://www.avanza.se/aza/aktieroptioner/kurslistor/kurslistor.jsp?cc=SE&lkey=LargeCap.SE"));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		while(!close){
			while(run){
				ArrayList<ParserStock> stockList = null;
				Long timeBefore = System.currentTimeMillis();
				for(URL url : avanzaURLList){
					stockList = parser.parse(url);
				    inserter.insertStockData(stockList.toArray());

				}
				Long timeElapsed = System.currentTimeMillis() - timeBefore;
				System.out.println("Parsing done in:" +timeElapsed + " ms.");
				for(ParserStock stock : stockList){
					System.out.println(stock);
				}
				try {
					Thread.sleep(60000-timeElapsed);
				} catch (InterruptedException e) {
					e.printStackTrace();
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

	/** Pause the parser until run() is called. */
	@Override
	public void hold() {
		//TODO: Delete?
		run = false;
	}
	
	/**
	 *  Stops the Runner
	 */
	public boolean stopRunner() {
		//TODO: Delete?
		if(!close){
			close = true;
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *  Stops the parser
	 *  
	 */
	public boolean stopParser() {
		//TODO: Delete?
		if(run){
			run = false;
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 *  Stops the parser
	 *  
	 */
	public boolean startParser() {
		//TODO: Delete?
		if(!run){
			run = true;
			return true;
		}
		else{
			return false;
		}
	}
	
}