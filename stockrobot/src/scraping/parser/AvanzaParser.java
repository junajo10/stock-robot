package scraping.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

import scraping.model.ParserStock;


/**
 * 
 * 
 * A parser made specifically for the Avanza homepage.
 * 
 * @author Erik
 * 
 * 
 */
public class AvanzaParser implements IParser {
	
	String market; 
	
	/** Parses the given Avanza-URL
	 * @param URL to be parsed */
	public ArrayList<ParserStock> parse(URL url, String market){
			this.market = market;
			URLConnection connection;
			HTMLDocument htmlDoc;
			ArrayList<ParserStock> stockList = new ArrayList<ParserStock>();
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
		private ArrayList<ParserStock> stockList;
		boolean startNewStock = false;
		String lastInput = "";
		private ParserStock stock;
		int counter = 0;
		
		public void setStockList(ArrayList<ParserStock> stockList){
			this.stockList = stockList;
		}
		public ArrayList<ParserStock> getStockList(){
			return stockList;
		}
		
		private String replaceAvanzaCharacters( String input ) {
			return input.replaceAll("ö","š").replaceAll("Ö","…").replaceAll("Å","").replaceAll("ä","Š").replaceAll("å","Œ");
		}
		
		/**Method is called once the parser finds a String in the given HTML-page.
		  */
		@Override
		public void handleText(char[] data, int pos) {
			String input = new StringBuffer().append(data).toString();
			
			if(input.equals("SŠlj")){
				startNewStock = false;
				lastInput = input; 
				//System.out.print("Hittat Sälj!");
			}
			else if(input.length()==1 && lastInput.equals("SŠlj")){
				startNewStock = true;
				lastInput = input;
			}
			else if(startNewStock){
				if(counter==0){
					stock = new ParserStock(replaceAvanzaCharacters(input));
					counter++;
				}
				else if(counter==1 || counter==2){
					//Skip data.
					counter++;
				}
				else if(counter==3){
					input = input.replace(',', '.');
					stock.setBuy(Double.valueOf(input));
					counter++;
				}
				else if(counter==4){
					input = input.replace(',', '.');
					stock.setSell(Double.valueOf(input));
					counter++;
				}
				else if(counter>4 && counter<8){
					counter++;
				}
				else if(counter==8){
					stock.setVolume(Integer.valueOf(input));
					counter++;
				}
				else if(counter ==9){
					stock.setMarket(market);
					stock.setDate(getDate(input));
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
	
	private Date getDate( String time ) {
		
		Date d = new Date();
		
		Calendar cal = new GregorianCalendar();
		cal.set(d.getYear() + 1900, 
				d.getMonth(), 
				d.getDate(), 
				Integer.parseInt(time.substring(0,2)), 
				Integer.parseInt(time.substring(3,5)), 
				0);
		
		//System.out.println( "getDate:::" + cal.getTime() );
		
		return cal.getTime();
	}

}
