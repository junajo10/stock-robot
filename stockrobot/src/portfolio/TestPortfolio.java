package portfolio;

import generic.Pair;

import java.util.Date;
import java.util.List;

import stock.IStock;


public class TestPortfolio implements IPortfolio{

	@Override
	public IStock[] getAvalibleStocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStock[] getCurrentStocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<IStock, Date>[] getHistoryStocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAlgorithmId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInvestedAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getUnusedAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean investNAmount(int n) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setAlgorithm(String algorithm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setAlgorithm(int algorithmId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setStocksToWatch(List<IStock> stocks) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stopBuying(boolean flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopSelling(boolean flag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isStopBuyingFlagSet() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStopSellingFlagSet() {
		// TODO Auto-generated method stub
		return false;
	}



}
