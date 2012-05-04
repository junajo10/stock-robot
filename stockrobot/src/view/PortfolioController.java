package view;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import view.components.ItemCmbPortfolio;
import viewfactory.ViewFactory;

import model.portfolio.IPortfolio;


public class PortfolioController {

	private PortfolioGui gui;
	
	public PortfolioController(PortfolioGui view){
		
		gui = view;
		
	}
	
	public ActionListener getBalanceHistoryListener() {
		
		return new BalanceHistoryListener();
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
	
	public ActionListener getChangeAlgorithmListener() {
		
		return new ChangeAlgorithmListener();
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
	
	public ActionListener getChangePortfolioListener() {
		
		return new ChangePortfolioListener();
	}
	
	/**
	 * Listener for the button change algorithm in portfolio gui
	 */
	private class ChangePortfolioListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if ((ItemCmbPortfolio)((JComboBox)e.getSource()).getSelectedItem() != null) {

				IPortfolio portfolio = ((ItemCmbPortfolio)((JComboBox)e.getSource()).getSelectedItem()).getPortfolio();
				gui.setPortfolio(portfolio);
				System.out.println(portfolio);
				gui.updateCash();
			}
			
		}
	}
	
	public ActionListener getShowStockListener() {
		
		return new ShowStockListener();
	}
	
	/**
	 * Listener for the button change algorithm in portfolio gui
	 */
	private class ShowStockListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() {
			    	//Used to be assigned to a local variable, removed it because the intention is just to launch a new JFrame subclass
			    	ViewFactory.getStockInfoGUI();
			    	//ViewFactory.getStockTableView();
			    }
			});
		}
	}
}
