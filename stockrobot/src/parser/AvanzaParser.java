package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;


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
		try {
			avanzaURLList.add(new URL("https://www.avanza.se/aza/aktieroptioner/kurslistor/kurslistor.jsp?cc=SE&lkey=LargeCap.SE"));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		while(true){
			while(run){
				Long timeBefore = System.currentTimeMillis();
				ArrayList<String> stockNames;
				for(URL url : avanzaURLList){
					stockNames = getStockNames(url);
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
		//TODO: Delete?
		run = false;
	}
	
	/** Parses the given Avanza-URL
	 * @param URL to be parsed */
	public void parse(URL url){
		//TODO: Implement the parse function.
			URLConnection connection;
			HTMLDocument htmlDoc;
			try {
				connection = url.openConnection();
			    InputStream is = connection.getInputStream();
			    InputStreamReader isr = new InputStreamReader(is);
			    BufferedReader br = new BufferedReader(isr);
			    HTMLEditorKit htmlKit = new HTMLEditorKit();
			    htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
			    HTMLEditorKit.Parser parser = new ParserDelegator();
			    AvanzaCallback AvanzaCb = new AvanzaCallback();
			    parser.parse(br, AvanzaCb, true);
				updateStocks(currentStock);

			} catch (IOException e1) {
				e1.printStackTrace();
			}


	}
	
	public ArrayList<String> getStockNames(URL url){
		ArrayList<String> stocks = new ArrayList<String>();
		return null;
		
	}
	
	private class AvanzaCallback extends ParserCallback {

		/**Method is called once the parser finds a String in the given URL
		 * @param String data
		 * @param Position of the char[]
		 */
		@Override
		public void handleText(char[] data, int pos) {
			String input = new StringBuffer().append(data).toString();
			
		}
	}
	

}
