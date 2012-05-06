package model.scraping.core;

import java.util.NoSuchElementException;
import java.util.Scanner;

import model.scraping.parser.IParserRunner;
import model.scraping.parser.ParserRunner;
import model.scraping.parser.SimulationRunner;


public class Harvester {
	private Thread parserThread;
	private IParserRunner parserRunner;
	private int serverPort = 12344;
	/**
	 * Main class for the Parsing part of the program.
	 * <p>
	 * Reads commands from the commandline.
	 * <p>
	 * @param args
	 * @author Erik
	 */
	public static void main(String[] args) {
		Harvester harvest = new Harvester();
	}
	
	public Harvester(){
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
			startParser();
			System.out.println("*** Parser started successfully. ***");	
		}
		else if(str.equals("status")){
				System.out.println("*** STATUS ***");
				if(status()){
					System.out.println("*** Parser is up and running. ***");
				}
				else {
					System.out.println("*** Parser not started or dead. ***");
				}
		}
		else if (str.equals("simulation start")) {
			System.out.println("Starting simulation");
			startSimulation();
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
		else if (str.startsWith("port")) {
			String portNumber = str.substring(5);
			System.out.println(portNumber);
			try {
				int port = Integer.parseInt(portNumber);
				
				System.out.println("Server port set to: " + port);
				serverPort = port;
			} catch (NumberFormatException e) {
				System.out.println("Malformed portnumber");
			}
			
		}
		else if(str.equals("force stop")){
			System.out.println("*** STATUS ***");
			if(forceStop()){
				System.out.println("*** Parser thread stopped successfully. ***");
			}
			else{
				System.out.println("*** Parser already stopped or not started.");
			}
		}
		else if(str.equals("start")){
			startParser();
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
	
	
	private boolean startSimulation() {
		parserRunner = new SimulationRunner(serverPort);
		parserThread = new Thread(parserRunner);
		parserThread.start();
		parserRunner.startParser();
		
		return true;
	}

	/**
	 * Force a start of the parser thread.
	 * @return True if thread started.
	 * <p>
	 * Otherwise false.
	 */
	private boolean startParser(){
		parserRunner = new ParserRunner(serverPort);
		parserThread = new Thread(parserRunner);
		parserThread.start();
		parserRunner.startParser();
		return true;
	}
	
	
	/**
	 * Force the parser to stop, without waiting 
	 * for it to complete a parsing loop.
	 * <p>
	 * @return True if the parser was running and was successfully stopped.
	 * <p>
	 * Otherwise false.
	 */
	@SuppressWarnings("deprecation")
	private boolean forceStop(){
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
	
	/**
	 * Stops the parser temporarily.
	 * <p>
	 * @return True if the parser was running and was successfully stopped.
	 * <p>
	 * Otherwise false.
	 */
	private boolean stopParser(){
		
		if(parserThread==null){
			return false;
		}
		else if(parserThread != null){
			parserRunner.stopParser();
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Checks if the parser is running.
	 * <p>
	 * @return True if the parser is running
	 * <p>
	 * Otherwise false.
	 */
	private boolean status(){
		
		if(parserThread==null){
			return false;
		}
		else if(parserThread != null && parserRunner != null && parserThread.isAlive()){
			return parserRunner.status();
		}
		else{
			return false;
		}
	}
	

}
