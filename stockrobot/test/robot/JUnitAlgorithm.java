package robot;

import java.util.List;
import java.util.Set;

import portfolio.IPortfolio;
import trader.ITrader;

import database.jpa.tables.AlgorithmSettingDouble;
import database.jpa.tables.AlgorithmSettingLong;
import generic.Log;
import generic.Pair;
import algorithms.IAlgorithm;

public class JUnitAlgorithm implements IAlgorithm{

	private volatile int updateVar = 0;
	private long freq;
	
	public JUnitAlgorithm(long freq){
	
		this.updateVar = updateVar;
		this.freq = freq;
	}
	
	@Override
	public boolean update() {
		
		updateVar++;
		
		try {
			Thread.sleep(freq);
		} catch (InterruptedException e) {
			Log.instance().log(Log.TAG.DEBUG, "TestAlgorithm got interupted");
		}
		
		return true;
	}

	@Override
	public String getName() {

		return "";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getUpdatedNrTimes(){
		return updateVar;
	}

	@Override
	public Set<AlgorithmSettingDouble> getDefaultDoubleSettings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<AlgorithmSettingLong> getDefaultLongSettings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean giveDoubleSettings(List<Pair<String, Double>> doubleSettings) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean giveLongSettings(List<Pair<String, Long>> longSettings) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IAlgorithm createInstance(IRobot_Algorithms robot,
			IPortfolio portfolio, ITrader trader) {
		// TODO Auto-generated method stub
		return null;
	}
}

