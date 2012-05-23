package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
	private PortfolioSettingsController portfolioSettings;
	
	private Map<String, EventListener> actions;
	private PortfolioHistoryController historyController = new PortfolioHistoryController();
	
	public PortfolioController(ITrader trader, IPortfolioHandler portfolios){
		
		this.trader = trader;
		this.portfolios = portfolios;
		
		actions = new HashMap<String, EventListener>();
		actions.put(PortfolioView.CREATE_PORTFOLIO, createPortfolioListener);
		actions.put(PortfolioView.MANAGE_ALGORITHMS, manageAlgorithmSettingsListener);
		actions.put(PortfolioView.HISTORY, portfolioHistoryListener);
		actions.put(PortfolioView.PORTFOLIOSELECTOR, selectedPortfolioListener);
		actions.put(PortfolioView.SETTINGS, settingsListener);
	}
	ActionListener settingsListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			IPortfolio portfolio = (view.getSelectedPortfolio());
			
			if (portfolio != null) {
				portfolioSettings.display(portfolio);
			}
		}
	};
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
				algoSettings.init();
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
	
	ItemListener selectedPortfolioListener = new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED){
				for(IPortfolio p : portfolios.getPortfolios()){
					if (p.getName().equals(e.getItem())) {
						view.setSelectedPortfolio(p);
					}
				}
			}
		}
	};
	
	@Override
	public void display(Object model) {
		defineSubControllers();
		
		view = new PortfolioView(trader, portfolios);
		view.init(); //Avoiding PMD errors
		IPortfolioHandler handler = (IPortfolioHandler) model;
		view.addActions(actions);
		view.display(handler);
	}

	@Override
	public void cleanup() {} //NOPMD

	public void defineSubControllers() {
		portfolioSettings = new PortfolioSettingsController();
	}

}