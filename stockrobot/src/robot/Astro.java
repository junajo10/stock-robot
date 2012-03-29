package robot;

import gui.PortfolioController;
import gui.PortfolioGui;
import gui.StockInfoGUI;

import java.util.Date;
import java.util.List;
import java.util.Random;

import database.jpa.IJPAHelper;
import database.jpa.JPAHelper;
import database.jpa.tables.AlgorithmEntitys;
import database.jpa.tables.PortfolioEntitys;
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
	PortfolioGui portfolioGui = null;
	StockInfoGUI stockInfoGUI = null;
	PortfolioController portfolioController = null;
	ITrader trader = null;
	IJPAHelper jpaHelper = JPAHelper.getInstance();
	Random rand = new Random(System.currentTimeMillis());

	private static boolean simulate = false;
	private static int timeBetweenUpdates = 1000;
	/**
	 * Starts the system up
	 */
	//TODO: In a new thread?
	private void start() {
		
		System.out.println("ASTRo is starting up.");

		trader				= TraderSimulator.getInstance();
		algorithmsLoader 	= AlgorithmsLoader.getInstance(this);
		portfolioHandler 	= PortfolioHandler.getInstance();
		portfolioGui 		= new PortfolioGui(portfolioHandler);
		stockInfoGUI 		= new StockInfoGUI();
		portfolioController = new PortfolioController(portfolioGui,portfolioHandler,trader);


		if (simulate) {
			initSimulationState();
		}
		while(true) {
			if (simulate)
				simulateNewStocks();


			for (IPortfolio p : portfolioHandler.getPortfolios()) {
				if (simulate) {
					if (rand.nextInt(10) == 1) {
						long newInvestment = ((long)rand.nextInt(1000)*1000000);

						p.investAmount(newInvestment);
						System.out.println("More money invested: " + newInvestment + " to portfolio: " + p);
					}
				}

				p.getAlgorithm().update();
			}
			try {
				Thread.sleep(timeBetweenUpdates);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void initSimulationState() {
		List<PortfolioEntitys> portfolios = jpaHelper.getAllPortfolios();
		
		if (portfolios.size() == 0) {
			PortfolioEntitys portfolio = new PortfolioEntitys("portfolio 1");
			jpaHelper.storeObject(portfolio);
			portfolio.setAlgorithm(new AlgorithmEntitys("algorithm1", "algorithms.TestAlgorithm"));
			jpaHelper.updateObject(portfolio);

			
			PortfolioEntitys portfolio2 = new PortfolioEntitys("portfolio 2");
			jpaHelper.storeObject(portfolio2);
			
			portfolio2.setAlgorithm(new AlgorithmEntitys("algorithm2", "algorithms.TestAlgorithm2"));
			jpaHelper.updateObject(portfolio2);
			
			portfolios = jpaHelper.getAllPortfolios();
		}
		
		for (PortfolioEntitys p : portfolios) {
			jpaHelper.investMoney(10000000, p);
		}
		List<StockNames> stockNames = jpaHelper.getAllStockNames();
		if (stockNames.size() == 0) {
			StockNames stockName = new StockNames("Stock1", "MarketA");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock2", "MarketB");
			jpaHelper.storeObject(stockName);
			
			
			stockName = new StockNames("Stock3", "MarketB");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock4", "MarketA");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock5", "MarketB");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock6", "MarketB");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock7", "MarketB");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock8", "MarketB");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock9", "MarketB");
			jpaHelper.storeObject(stockName);
			
			stockName = new StockNames("Stock10", "MarketB");
			jpaHelper.storeObject(stockName);
		}
		
	}

	/**
	 * Just add a new stock
	 */
	private void simulateNewStocks() {
		List<StockNames> stockNames = jpaHelper.getAllStockNames();

		for (StockNames sn : stockNames) {
			StockPrices sp = new StockPrices(sn, rand.nextInt(100000000), rand.nextInt(100000000), rand.nextInt(100000000), rand.nextInt(100000000), new Date(System.currentTimeMillis()));
			//System.out.println("New stockPrice created: " + sp);
			jpaHelper.storeObjectIfPossible(sp);
		}
	}


	@Override
	public boolean reportToUser(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String args[]) {
		Astro astro = new Astro();

		for (int i = 0; i < args.length; i++) {
			String s = args[i];

			if (s.contentEquals("--simulate") || s.contentEquals("-s"))
				simulate = true;
			else if (s.contentEquals("-t") || s.contentEquals("--time")) {
				if (args.length < i+3) {
					try {
						timeBetweenUpdates = Math.abs(Integer.parseInt(args[i+1]));
						System.out.println("Algorithm update time set to: " + timeBetweenUpdates + "ms.");
						i++;
					} catch (NumberFormatException e) {
						System.out.println("No valid update time.");
						System.exit(1);
					}
				}
			}
			else if (s.contentEquals("--help")) {
				System.out.println("ASTRo\nAlgorithm Stock Trading Robot\n\n\t-s or --simulate" + 
						"\tTo simulate new stocks and more investments.\n" +
						"\t--time x or -t x\tSet the time between algorithm updates to x ms.\n");
			}
			else {
				System.out.println("Unknown parameter, aborting\n");
				System.exit(1);
			}
		}

		astro.start();
	}

	@Override
	public IJPAHelper getJPAHelper() {
		return jpaHelper;
	}
}
