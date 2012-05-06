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
	 * @param args
	 * @author Erik
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		HarvesterConsole harvest = new HarvesterConsole();
	}

	

	
	public HarvesterConsole(){
		harv = new Harvester(12334);
		System.out.println("*** ASTRo Harvester started. ***");
		System.out.println("*** Write help for help. ***");
		Scanner in = new Scanner(System.in);
		while(true){
			System.out.print("ASTRo: ");
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
			System.out.println("*** ASTRo commandline help ***");
			System.out.println("Syntax: (command) => (explanation)");
			System.out.println("status         => Prints status on the parse.");
			System.out.println("stop           => Temporarly stop the parser thread.");	
			System.out.println("force stop     => Forces the parser to stop parse. Warning! May corrupt database.");	
			System.out.println("restart parser => Restarts the parser.");
			System.out.println("port #         => Sets serverPort to # default is 12344.");		
			System.out.println("exit => Exits the entire program.");		
			System.out.println("*** END OF HELP ***");
		}			
		else if(str.equals("restart parser")){
			harv.startParser();
			System.out.println("*** Parser started successfully. ***");	
		}
		else if(str.equals("status")){
				System.out.println("*** STATUS ***");
				if(harv.status()){
					System.out.println("*** Parser is up and running. ***");
				}
				else {
					System.out.println("*** Parser not started or dead. ***");
				}
		}
		else if (str.equals("simulation start")) {
			System.out.println("Starting simulation");
			harv.startSimulation();
		}
		else if(str.equals("stop")){
			System.out.println("*** STATUS ***");
			if(harv.stopParser()){
				System.out.println("*** Parser thread stopped successfully. ***");
			}
			else{
				System.out.println("*** Parser already stopped or not started.");
			}
		}
		else if (str.startsWith("port")) {
			String portNumber = str.substring(5);
			try {
				int port = Integer.parseInt(portNumber);
				harv.setPort(port);
				System.out.println("*** Server port set to: " + port);
			} catch (NumberFormatException e) {
				System.out.println("*** Malformed portnumber");
			}
			
		}
		else if(str.equals("force stop")){
			System.out.println("*** STATUS ***");
			if(harv.forceStop()){
				System.out.println("*** Parser thread stopped successfully. ***");
			}
			else{
				System.out.println("*** Parser already stopped or not started.");
			}
		}
		else if(str.equals("start")){
			System.out.print(harv);
			harv.startParser();
			System.out.println("*** Parser started successfully. ***");	
		}
		else if(str.equals("exit")){
			System.out.println("*** Exiting Harvester. ***");
			System.exit(0);
		}
		else{
			System.out.println("*** Unknown command, write help for help. ***");
		}
	}
	
}
