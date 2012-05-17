package utils.global;

import java.text.DecimalFormat;

/**
 * 
 * FinancialLongConverter
 * 
 * Instead of representing money as some kind of integral or float value, 
 * we store it as a long, where the last X digits are decimals.
 * 
 * This is to avoid unnecessary roundoff errors
 * 
 * @author kristian
 *
 */
public class FinancialLongConverter {
	
	public static final int DECIMALLENGTH = 6;
	
	/**
	 * Float -> Long
	 * 
	 * @param input
	 * @return
	 */
	public static long fromFloat( float input ) {
		
		String dec = Float.toString( input );
		
		String numbers = "";
		String decimals = "";
		int limit = 0;
		
		//If we have a large number, java's tostring will insert E^X to make it shorter, then add as many zero's as needed
		if( dec.contains("E") ) {
			
			numbers = dec.split( "E" )[0];
			limit = Integer.parseInt(dec.split( "E" )[1]) - dec.split("\\.")[1].split("E")[0].length();
			
		//Otherwise, add as many zero's as needed 
		} else {
			
			numbers = dec.split( "\\." )[0];
			decimals = dec.split( "\\." )[1];
			
			//Add as many zero's as needed to get decimalLength decimals
			limit = DECIMALLENGTH - decimals.length();
		}
		
		for( int i = 0; i < limit; i ++ ) {
			
			decimals += "0";
		}
		
		return Long.parseLong( numbers + decimals );
	}
	
	/**
	 * Float -> Long
	 * 
	 * The conversion of E^X isn't the most elegant solution.
	 * 
	 * @param input
	 * @return
	 */
	public static long fromDouble( double input ) {
		
		String dec = Double.toString( input );
		
		String numbers = "";
		String decimals = "";
		int limit = 0;
		
		//If we have a large number, java's tostring will insert E^X to make it shorter, then add as many zero's as needed
		if( dec.contains("E") ) {
			
			numbers = dec.split( "E" )[0];
			limit = Integer.parseInt(dec.split( "E" )[1]) - dec.split("\\.")[1].split("E")[0].length();
			
		//Otherwise, add as many zero's as needed 
		} else {
			
			numbers = dec.split( "\\." )[0];
			decimals = dec.split( "\\." )[1];
			
			//Add as many zero's as needed to get decimalLength decimals
			limit = DECIMALLENGTH - decimals.length();
		}
		
		for( int i = 0; i < limit; i ++ ) {
			
			decimals += "0";
		}
		
		return Long.parseLong( (numbers + decimals).replaceAll("\\.", "") );
	}
	public static double toDouble(long input) {
		return input/(Math.pow(10, DECIMALLENGTH));
	}
	public static String toStringTwoDecimalPoints(long input) {
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(toDouble(input));
	}
	public static long toFinancialLong(long input) {
		return (long) (input * Math.pow(10, DECIMALLENGTH));
	}
}