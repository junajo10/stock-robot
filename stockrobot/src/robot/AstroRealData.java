package robot;

import gui.PortfolioController;
import gui.PortfolioGui;

import java.util.Random;

import database.jpa.JPAHelper;

import algorithms.loader.AlgorithmsLoader;

import portfolio.IPortfolioHandler;
import portfolio.PortfolioHandler;
import trader.ITrader;
import trader.TraderSimulator;


/**
 * @author kristian
 *
 * The starting point of the ASTRo System, base for the real data (from price list) implementation.
 * 
 */
public class AstroRealData implements IRobot_Algorithms {

	IPortfolioHandler portfolioHandler 			= null;
	AlgorithmsLoader algorithmsLoader 			= null;
	PortfolioGui portfolioGui 					= null;
	PortfolioController portfolioController 	= null;
	ITrader trader 								= null;
	JPAHelper jpaHelper 						= null;
	Random rand 								= new Random(System.currentTimeMillis());
	RobotScheduler scheduler 					= null;
	
	/**
	 * Starts the system up
	 */
	private void start() {
		System.out.println("ASTRo is starting up!!!!");

		trader = TraderSimulator.getInstance();
		 
		algorithmsLoader = AlgorithmsLoader.getInstance(this);
		
		portfolioHandler = PortfolioHandler.getInstance();
		
		portfolioGui = new PortfolioGui(portfolioHandler);
		
		portfolioController = new PortfolioController(portfolioGui,portfolioHandler,trader);
		
		jpaHelper = JPAHelper.getInstance();
		
		scheduler = new RobotScheduler( portfolioHandler );
		scheduler.startScheduler();
	}

	@Override
	public boolean reportToUser(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String args[]) {
		AstroRealData astrord = new AstroRealData();
		
		astrord.start();
	}
}
