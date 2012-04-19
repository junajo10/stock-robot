package gui;

import gui.components.Item_cmb_Portfolio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import portfolio.IPortfolio;
import portfolio.IPortfolioHandler;
import trader.ITrader;

public class PortfolioController {

	private PortfolioGui gui;
	private IPortfolioHandler portfolioHandler;
	private ITrader trader;
	
	public PortfolioController(PortfolioGui gui, IPortfolioHandler portfolioHandler, ITrader trader){
		
		this.gui = gui;
		this.portfolioHandler = portfolioHandler;
		this.trader = trader;
		
		hookGui();
	}
	
	private void hookGui(){
		
		portfolioHandler.addAddObserver(gui);
		trader.addAddObserver(gui);
		gui.addBalanceHistoryListener(new BalanceHistoryListener());
		gui.addChangeAlgorithmListener(new ChangeAlgorithmListener());
		gui.addChangePortfolioListener(new ChangePortfolioListener());
		gui.addStockListener(new ShowStockListener());
		
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
			IPortfolio portfolio = ((Item_cmb_Portfolio)((JComboBox)e.getSource()).getSelectedItem()).getPortfolio();
			gui.setPortfolio(portfolio);
			System.out.println(portfolio);
			gui.updateCash();
		}
	}
	
	/**
	 * Listener for the button change algorithm in portfolio gui
	 */
	private class ShowStockListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() {
			    	StockInfoGUI stockInfo = new StockInfoGUI();
			    }
			});
			
			System.out.println("herro");
		}
	}
}
