package algorithms;


import portfolio.IPortfolio;
import robot.IRobot_Algorithms;

/**
 * A simple test algorithm based on a algorithm Kristian has come up with
 * 
 * @author daniel
 *
 */
public class TestAlgorithm implements IAlgorithm{
	
	IRobot_Algorithms robot;
	IPortfolio portfolio;
	
	public TestAlgorithm(IRobot_Algorithms robot, IPortfolio portfolio) {
		this.robot = robot;
		this.portfolio = portfolio;
		
		System.out.println("hej");
	}
	public static IAlgorithm init(IRobot_Algorithms robot, IPortfolio portfolio) {
		return new TestAlgorithm(robot, portfolio);
	}
	
	@Override
	public boolean update() {
		return false;
	}
	@Override
	public String getName() {
		return "TestAlgoritm1";
	}
	@Override
	public String getDescription() {
		return "Test algorithm1\n\nBuys when a stock has gone up 3 times in a row\nSells when a stock has gone down 3 times in a row";
	}
	
	
	@Override
	public boolean giveSetting(int id, int value) {
		return false;
	}
	@Override
	public boolean giveSetting(int id, String value) {

		return false;
	}
	@Override
	public boolean giveSetting(int id, double value) {

		return false;
	}
	@Override
	public int getNumberOfSettings() {

		return 1;
	}
	@Override
	public String getSettingText(int id) {
		switch (id) {
		case 0:
			return "Köp efter X pluss:";
		default:
			System.out.println("hepp");
		}
			
		return null;
	}
	@Override
	public String getSettingType(int id) {
		switch (id) {
		case 0:
			return "int";
		}
		return null;
	}
	@Override
	public String getSettingRange(int id) {
		switch (id) {
		case 0:
			return "1-5000";
		}
		return null;
	}
	@Override
	public String getSettingDefault(int id) {
		switch (id) {
		case 0:
			return "3";
		}
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
	


}
