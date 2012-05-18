package view;

import java.awt.EventQueue;
import java.awt.Rectangle;

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
import javax.swing.JCheckBox;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class AstroView extends JFrame implements IView {

	private static final long serialVersionUID 			= 2371008027508651564L;

	public static final String OPEN_GRAPHWINDOW 		= "openGraphwindow";
	public static final String START_SIMULATION 		= "startSimulation";
	public static final String OPEN_STOCKTABLE			= "stockTable";	
	public static final String WINDOW_CLOSE 			= "windowClose";
	public static final String OPEN_PORTFOLIOVIEW 		= "PortfolioView";
	public static final String SHOW_LOG			 		= "ShowLog";


	int i = 2133;
	private JPanel contentPane;
	JButton btnSimulate 								= new JButton("Simulate Algorithms");
	JButton btnGraph 									= new JButton("Graph Window");
	JButton btnStocks 									= new JButton("Browse Stocks");
	JButton btnPortfolio 								= new JButton("Open Portfolio");

	WindowListener windowListener;

	JList log;
	DefaultListModel logModel;
	JCheckBox chckbxShowLog;
	
	
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
		setBounds(100, 100, 358, 152);
		
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
		contentPane.setBackground(UIManager.getColor("ToolBar.light"));
		contentPane.setForeground(Color.GREEN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblAstroStatus = new JLabel("ASTRo Robot Status:");
		lblAstroStatus.setForeground(SystemColor.desktop);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setForeground(Color.CYAN);
		progressBar.setValue(100);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnClearLog = new JButton("Clear Log");
		
		JButton btnExportLog = new JButton("Export Log");
		
		JCheckBox autoScroll = new JCheckBox("Autoscroll Log");
		autoScroll.setBackground(Color.LIGHT_GRAY);
		autoScroll.setForeground(Color.BLACK);
		autoScroll.setSelected(true);
		
		chckbxShowLog = new JCheckBox("Show Log");
		chckbxShowLog.setForeground(Color.BLACK);
		chckbxShowLog.setBackground(UIManager.getColor("ToolBar.light"));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblAstroStatus)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnSimulate, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
								.addComponent(btnPortfolio, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnGraph, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnStocks, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnClearLog, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExportLog, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE))
						.addComponent(autoScroll, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxShowLog, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAstroStatus)
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPortfolio)
						.addComponent(btnStocks))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSimulate)
						.addComponent(btnGraph))
					.addPreferredGap(ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
					.addComponent(chckbxShowLog)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 354, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(autoScroll)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnExportLog)
						.addComponent(btnClearLog)))
		);
		
		log = new JList();
		log.setBackground(Color.WHITE);
		log.setForeground(Color.BLACK);
		scrollPane.setViewportView(log);
		
		JLabel lblAstroLog = new JLabel("ASTRo Log");
		lblAstroLog.setForeground(Color.BLACK);
		lblAstroLog.setBackground(Color.WHITE);
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
		log.setModel(model);
	}
	
	public void showLog(){
		Rectangle currentPosition = getBounds();
		
		setBounds(currentPosition.x, currentPosition.y, 358, 575);
	}
	
	public void hideLog(){
		Rectangle currentPosition = getBounds();
		
		setBounds(currentPosition.x, currentPosition.y, 358, 165);
	}
	
	public Boolean getShowLogIsSelected(){
		return chckbxShowLog.isSelected();
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

		chckbxShowLog.addActionListener((ActionListener) actions.get(SHOW_LOG));
		
		addWindowListener(windowListener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {} //NOPMD
}