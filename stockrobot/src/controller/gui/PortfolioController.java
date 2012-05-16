package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import model.portfolio.IPortfolio;
import model.portfolio.IPortfolioHandler;
import model.trader.ITrader;

import view.PortfolioView;

/**
 * 
 * @author Mattias
 *
 */
public class PortfolioController implements IController {

	public static final String CLASS_NAME = "PortfolioController";
	private PortfolioView view;
	private ITrader trader;
	private IPortfolioHandler portfolios; 
	
	private Map<String, EventListener> actions;
	private PortfolioHistoryController historyController = new PortfolioHistoryController();
	
	//TODO MATTIAS: Implement a real model for this, so we can access selectedPortfolio without casts. 
	public PortfolioController(ITrader trader, IPortfolioHandler portfolios){
		
		this.trader = trader;
		this.trader.addAddObserver(this);
		this.portfolios = portfolios;
		
		actions = new HashMap<String, EventListener>();
		actions.put(PortfolioView.CREATE_PORTFOLIO, createPortfolioListener);
		actions.put(PortfolioView.MANAGE_ALGORITHMS, manageAlgorithmSettingsListener);
		actions.put(PortfolioView.HISTORY, portfolioHistoryListener);
	}
	
	ActionListener createPortfolioListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			WizardFactory.buildPortfolioWizard();
		}
	};
	
	ActionListener manageAlgorithmSettingsListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			IPortfolio portfolio = (view.getSelectedPortfolio());
			
			if (portfolio != null) {
				
				AlgorithmSettingsController algoSettings = new AlgorithmSettingsController( portfolio );
				algoSettings.display( new Object() );
			}
		}
	};
	
	ActionListener portfolioHistoryListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			IPortfolio portfolio = (view.getSelectedPortfolio());
			
			if (portfolio != null)
				historyController.display(portfolio.getPortfolioTable());
		}
	};
	
	@Override
	public void display(Object model) {
		
		view = new PortfolioView(trader, portfolios);
		IPortfolioHandler handler = (IPortfolioHandler) model;
		view.addActions(actions);
		view.display(handler);
	}

	@Override
	public void cleanup() {} //NOPMD

	@Override
	public Map<String, EventListener> getActionListeners() {

		return actions;
	}

	@Override
	public void defineSubControllers() {} //NOPMD

	@Override
	public String getName() {

		return CLASS_NAME;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {}
}