package model.wizard.portfolio;

import utils.global.Log;
import model.database.jpa.IJPAHelper;
import model.database.jpa.JPAHelper;
import model.database.jpa.tables.PortfolioEntity;
import model.wizard.WizardModel;
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
		}else{
			properties.put(PROPERTY_NAME, false);
			//TODO fire error event
		}
	}
	
	public void setAlgorithm(String algorithm){
		
		if(algorithm != null){
			this.algorithm = algorithm;
			properties.put(PROPERTY_ALGORITHM, true);
		}else{
			properties.put(PROPERTY_ALGORITHM, false);
			//TODO fire error event
		}
	}
	
	public void setBalance(long amount){
		
		if(amount >= 0){
			balance = amount;
			properties.put(PROPERTY_BALANCE, true);
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
			IJPAHelper jpaHelper = JPAHelper.getInstance();
			PortfolioEntity p = new PortfolioEntity(name);
			p.setAlgorithm("algorithm");
			p.invest(balance, true);
			jpaHelper.storeObject(p);
			Log.log(Log.TAG.DEBUG, "Portfolio " + name + " created");
			
			Log.log(Log.TAG.DEBUG, "Existing portfolios");
			for(PortfolioEntity pE : jpaHelper.getAllPortfolios()){
				
				Log.log(Log.TAG.DEBUG, pE.getName());
			}
		}
	}
}
