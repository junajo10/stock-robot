package parser;

import java.net.URL;
import java.util.ArrayList;

/**
 * @author Erik
 *
 * A parser made specifically for the Avanza homepage.
 * 
 * 
 */
public class AvanzaParser implements IParser {
	boolean run = true;
	@Override
	public void run() {
		run = true;
		ArrayList<URL> avanzaURLList = new ArrayList<URL>();
		ArrayList<String> stocks = new ArrayList<String>();
		
		while(true){
			while(run){
				Long timeBefore = System.currentTimeMillis();
				for(URL url : avanzaURLList){
					parse(url);
				}
				Long timeElapsed = System.currentTimeMillis() - timeBefore;
				try {
					Thread.sleep(60000-timeElapsed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** Pause the parser until run() is called. */
	@Override
	public void hold() {
		run = false;
	}
	
	/** Parses the given Avanza-URL
	 * @param URL to be parsed */
	public void parse(URL url){
		
	}

}
