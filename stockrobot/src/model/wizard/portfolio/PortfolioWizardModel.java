package model.wizard.portfolio;

import utils.global.FinancialLongConverter;
import utils.global.Log;
import model.portfolio.IPortfolio;
import model.portfolio.IPortfolioHandler;
import model.portfolio.PortfolioHandler;
import model.wizard.WizardPageModel;

public class PortfolioWizardModel extends WizardPageModel{

	private String name;
	private String algorithm;
	private long balance;
	
	private static final int MIN_SIZE_NAME = 5;
	
	public static final int PROPERTY_NAME = 1;
	private static final int PROPERTY_BALANCE = 2;
	private static final int PROPERTY_ALGORITHM = 3;
	
	public PortfolioWizardModel() {
		super();
		
		properties.put(PROPERTY_NAME, false);
	}
	
	public void setName(String name){
		
		if(name.length() >= MIN_SIZE_NAME){
			this.name = name;
			properties.put(PROPERTY_NAME, true);
			Log.log(Log.TAG.DEBUG, "[PageModel] Name set to " + name);
		}else{
			properties.put(PROPERTY_NAME, false);
		}
	}
	
	public String getAlgorithm(){
		return algorithm;
	}
	
	public void setAlgorithm(String algorithm){
		
		if(algorithm != null){
			this.algorithm = algorithm;
			properties.put(PROPERTY_ALGORITHM, true);
			Log.log(Log.TAG.DEBUG, "[PageModel] Algorithm set to " + algorithm);
		}else{
			properties.put(PROPERTY_ALGORITHM, false);
		}
	}
	
	public void setBalance(long amount){
		
		if(amount >= 0){
			balance = FinancialLongConverter.toFinancialLong(amount);
			properties.put(PROPERTY_BALANCE, true);
			Log.log(Log.TAG.DEBUG, "[PageModel] Balance set to " + amount);
		}else{
			properties.put(PROPERTY_BALANCE, false);
		}
	}
	
	public long getBalance(){
		return balance;
	}

	@Override
	public boolean canFinish() {
		
		boolean finish = true;
		
		for(boolean v : properties.values()){
			finish = finish && v;
		}
		
		return finish;
	}

	@Override
	public void finish() {
		
		if(canFinish()){
			IPortfolioHandler portfolioHandler = PortfolioHandler.getInstance();
			
			IPortfolio newPortfolio = portfolioHandler.createNewPortfolio(name);
			newPortfolio.investAmount(balance);
			
			portfolioHandler.setAlgorithm(newPortfolio, algorithm);
			
			Log.log(Log.TAG.VERBOSE, "Portfolio Created \n" +
					"name: " + name + "\n" +
					"balance: " + balance + "\n" +
					"algorithm: " + algorithm); 
				
		}
	}
}
