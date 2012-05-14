package model.wizard.portfolio;

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
	public static final int PROPERTY_ALGORITHM = 2;
	
	public PortfolioWizardModel(WizardModel wizardModel) {
		super(wizardModel);
		
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
		}else{
			//TODO send error event
		}
	}
	
	public long getBalance(){
		return balance;
	}

	@Override
	public boolean canFinish() {
		
		boolean finish = true;
		
		if(name == null)
			finish = false;
		else if(algorithm == null)
			finish = false;
		else if(balance < 0)
			finish = false;
		
		return finish;
	}

	@Override
	public void finish() {
		
		if(canFinish()){
			//TODO create new portfolio
		}
	}
}
