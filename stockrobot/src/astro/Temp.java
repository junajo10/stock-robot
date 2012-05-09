package astro;

import java.util.Date;
import java.util.Random;

import utils.global.Log;
import utils.global.Log.TAG;

import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;
import model.database.jpa.tables.StockNames;
import model.database.jpa.tables.StockPrices;
import model.portfolio.IPortfolioHandler;
import model.portfolio.IRobot_Algorithms;
import model.portfolio.PortfolioHandler;
import model.robot.RobotScheduler;
import model.trader.ITrader;
import model.trader.TraderSimulator;

public class Temp implements IRobot_Algorithms{

	IJPAHelper jpaHelper;
	IPortfolioHandler pHandler;
	RobotScheduler rs;
	TraderSimulator trader = TraderSimulator.getInstance();
	StockNames sn;
	public static void main(String[] args) {

		new Temp();

	}

	public Temp() {
		jpaHelper = JPAHelper.getInstance();
		
		if (jpaHelper.getAllPortfolios().size() == 0) {
			PortfolioEntity p = new PortfolioEntity("APA");
			p.setAlgorithm("TestAlgorithm1");
			jpaHelper.storeObjectIfPossible(p);
		}

		
		StockNames sn = new StockNames("APA", "BEPA");
		jpaHelper.storeObjectIfPossible(sn);
		
		this.sn = jpaHelper.getAllStockNames().get(0);
		
		pHandler = PortfolioHandler.getInstance(this);
		
		rs = new RobotScheduler(pHandler);
		Log.instance().setFilter(TAG.VERBOSE, true);
		//Log.instance().setFilter(TAG.VERY_VERBOSE, true);
		apa();
	}
	public void apa() {
		Random rand = new Random(System.currentTimeMillis());
		while (true) {
			for (PortfolioEntity pe : jpaHelper.getAllPortfolios()) {
				pe.invest(1000000, true);
				jpaHelper.updateObject(pe);
			}
			
			
			StockPrices sp = new StockPrices(sn, 1, 1, rand.nextInt(1000000), rand.nextInt(1000000), new Date(System.currentTimeMillis()));
			jpaHelper.storeObject(sp);
			
			rs.runAlgorithms();
			
			try {
				Thread.sleep(1100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public IJPAHelper getJPAHelper() {
		return jpaHelper;
	}

	@Override
	public ITrader getTrader() {
		return trader;
	}
}
