package model.wizard.portfolio;

import model.database.jpa.tables.PortfolioEntity;
import model.wizard.WizardModel;
import model.wizard.WizardPageModel;

public class PortfolioWizardModel extends WizardPageModel{

	PortfolioEntity portfolio;
	private String name;
	private String algorithm;
	private long balance;
	
	
	public PortfolioWizardModel(WizardModel wizardModel) {
		super(wizardModel);
		
		portfolio = new PortfolioEntity();
	}
	
	public void setName(String name){
		
		this.name = name;
	}
	
	public void setAlgorithm(String algorithm){
		this.algorithm = algorithm;
	}
	
	public void addBalance(long amount){
		balance = amount;
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
			
		}
	}
}
