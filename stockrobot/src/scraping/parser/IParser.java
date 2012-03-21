package scraping.parser;

import java.net.URL;
import java.util.ArrayList;

import scraping.model.ParserStock;

/**
 * @author Erik,kristian
 * 
 * Interface for parsing some data source
 */
public interface IParser {
	
	public ArrayList<ParserStock> parse(URL url);
}