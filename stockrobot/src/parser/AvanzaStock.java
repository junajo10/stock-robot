package parser;

import java.sql.Date;

import scraping.database.PriceDataRepresentation;

/**
 * 
 * @author Erik
 * 
 * Class representing a parsed stock from Avanza.
 *
 */
public class AvanzaStock /*extends PriceDataRepresentation*/ {
	public String	name;
	public int 		volume;
	public double 	latest;
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
		return name;
	}
}
