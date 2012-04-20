package model.scraping.parser;

import java.net.URL;
import java.util.ArrayList;

import model.scraping.model.ParserStock;


/**
 * @author Erik,kristian
 * 
 * Interface for parsing some data source
 */
public interface IParser {
	
	public ArrayList<ParserStock> parse(URL url, String market);
}