package scraping.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
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
				ArrayList<AvanzaStock> stockList = null;
				Long timeBefore = System.currentTimeMillis();
				for(URL url : avanzaURLList){
					stockList = parse(url);
				    //DB.insertNewStockData(stockList);

				}
				Long timeElapsed = System.currentTimeMillis() - timeBefore;
				System.out.println("Parsing done in:" +timeElapsed + " ms.");
				for(AvanzaStock stock : stockList){
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
	
	/** Parses the given Avanza-URL
	 * @param URL to be parsed */
	private ArrayList<AvanzaStock> parse(URL url){
			URLConnection connection;
			HTMLDocument htmlDoc;
			ArrayList<AvanzaStock> stockList = new ArrayList<AvanzaStock>();
			try {
				connection = url.openConnection();
			    InputStream is = connection.getInputStream();
			    InputStreamReader isr = new InputStreamReader(is);
			    BufferedReader br = new BufferedReader(isr);
			    HTMLEditorKit htmlKit = new HTMLEditorKit();
			    htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
			    HTMLEditorKit.Parser parser = new ParserDelegator();
			    AvanzaCallback avanzaCb = new AvanzaCallback();

			    avanzaCb.setStockList(stockList);
			    parser.parse(br, avanzaCb, true);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return stockList;


	}
	
	/**
	 * 
	 * @author Erik
	 * A private class of which the AvanzaParser uses to parse a URL.
	 */
	private class AvanzaCallback extends ParserCallback {
		private ArrayList<AvanzaStock> stockList;
		boolean startNewStock = false;
		String lastInput = "";
		private AvanzaStock stock;
		int counter = 0;
		
		public void setStockList(ArrayList<AvanzaStock> stockList){
			this.stockList = stockList;
		}
		public ArrayList<AvanzaStock> getStockList(){
			return stockList;
		}

		/**Method is called once the parser finds a String in the given HTML-page.
		  */
		@Override
		public void handleText(char[] data, int pos) {
			String input = new StringBuffer().append(data).toString();
			if(input.equals("Sälj")){
				startNewStock = false;
				lastInput = input; 
				//System.out.print("Hittat Sälj!");
			}
			else if(input.length()==1 && lastInput.equals("Sälj")){
				startNewStock = true;
				lastInput = input;
			}
			else if(startNewStock){
				if(counter==0){
					stock = new AvanzaStock(input);
					counter++;
				}
				else if(counter==1 || counter==2){
					//Skip data.
					counter++;
				}
				else if(counter==3){
					input = input.replace(',', '.');
					stock.buy = Double.valueOf(input);
					counter++;
				}
				else if(counter==4){
					input = input.replace(',', '.');
					stock.sell = Double.valueOf(input);
					counter++;
				}
				else if(counter>4 && counter<8){
					counter++;
				}
				else if(counter==8){
					stock.volume = Integer.valueOf(input);
					counter++;
				}
				else if(counter ==9){
					stockList.add(stock);
					counter=0;
					startNewStock = false;
				}
			}
			else{
				startNewStock = false;
				lastInput = input;
			}
		}
	}
	

}
