package robot;

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

	@Override
	public boolean giveSetting(int id, int value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean giveSetting(int id, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean giveSetting(int id, double value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNumberOfSettings() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSettingText(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSettingType(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSettingRange(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSettingDefault(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCurrentIntSetting(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCurrentStringSetting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getCurrentDoubleSetting(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getUpdatedNrTimes(){
		return updateVar;
	}
}

