package scraping.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import scraping.model.ParserStock;

public class ParserRunner implements IParserRunner {
	
	boolean run = true;
	AvanzaParser parser = new AvanzaParser();
	
	@Override
	public void run() {
		run = true;
		ArrayList<URL> avanzaURLList = new ArrayList<URL>();
		try {
			avanzaURLList.add(new URL("https://www.avanza.se/aza/aktieroptioner/kurslistor/kurslistor.jsp?cc=SE&lkey=LargeCap.SE"));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		while(true){
			while(run){
				ArrayList<ParserStock> stockList = null;
				Long timeBefore = System.currentTimeMillis();
				for(URL url : avanzaURLList){
					stockList = parser.parse(url);
				    //DB.insertNewStockData(stockList);

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
}