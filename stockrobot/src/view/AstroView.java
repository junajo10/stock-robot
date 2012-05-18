package view;

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


public class AstroView extends JFrame implements IView {

	private static final long serialVersionUID 			= 2371008027508651564L;

	public static final String OPEN_GRAPHWINDOW 		= "openGraphwindow";
	public static final String START_SIMULATION 		= "startSimulation";
	public static final String OPEN_STOCKTABLE			= "stockTable";	
	public static final String WINDOW_CLOSE 			= "windowClose";
	public static final String OPEN_PORTFOLIOVIEW 		= "PortfolioView";
	public static final String SHOW_LOG			 		= "ShowLog";

	private final int HEIGHT_FRAME_SUBTRACTED 	= 170;
	private final int HEIGHT_FRAME_EXPANDED 	= 655;
	private final int WIDTH_FRAME = 373;

	private JPanel contentPane;
	JButton btnSimulate 								= new JButton("Simulate Algorithms");
	JButton btnGraph 									= new JButton("Graph Window");
	JButton btnStocks 									= new JButton("Browse Stocks");
	JButton btnPortfolio 								= new JButton("Open Portfolio");

	WindowListener windowListener;

	JList log;
	DefaultListModel logModel;
	JCheckBox chckbxShowLog;
	JScrollPane scrollPane;
	private JPanel pnlLog;

	/**
	 * Create the frame.
	 */
	public AstroView() {
		setTitle("ASTRo Main");
		logModel = new DefaultListModel();
		
		setBounds(150, 150, 373, 655);
		
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
		contentPane.setBackground(SystemColor.control);
		contentPane.setForeground(Color.GREEN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblAstroStatus = new JLabel("ASTRo Robot Status:");
		lblAstroStatus.setForeground(SystemColor.desktop);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setForeground(Color.CYAN);
		progressBar.setValue(100);
		
		pnlLog = new JPanel();
		pnlLog.setBackground(null);
		
		chckbxShowLog = new JCheckBox("Show Log");
		chckbxShowLog.setForeground(Color.BLACK);
		chckbxShowLog.setBackground(SystemColor.control);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblAstroStatus)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnSimulate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnPortfolio, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnGraph, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
								.addComponent(btnStocks, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
						.addComponent(chckbxShowLog))
					.addGap(9))
				.addComponent(pnlLog, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 361, Short.MAX_VALUE)
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
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxShowLog)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlLog, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		scrollPane = new JScrollPane();
		
		log = new JList();
		log.setBackground(Color.WHITE);
		log.setForeground(Color.BLACK);
		scrollPane.setViewportView(log);
		
		JLabel lblAstroLog = new JLabel("ASTRo Log");
		lblAstroLog.setForeground(Color.BLACK);
		lblAstroLog.setBackground(Color.WHITE);
		lblAstroLog.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblAstroLog);
		scrollPane.setViewportView(log);
		log.setModel(logModel);
		
		JCheckBox cbxAutoScroll = new JCheckBox("Autoscroll Log");
		cbxAutoScroll.setBackground(null);
		cbxAutoScroll.setForeground(Color.BLACK);
		cbxAutoScroll.setSelected(true);
		
		JButton btnClearLog = new JButton("Clear Log");
		
		JButton btnExportLog = new JButton("Export Log");
		GroupLayout gl_pnlLog = new GroupLayout(pnlLog);
		gl_pnlLog.setHorizontalGroup(
			gl_pnlLog.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLog.createSequentialGroup()
					.addGroup(gl_pnlLog.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlLog.createSequentialGroup()
							.addComponent(btnClearLog, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExportLog, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlLog.createSequentialGroup()
							.addContainerGap()
							.addComponent(cbxAutoScroll, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)))
					.addGap(9))
				.addGroup(gl_pnlLog.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlLog.setVerticalGroup(
			gl_pnlLog.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlLog.createSequentialGroup()
					.addGap(5)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbxAutoScroll)
					.addGap(18)
					.addGroup(gl_pnlLog.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClearLog)
						.addComponent(btnExportLog))
					.addContainerGap())
		);
		pnlLog.setLayout(gl_pnlLog);
		contentPane.setLayout(gl_contentPane);
		hideLog();
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
		pnlLog.setVisible(true);
		setBounds(currentPosition.x, currentPosition.y, WIDTH_FRAME, HEIGHT_FRAME_EXPANDED);
	}
	
	public void hideLog(){
		Rectangle currentPosition = getBounds();
		pnlLog.setVisible(false);
		setBounds(currentPosition.x, currentPosition.y, WIDTH_FRAME, HEIGHT_FRAME_SUBTRACTED);
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