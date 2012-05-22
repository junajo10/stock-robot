package model.wizard.portfolio;

import utils.global.FinancialLongConverter;
import utils.global.Log;
import model.algorithms.loader.PluginAlgortihmLoader;
import model.database.jpa.tables.PortfolioEntity;
import model.portfolio.IAlgorithm;
import model.portfolio.IPortfolio;
import model.portfolio.IPortfolioHandler;
import model.portfolio.PortfolioHandler;
import model.wizard.WizardPageModel;

public class PortfolioWizardModel extends WizardPageModel{

	PortfolioEntity portfolio;
	private String name;
	private String algorithm;
	private long balance;
	
	private static final int MIN_SIZE_NAME = 5;
	
	public static final int PROPERTY_NAME = 1;
	public static final int PROPERTY_BALANCE = 2;
	public static final int PROPERTY_ALGORITHM = 3;
	
	public PortfolioWizardModel() {
		super();
		
		portfolio = new PortfolioEntity();
		properties.put(PROPERTY_NAME, false);
	}
	
	public void setName(String name){
		
		if(name.length() >= MIN_SIZE_NAME){
			this.name = name;
			properties.put(PROPERTY_NAME, true);
			Log.log(Log.TAG.DEBUG, "[PageModel] Name set to " + name);
		}else{
			properties.put(PROPERTY_NAME, false);
			//TODO fire error event
		}
	}
	
	public void setAlgorithm(String algorithm){
		
		if(algorithm != null){
			this.algorithm = algorithm;
			properties.put(PROPERTY_ALGORITHM, true);
			Log.log(Log.TAG.DEBUG, "[PageModel] Algorithm set to " + algorithm);
		}else{
			properties.put(PROPERTY_ALGORITHM, false);
			//TODO fire error event
		}
	}
	
	public void setBalance(long amount){
		
		if(amount >= 0){
			balance = FinancialLongConverter.toFinancialLong(amount);
			properties.put(PROPERTY_BALANCE, true);
			Log.log(Log.TAG.DEBUG, "[PageModel] Balance set to " + amount);
		}else{
			properties.put(PROPERTY_BALANCE, false);
			//TODO send error event
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
			
			portfolioHandler.setAlgorithm(newPortfolio, algorithm);
			newPortfolio.investAmount(balance);
			
			Log.log(Log.TAG.NORMAL, "Portfolio Created \n" +
					"name: " + name + "\n" +
					"balance: " + balance + "\n" +
					"algorithm: " + algorithm); 
				
		}
	}
}
