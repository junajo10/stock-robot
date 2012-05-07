package robot;

import java.beans.PropertyChangeListener;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import model.portfolio.IPortfolio;
import model.portfolio.IPortfolioHandler;
import model.robot.RobotHandler;

/**
 * JUnit tests for RobotHandler-class.
 * 
 * Remove?!
 * 
 * @author Erik
 *
 */
public class RobotHandlerTest {
	
	@Test
	public void testRunningAlgorithms(){
		IPortfolioHandler samplePortFolio = new SamplePortfolio();
		RobotHandler handler = new RobotHandler(samplePortFolio);
		handler.runAlgorithms();
		Assert.assertTrue(true);
	}



	
	private class SamplePortfolio implements IPortfolioHandler  {

		@Override
		public void addAddObserver(PropertyChangeListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeObserver(PropertyChangeListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public IPortfolio createNewPortfolio(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<IPortfolio> getPortfolios() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean removePortfolio(IPortfolio portfolio) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<String> getAlgorithmNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean setAlgorithm(IPortfolio p, String algorithmName) {
			// TODO Auto-generated method stub
			return false;
		}

	}
}
