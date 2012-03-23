package scraping.model;

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
	private double	lastClose;
	private double 	buy;
	private double 	sell;
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
		String str = "Aktie: " + name + " Köp: " + buy + " Sälj: " + sell + " volym: " + volume;
		return str;
	}
	
	//setters
	public void setName( 		String _name ) 		{ name = _name; }
	public void setMarket( 		String _market ) 	{ market = _market; }
	public void setVolume( 		int _volume ) 		{ volume = _volume; }
	public void setLastClose( 	double _lastClose ) { lastClose = _lastClose; }
	public void setBuy( 		double _buy ) 		{ buy = _buy; }
	public void setSell( 		double _sell ) 		{ sell = _sell; }
	public void setDate( 		Date _date ) 		{ date = _date; }
	
	//getters
	public String	getName() 		{ return name; }
	public String	getMarket() 	{ return market; }
	public int		getVolume() 	{ return volume; }
	public double	getLastClose() 	{ return lastClose; }
	public double	getBuy() 		{ return buy; }
	public double	getSell() 		{ return sell; }
	public Date		getDate() 		{ return date; }
}