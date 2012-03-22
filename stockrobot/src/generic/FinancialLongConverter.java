package generic;

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
	
	public static final int decimalLength = 6;
	
	/*
	public static void main( String[] args ) {
		
		System.out.println( Long.toString( fromFloat((float)3429.473286876) ) );
		System.out.println( Long.toString( fromDouble((float)3429.473286876) ) );
	}*/
	
	/**
	 * Float -> Long
	 * 
	 * @param input
	 * @return
	 */
	public static long fromFloat( float input ) {
		
		String dec = Float.toString( input );
		
		String numbers= dec.split( "\\." )[0];
		String decimals = dec.split( "\\." )[1];
		
		//Add as many zero's as needed to get decimalLength decimals
		int limit = decimalLength - decimals.length();
		for( int i = 0; i < limit; i ++ )
			decimals += "0";
		
		return Long.parseLong( numbers + decimals );
	}
	
	/**
	 * Float -> Long
	 * 
	 * @param input
	 * @return
	 */
	public static long fromDouble( double input ) {
		
		String dec = Double.toString( input );
		
		String numbers= dec.split( "\\." )[0];
		String decimals = dec.split( "\\." )[1];
		
		//Add as many zero's as needed to get decimalLength decimals
		int limit = decimalLength - decimals.length();
		for( int i = 0; i < limit; i ++ )
			decimals += "0";
		
		return Long.parseLong( numbers + decimals );
	}
}