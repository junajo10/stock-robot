package scraping.core;

import java.util.Scanner;

import scraping.parser.AvanzaParser;

public class Harvester {
	private static Thread parserThread;
	/**
	 * Main class for the Parsing part of the program.
	 * @param args
	 * @author Erik
	 */
	public static void main(String[] args) {

		System.out.println("*** ASTRo Parser started. ***");
		System.out.println("*** Write help for help. ***");
		//parserThread.run();
		while(true){
			System.out.print("*** ASTRo command: ");
			Scanner in = new Scanner(System.in);
			String input = in.nextLine();
			takeCommand(input);
		}
	}
	
	private static void takeCommand(String str){
		if(str.equals("help")){
			System.out.println("*** ASTRo commandline help ***");
			System.out.println("Syntax: (command) => (explanation)");
			System.out.println("status => Prints status on scheduler and parser");
			System.out.println("stop => Temporarly stop the parser thread.");				
			System.out.println("restart_parser => Restarts the parser.");		
			System.out.println("*** END OF HELP ***");
		}			
		else if(str.equals("restart_parser")){
			startParser();
			System.out.println("*** Parser started successfully. ***");	
		}
		else if(str.equals("status")){
				System.out.println("*** STATUS ***");
				if(parserThread==null){
					System.out.println("*** Parser not started or dead. ***");
				}
				else if(parserThread.isAlive()){
					System.out.println("*** Parser is up and running. ***");
				}
				else{
					System.out.println("*** Parser not started or dead. ***");
				}
		}
		else if(str.equals("stop")){
			System.out.println("*** STATUS ***");
			if(stopParser()){
				System.out.println("*** Parser thread stopped successfully. ***");
			}
			else{
				System.out.println("*** Parser already stopped or not started.");
			}
		}
		else{
			System.out.println("*** Unknown command, write help for help. ***");
		}
	}
	
	private static boolean startParser(){
		AvanzaParser parser = new AvanzaParser();
		parserThread = new Thread(parser);
		parserThread.start();
		return true;
	}
	
	private static boolean stopParser(){
		if(parserThread==null){
			return false;
		}
		else if(parserThread.isAlive()){
			parserThread.stop();
			return true;
		}
		else{
			return false;
		}
	}
	

}
