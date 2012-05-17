package robot;

import java.util.List;

import utils.global.Log;
import utils.global.Pair;

import model.database.jpa.tables.AlgorithmSettingDouble;
import model.database.jpa.tables.AlgorithmSettingLong;
import model.portfolio.IAlgorithm;
import model.portfolio.IPortfolio;
import model.portfolio.IRobot_Algorithms;

/**
 *
 * TODO: ALL!!! Is this class going to the trash can?
 * 
 * RobotSchedulerTest depends on it, this adds the number of updates an algorithm got
 * But maybe a complte rewrite should be in order since, robothandler is going to be rewritten.
 *  /Daniel
 *  
 * @author Mattias
 *
 */
public class JUnitAlgorithm implements IAlgorithm {

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
			Log.log(Log.TAG.DEBUG, "TestAlgorithm got interupted");
		}
		
		return true;
	}

	@Override
	public String getName() {

		return "";
	}

	@Override
	public String getDescription() {
		
		return "";
	}
	
	public int getUpdatedNrTimes(){
		return updateVar;
	}

	@Override
	public List<AlgorithmSettingDouble> getDefaultDoubleSettings() {
		
		return null;
	}

	@Override
	public List<AlgorithmSettingLong> getDefaultLongSettings() {
		
		return null;
	}

	@Override
	public boolean giveDoubleSettings(List<Pair<String, Double>> doubleSettings) {
		
		return false;
	}

	@Override
	public boolean giveLongSettings(List<Pair<String, Long>> longSettings) {
		
		return false;
	}

	@Override
	public void setRobot(IRobot_Algorithms robot) {
		
	}

	@Override
	public void setPortfolio(IPortfolio p) {
		
	}
}