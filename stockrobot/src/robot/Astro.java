package robot;

import java.util.Date;
import java.util.Random;

import database.jpa.JPAHelper;
import database.jpa.tables.StockNames;
import database.jpa.tables.StockPrices;

import algorithms.loader.AlgorithmsLoader;

import portfolio.IPortfolio;
import portfolio.IPortfolioHandler;
import portfolio.PortfolioHandler;
import trader.ITrader;
import trader.TraderSimulator;


/**
 * @author Daniel
 *
 * The starting point of the ASTRo System.
 * 
 * It will currently insert a new stock in the system each second, 
 * and then start the algorithms in all portfolios.
 * 
 * This is the very very first basic prototype of the system.
 */
public class Astro implements IRobot_Algorithms{

	IPortfolioHandler portfolioHandler = null;
	AlgorithmsLoader algorithmsLoader = null;
	ITrader trader = null;
	JPAHelper jpaHelper = null;
	Random rand = new Random(System.currentTimeMillis());
	/**
	 * Starts the system up
	 */
	//TODO: In a new thread?
	private void start() {
		System.out.println("ASTRo is starting up.");

		trader = TraderSimulator.getInstance();
		
		algorithmsLoader = AlgorithmsLoader.getInstance(this);
		
		portfolioHandler = PortfolioHandler.getInstance();
		
		jpaHelper = JPAHelper.getInstance();
		
		while(true) {
			simulateNewStock();
			
			
			for (IPortfolio p : portfolioHandler.getPortfolios()) {
				if (rand.nextInt(10) == 1) {
					long newInvestment = ((long)rand.nextInt(1000)*1000);
					System.out.println("More money invested: " + newInvestment + " to portfolio: " + p);
					p.investAmount(newInvestment);
				}
				
				p.getAlgorithm().update();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * Just add a new stock
	 */
	private void simulateNewStock() {
		StockNames s = jpaHelper.getAllStockNames().get(0);
		StockPrices sp = new StockPrices(s, rand.nextInt(1000), rand.nextInt(1000), rand.nextInt(1000), rand.nextInt(1000), new Date(System.currentTimeMillis()));
		jpaHelper.storeObjectIfPossible(sp);		
	}


	@Override
	public boolean reportToUser(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String args[]) {
		Astro astro = new Astro();
		astro.start();
	}
}
