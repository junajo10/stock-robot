package controller.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.portfolio.PortfolioHandler;
import model.robot.AstroModel;

import utils.AbstractWindowCloseAdapter;
import utils.global.Log;
import utils.global.Log.TAG;
import view.AstroView;

/**
 * 
 * @author Daniel
 */
public class AstroController implements IController {
	
	public static final String CLASSNAME = "Astro Controller";
	
	AstroModel model;
	AstroView view = new AstroView();
	List<IController> subControllers = new ArrayList<IController>();
	
	//Hardcoded sub controllers:
	private IController simController;
	private IController graphController;
	private IController stockInfoController;
	private IController portfolioController;
	
	ActionListener startSim = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					simController.display(null);
				}
			});
		}
	};
	
	ActionListener openGraph = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					graphController.display(null);
				}
			});
		}
	};
	
	ActionListener openStockInfo = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					stockInfoController.display(null);
				}
			});
		}
	};
	
	ActionListener openPortfolioView = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					portfolioController.display(null);
				}
			});
		}
	};
		
	WindowListener windowClose = new AbstractWindowCloseAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			cleanup();
		}
	};

	public AstroController() {

	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}

	@Override
	public void display(Object parserServer) {
		String server[] = parserServer.toString().split(":");
		String host = "";
		int port = 0;
		
		if (server.length >= 2) {
			host = parserServer.toString().substring(0, parserServer.toString().lastIndexOf(":"));
			try {
				port = Integer.parseInt(server[server.length-1]);
				if (port <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException e) {
				Log.log(TAG.ERROR, "Couldent parse port to server");
			}
			Log.log(TAG.VERY_VERBOSE, "Parsed location field to: " + host + " as host at port number: " + port);
		}
		
		
		if (this.model == null) {
			if (port > 0) {
				try {
					this.model = new AstroModel(host, port);
				} catch (Exception e) {
					//TODO: Show message to user that connection couldent be established.
					JOptionPane.showMessageDialog(null, "Couldent connect to parser server", "Error", JOptionPane.ERROR_MESSAGE);
					cleanup();
				}
			}
			else
				this.model = new AstroModel();
			
			if (this.model != null)
				this.model.startScheduler();
		}
		if (this.model != null) {
			defineSubControllers();
			view.addActions(getActionListeners());
			
			view.display(this.model);
		}
	}

	@Override
	public void cleanup() {
		view.removePropertyChangeListener(this);
		view.cleanup();
		
		if (model != null) {
			model.cleanup();
		}
		model = null;
	}

	@Override
	public Map<String, EventListener> getActionListeners() {
		Map<String, EventListener> actions = new HashMap<String,EventListener>();
		actions.put(AstroView.START_SIMULATION, startSim);
		actions.put(AstroView.OPEN_GRAPHWINDOW, openGraph);
		actions.put(AstroView.OPEN_STOCKTABLE, openStockInfo);
		actions.put(AstroView.OPEN_PORTFOLIOVIEW, openPortfolioView);
		actions.put(AstroView.WINDOW_CLOSE, windowClose);
		return actions;
	}


	@Override
	public String getName() {
		
		return CLASSNAME;
	}

	@Override
	public void defineSubControllers() {
		
		graphController = new GraphController();
		subControllers.add( graphController );
		
		stockInfoController = new StockTableController();
		subControllers.add( stockInfoController );
		
		simController = new SimController();
		subControllers.add(simController);
		
		portfolioController = new PortfolioController(model.getTrader(), PortfolioHandler.getInstance());
		subControllers.add( portfolioController );
	}
}