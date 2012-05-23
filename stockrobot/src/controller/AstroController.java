package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.ListModel;
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
	
	private AstroModel model;
	private AstroView view = new AstroView();
	private List<IController> subControllers = new ArrayList<IController>();
	
	//Hardcoded sub controllers:
	private IController simController;
	private GraphController graphController; //Typed this GraphController instead of IController to be able to call init(), to avoid a PMD error.
	private IController stockInfoController;
	private IController portfolioController;
	
	private ActionListener startSim = new ActionListener() {
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
	
	private ActionListener openGraph = new ActionListener() {
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
	
	private ActionListener openStockInfo = new ActionListener() {
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
	
	private ActionListener openPortfolioView = new ActionListener() {
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
	
	private ActionListener clearLog = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					Log.instance().clearLog();
				}
			});
		}
	};
	
	private ActionListener exportLog = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					File logTxtFile = view.openChooseDirectory();
					if (logTxtFile != null) {
						ListModel model = Log.instance().getModel();
						PrintStream out = null;
						try {
							out = new PrintStream(new FileOutputStream(logTxtFile));
					        int len = model.getSize(); 
					        for(int i = 0; i < len; i++) { 
					        	out.println(model.getElementAt(i).toString()); 
					        } 
							Log.log(Log.TAG.NORMAL, "Log exported to "+logTxtFile.getAbsolutePath());
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}  finally {
							if (out != null)
								out.close();
						}
					}
				}
			});
		}
	};	
	
	ActionListener showLog = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(view.getShowLogIsSelected()){
				view.showLog();
			}
			else {
				view.hideLog();
			}
		}
	};
		
	WindowListener windowClose = new AbstractWindowCloseAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			cleanup();
		}
	};

	@Override
	public void display(Object parserServer) {
		String server[] = parserServer.toString().split(":");
		String host = "";
		int port = 0;
		
		view.setLogModel(Log.instance().getModel());
		
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
					
					JOptionPane.showMessageDialog(null, "Couldent connect to parser server", "Error", JOptionPane.ERROR_MESSAGE);
					cleanup();
				}
			}
			else {
				
				this.model = new AstroModel();
			}
			
			if (this.model != null) {
				
				this.model.startScheduler();
			}
		}
		if (this.model != null) {
			defineSubControllers();
			view.addActions(getActionListeners());
			
			view.display(this.model);
		}
	}

	@Override
	public void cleanup() {
		view.cleanup();
		
		if (model != null) {
			model.cleanup();
		}
		model = null;
	}

	public Map<String, EventListener> getActionListeners() {
		Map<String, EventListener> actions = new HashMap<String,EventListener>();
		actions.put(AstroView.START_SIMULATION, startSim);
		actions.put(AstroView.OPEN_GRAPHWINDOW, openGraph);
		actions.put(AstroView.OPEN_STOCKTABLE, openStockInfo);
		actions.put(AstroView.OPEN_PORTFOLIOVIEW, openPortfolioView);
		actions.put(AstroView.WINDOW_CLOSE, windowClose);
		actions.put(AstroView.SHOW_LOG, showLog);
		actions.put(AstroView.CLEAR_LOG, clearLog);
		actions.put(AstroView.EXPORT_LOG, exportLog);
		return actions;
	}

	public void defineSubControllers() {
		
		graphController = new GraphController();
		graphController.init();
		subControllers.add( graphController );
		
		stockInfoController = new StockTableController();
		subControllers.add( stockInfoController );
		
		simController = new SimController();
		subControllers.add(simController);
		
		portfolioController = new PortfolioController(model.getTrader(), PortfolioHandler.getInstance());
		subControllers.add( portfolioController );
	}
}