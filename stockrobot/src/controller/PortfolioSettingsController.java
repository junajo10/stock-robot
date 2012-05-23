package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import model.portfolio.IPortfolio;

import utils.AbstractWindowCloseAdapter;
import utils.global.FinancialLongConverter;
import view.PortfolioSettingsView;

public class PortfolioSettingsController implements IController {
	private PortfolioSettingsView view;
	private IPortfolio portfolio;
	
	private ActionListener okAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (view.getSelectedPane().contentEquals(PortfolioSettingsView.INVESTEXTRAXT)) {
				int amount = view.getAmountEntered();
				
				if (amount != 0) {
					portfolio.investAmount(FinancialLongConverter.toFinancialLong(amount));
				}
			}
			else if (view.getSelectedPane().contentEquals(PortfolioSettingsView.SETTINGS)) {
				portfolio.stopBuying(view.isStopBuyingFlagSet());
				portfolio.stopSelling(view.isStopSellingFlagSet());
			}
			cleanup();
		}
	};
	private ActionListener cancelAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			cleanup();
		}
	};
	private WindowListener windowClose = new AbstractWindowCloseAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			cleanup();
		}
	};

	@Override
	public void display(Object model) {
		this.portfolio = (IPortfolio) model;
		view = new PortfolioSettingsView();
		view.addActions(getActionListeners());
		view.display(model);
	}

	@Override
	public void cleanup() {
		view.cleanup();
		view = null;
	}

	public Map<String, EventListener> getActionListeners() {
		Map<String, EventListener> actions = new HashMap<String, EventListener>();
		actions.put(PortfolioSettingsView.OKPRESSED, okAction);
		actions.put(PortfolioSettingsView.CANCELPRESSED, cancelAction);
		actions.put(PortfolioSettingsView.WINDOWCLOSE, windowClose);
		
		return actions;
	}
}
