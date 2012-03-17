package parser;

import java.sql.Date;

import scraping.database.PriceDataRepresentation;

/**
 * Class representing a parsed stock from Avanza.
 * 
 * @author Erik
 * 
 */
public class AvanzaStock /*extends PriceDataRepresentation*/ {
	public String	name;
	public int 		volume;
	public double 	buy;
	public double 	sell;
	public Date		date;
	
	public AvanzaStock(String name){
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
}
