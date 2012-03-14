package scraping.database;

import java.sql.Date;

/**
 * Data abstraction class to standardize interesting information from any given stock information source.
 * 
 * @author kristian
 */
public class PriceDataRepresentation {
	
	private final int 				_volume;
	private final float 			_high;
	private final float 			_low;
	private final float 			_latest;
	private final float 			_buy;
	private final float 			_sell;
	private final Date				_date;
	
	//Cons
	public PriceDataRepresentation( int 	volume, 
									float 	high, 
									float 	low,
									float 	latest,
									float 	buy,
									float 	sell,
									Date	date ) {
		
		_volume = volume;
		_high	= high;
		_low	= low;
		_latest = latest;
		_buy	= buy;
		_sell 	= sell;
		_date	= date;
	}
	
	//Getters
	public int 		getVolume() { return _volume; }
	public float	getHigh() 	{ return _high; }
	public float	getLow() 	{ return _low; }
	public float	getLatest() { return _latest; }
	public float	getBuy() 	{ return _buy; }
	public float	getSell()	{ return _sell; }
	public Date		getDate()	{ return _date; }
}