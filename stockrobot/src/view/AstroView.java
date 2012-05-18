package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class AstroView extends JFrame implements IView {

	private static final long serialVersionUID 			= 2371008027508651564L;

	public static final String OPEN_GRAPHWINDOW 		= "openGraphwindow";
	public static final String START_SIMULATION 		= "startSimulation";
	public static final String OPEN_STOCKTABLE			= "stockTable";	
	public static final String WINDOW_CLOSE 			= "windowClose";
	public static final String OPEN_PORTFOLIOVIEW 		= "PortfolioView";

	int i = 2133;
	private JPanel contentPane;
	JButton btnSimulate 								= new JButton("Simulate Algorithms");
	JButton btnGraph 									= new JButton("Graph Window");
	JButton btnStocks 									= new JButton("Browse Stocks");
	JButton btnPortfolio 								= new JButton("Open Portfolio");

	WindowListener windowListener;

	
	DefaultListModel logModel;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AstroView frame = new AstroView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AstroView() {
		setResizable(false);
		setTitle("ASTRo Main");
		setBounds(100, 100, 213, 396);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mnHelp.add(mntmHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblAstroStatus = new JLabel("ASTRo Status:");

		JProgressBar progressBar = new JProgressBar();
		progressBar.setForeground(Color.GREEN);
		progressBar.setValue(100);
		
		JScrollPane scrollPane = new JScrollPane();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(lblAstroStatus)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnPortfolio, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
						.addComponent(btnStocks, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
						.addComponent(btnSimulate, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
						.addComponent(btnGraph, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
					.addGap(35))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAstroStatus)
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnPortfolio)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStocks)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSimulate)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnGraph)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JList log = new JList();
		scrollPane.setViewportView(log);
		
		JLabel lblAstroLog = new JLabel("ASTRo Log");
		lblAstroLog.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblAstroLog);
		contentPane.setLayout(gl_contentPane);
		scrollPane.setViewportView(log);
		logModel = new DefaultListModel();
		log.setModel(logModel);
	}
	
	public void addLogItem(Object o){
		String str = (String) o;
		logModel.addElement(str);
	}
	
	public void setLogModel(DefaultListModel model){
		this.logModel = model;
	}

	@Override
	public void display(Object model) {
		setVisible(true);
	}

	@Override
	public void cleanup() {
		removeWindowListener(windowListener);

		for (ActionListener al : btnSimulate.getActionListeners()) {
			btnSimulate.removeActionListener(al);
		}
		for (ActionListener al : btnGraph.getActionListeners()) {
			btnGraph.removeActionListener(al);
		}
		for (ActionListener al : btnStocks.getActionListeners()) {
			btnStocks.removeActionListener(al);
		}
		for (ActionListener al : btnPortfolio.getActionListeners()) {
			btnPortfolio.removeActionListener(al);
		}
	}


	@Override
	public void addActions(Map<String, EventListener> actions) {

		btnSimulate.addActionListener((ActionListener) actions.get(START_SIMULATION));
		btnGraph.addActionListener((ActionListener) actions.get(OPEN_GRAPHWINDOW));
		btnStocks.addActionListener((ActionListener) actions.get(OPEN_STOCKTABLE));
		btnPortfolio.addActionListener((ActionListener) actions.get(OPEN_PORTFOLIOVIEW));
		windowListener = (WindowListener) actions.get(WINDOW_CLOSE);

		addWindowListener(windowListener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {} //NOPMD
}