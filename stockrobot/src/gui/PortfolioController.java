package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import portfolio.IPortfolio;
import portfolio.IPortfolioHandler;
import trader.ITrader;

public class PortfolioController {

	private PortfolioGui gui;
	private IPortfolioHandler portfolioHandler;
	
	public PortfolioController(PortfolioGui gui, IPortfolioHandler portfolioHandler, ITrader trader){
		
		this.gui = gui;
		this.portfolioHandler = portfolioHandler;
		
		
		portfolioHandler.addAddObserver(gui);
		trader.addAddObserver(gui);
		gui.addBalanceHistoryListener(new BalanceHistoryListener());
		gui.addChangeAlgorithmListener(new ChangeAlgorithmListener());
		gui.addChangePortfolioListener(new ChangePortfolioListener());
	}
	
	/**
	 * Listener for the button history in portfolio gui
	 */
	private class BalanceHistoryListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			System.out.println("Pressed history");
		}
	}
	
	/**
	 * Listener for the button change algorithm in portfolio gui
	 */
	private class ChangeAlgorithmListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			System.out.println("Pressed change");
		}
	}
	
	/**
	 * Listener for the button change algorithm in portfolio gui
	 */
	private class ChangePortfolioListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			IPortfolio portfolio = ((PortfolioGui.Item_cmb_Portfolio)((JComboBox)e.getSource()).getSelectedItem()).getPortfolio();
			gui.setPortfolio(portfolio);
			System.out.println(portfolio);
			gui.updateCash();
		}


	}
}
