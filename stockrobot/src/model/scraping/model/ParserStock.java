package model.scraping.model;

import java.util.Date;

/**
 * Class representing a parsed stock from Avanza.
 * 
 * @author Erik,kristian
 * 
 */
public class ParserStock {
	
	private String	name;
	private String 	market = "";
	private int 		volume;
	private long	lastClose;
	private long 	buy;
	private long 	sell;
	private Date	date;
	
	public ParserStock(String name){
		this.name = name;
	}
		
	@Override
	public boolean equals(Object o){
		return name.equals((String) o);
	}
	
	@Override 
	public String toString(){
		return "Stock: " + name + " Buy: " + buy + " Sell: " + sell + " Volume: " + volume;
	}
	
	//setters
	public void setName( 		String _name ) 		{ name = _name; }
	public void setMarket( 		String _market ) 	{ market = _market; }
	public void setVolume( 		int _volume ) 		{ volume = _volume; }
	public void setLastClose( 	long _lastClose ) { lastClose = _lastClose; }
	public void setBuy( 		long _buy ) 		{ buy = _buy; }
	public void setSell( 		long _sell ) 		{ sell = _sell; }
	public void setDate( 		Date _date ) 		{ date = _date; }
	
	//getters
	public String	getName() 		{ return name; }
	public String	getMarket() 	{ return market; }
	public int		getVolume() 	{ return volume; }
	public long		getLastClose()	{ return lastClose; }
	public long		getBuy() 		{ return buy; }
	public long		getSell() 		{ return sell; }
	public Date		getDate() 		{ return date; }
}