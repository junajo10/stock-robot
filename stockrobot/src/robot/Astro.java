package robot;

import java.sql.ResultSet;

import database.jpa.tables.StockPrices;

import algorithms.loader.AlgorithmsLoader;

import portfolio.IPortfolioHandler;
import portfolio.PortfolioHandler;


/**
 * @author Daniel
 *
 * The starting point of the ASTRo System.
 */
public class Astro implements IRobot_Algorithms{

	IPortfolioHandler portfolioHandler = null;
	AlgorithmsLoader algorithmsLoader = null;
	/**
	 * Starts the system up
	 */
	//TODO: In a new thread?
	private void start() {
		System.out.println("ASTRo is starting up.");

		algorithmsLoader = AlgorithmsLoader.getInstance(this);
		
		portfolioHandler = PortfolioHandler.getInstance();
		
		//TODO: Init TraderAPI
		
		//TODO: Init GUI
		
		/*
		while(true) {
			// Let the robot do its work.
			
		}
		*/
	}

	@Override
	public int buyStock(StockPrices s, int amount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int buyStock(StockPrices s, int amount, int atLeastN) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int sellStock(StockPrices s, int amount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int sellStock(StockPrices s, int amount, int atLeastN) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCourtagePrice(StockPrices s, int amount, boolean buying) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean reportToUser(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResultSet askQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String args[]) {
		Astro astro = new Astro();
		astro.start();
	}
}
