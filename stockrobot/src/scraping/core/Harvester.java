package scraping.core;

import scraping.parser.AvanzaParser;

public class Harvester {

	/**
	 * Main class for the Parsing part of the program.
	 * @param args
	 * @author Erik
	 */
	public static void main(String[] args) {
		AvanzaParser parser = new AvanzaParser();
		Thread parserThread = new Thread(parser);
		parserThread.run();
		System.out.print("*** Parser started. ***");
	}

}
