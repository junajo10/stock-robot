package robot;

import java.util.List;
import java.util.Set;

import database.jpa.tables.AlgorithmSettingDouble;
import database.jpa.tables.AlgorithmSettingLong;
import generic.Log;
import algorithms.IAlgorithm;

public class JUnitAlgorithm implements IAlgorithm{

	private Integer updateVar = 0;
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
	public boolean giveDoubleSettings(
			Set<AlgorithmSettingDouble> doubleSettings) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean giveLongSettings(Set<AlgorithmSettingLong> longSettings) {
		// TODO Auto-generated method stub
		return false;
	}
}

