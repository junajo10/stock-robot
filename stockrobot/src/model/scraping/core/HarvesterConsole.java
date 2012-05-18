package model.scraping.core;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class HarvesterConsole {
	
private Harvester harv;

	/**
	 * Main class for the Parsing part of the program.
	 * <p>
	 * Reads commands from the commandline.
	 * <p>
	 * This is an alternative version to the Harvester.
	 * Which uses the console instead of a GUI.
	 * @param args
	 * @author Erik
	 */
	public static void main(String[] args) {
		new HarvesterConsole();
	}

	

	
	public HarvesterConsole(){
		harv = new Harvester(12334);
		System.out.println("*** ASTRo Harvester started. ***"); //NOPMD
		System.out.println("*** Write help for help. ***"); //NOPMD
		Scanner in = new Scanner(System.in);
		while(true){
			System.out.print("ASTRo: "); //NOPMD
			try {
				String input = in.nextLine();
				takeCommand(input);
			} catch (NoSuchElementException e) {

			}
			
		}
	}
	/**
	 * Takes a command from the Harvester and executes it.
	 * 
	 * @param str command to the Harvester from the console.
	 */
	private void takeCommand(String str){
		if(str.equals("help")){
			System.out.println("*** ASTRo commandline help ***"); //NOPMD
			System.out.println("Syntax: (command) => (explanation)"); //NOPMD
			System.out.println("status         => Prints status on the parse."); //NOPMD
			System.out.println("stop           => Temporarly stop the parser thread.");	 //NOPMD
			System.out.println("force stop     => Forces the parser to stop parse. Warning! May corrupt database.");	 //NOPMD
			System.out.println("restart parser => Restarts the parser."); //NOPMD
			System.out.println("port #         => Sets serverPort to # default is 12344.");		 //NOPMD
			System.out.println("exit => Exits the entire program.");		 //NOPMD
			System.out.println("*** END OF HELP ***"); //NOPMD
		}			
		else if(str.equals("restart parser")){
			harv.startParser();
			System.out.println("*** Parser started successfully. ***");	 //NOPMD
		}
		else if(str.equals("status")){
				System.out.println("*** STATUS ***"); //NOPMD
				if(harv.status()){
					System.out.println("*** Parser is up and running. ***"); //NOPMD
				}
				else {
					System.out.println("*** Parser not started or dead. ***"); //NOPMD
				}
		}
		else if (str.equals("simulation start")) {
			System.out.println("Starting simulation"); //NOPMD
			harv.startSimulation();
		}
		else if(str.equals("stop")){
			System.out.println("*** STATUS ***"); //NOPMD
			if(harv.stopParser()){
				System.out.println("*** Parser thread stopped successfully. ***"); //NOPMD
			}
			else{
				System.out.println("*** Parser already stopped or not started."); //NOPMD
			}
		}
		else if (str.startsWith("port")) {
			String portNumber = str.substring(5);
			try {
				int port = Integer.parseInt(portNumber);
				harv.setPort(port);
				System.out.println("*** Server port set to: " + port); //NOPMD
			} catch (NumberFormatException e) {
				System.out.println("*** Malformed portnumber"); //NOPMD
			}
			
		}
		else if(str.equals("force stop")){
			System.out.println("*** STATUS ***"); //NOPMD
			if(harv.forceStop()){
				System.out.println("*** Parser thread stopped successfully. ***"); //NOPMD
			}
			else{
				System.out.println("*** Parser already stopped or not started."); //NOPMD
			}
		}
		else if(str.equals("start")){
			System.out.print(harv); //NOPMD
			harv.startParser();
			System.out.println("*** Parser started successfully. ***");	 //NOPMD
		}
		else if(str.equals("exit")){
			System.out.println("*** Exiting Harvester. ***"); //NOPMD
			System.exit(0);
		}
		else{
			System.out.println("*** Unknown command, write help for help. ***"); //NOPMD
		}
	}
	
}
