package controller.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import view.PortfolioGui;
import view.components.ItemCmbPortfolio;

import model.portfolio.IPortfolio;


public class PortfolioController2 {

	private PortfolioGui gui;
	
	public PortfolioController2(PortfolioGui view){
		
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
}