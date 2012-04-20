package viewfactory;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.portfolio.IPortfolioHandler;
import model.trader.ITrader;

import controller.gui.MainMenuController;

import view.AlgorithmSettingsView;
import view.MainMenuView;
import view.PortfolioController;
import view.PortfolioGui;

public class ViewFactory {

	public static JPanel getMainMenuView() {
		
		MainMenuView view = new MainMenuView();
		MainMenuController controller = new MainMenuController();
		
		view.bindOpenAlgorithmSettings(controller.bindAlgorithmSettingsGUIButton());
		view.bindStockInfoWindow(controller.bindStockInfoGUIButton());
		
		return view;
	}
	
	public static JFrame getPortfolioView(IPortfolioHandler portfolioHandler, ITrader trader){
		
		PortfolioGui view = new PortfolioGui(portfolioHandler);
		PortfolioController controller = new PortfolioController(view);
		
		portfolioHandler.addAddObserver(view);
		trader.addAddObserver(view);
		
		view.addBalanceHistoryListener(controller.getBalanceHistoryListener());
		view.addChangeAlgorithmListener(controller.getChangeAlgorithmListener());
		view.addChangePortfolioListener(controller.getChangePortfolioListener());
		view.addStockListener(controller.getShowStockListener());
		
		return view;
	}
	
	public static JFrame getGetSettingsPanel(String algorithmName) {
		
		AlgorithmSettingsView view = new AlgorithmSettingsView(algorithmName);
		
		return view;
	}
}
