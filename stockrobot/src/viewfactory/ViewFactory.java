package viewfactory;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.portfolio.IPortfolioHandler;
import model.robot.AlphaReceiver;
import model.trader.ITrader;

import controller.gui.MainMenuController;

import view.AlgorithmSettingsView;
import view.MainMenuView;
import view.PortfolioController;
import view.PortfolioGui;
import view.StockInfoGUI;
import view.StockTableView;
import view.algorithmsettings.SettingsPanel;

public class ViewFactory {

	public static JPanel getMainMenuView() {
		
		MainMenuView view = new MainMenuView();
		MainMenuController controller = new MainMenuController();
		//controller.bindStockInfoGUI(getStockInfoGUI());
		
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
	
	public static JFrame getPortfolioWizard(IPortfolioHandler handler){
		
		//TODO fix
		/*
		PortfolioWizardView view = new PortfolioWizardView(handler);
		PortfolioWizardContoller controller = new PortfolioWizardContoller(view);
		
		view.setCancelListener(controller.getCancelListener());
		view.getPageStart().setCreateFromNewListener(controller.getFromNewListener());
		view.getPageStart().setCreateFromCloneListener(controller.getFromCloneListener());
		*/
		return null;
	}
	
	public static JFrame getGetSettingsPanel(String algorithmName) {
		
		AlgorithmSettingsView view = new AlgorithmSettingsView(algorithmName);
		
		return view;
	}
	
	public static JPanel getSettingsPanel(String desc, double initValue, double minValue, double maxValue) { 
		
		SettingsPanel view = new SettingsPanel( desc, initValue, minValue, maxValue );
		return view;
	}
	
	public static StockInfoGUI getStockInfoGUI() { 
		
		StockInfoGUI view = new StockInfoGUI();
		
		return view;
	}
	
	public static JPanel getStockTableView() { 
		
		StockTableView view = new StockTableView();
		//StockTableController controller = new StockTableController();
		
		//TODO: Remove after alpha!
		AlphaReceiver receiveNotifier = new AlphaReceiver();
				
		view = new StockTableView();
				
		receiveNotifier.addAddObserver(view);
		
		//view.registerController( controller );
		
		return view;
	}
}
