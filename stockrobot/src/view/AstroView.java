package view;

import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.EventListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

import model.IModel;

import java.awt.SystemColor;

/**
 * View for Astro
 * <p>
 * Uses MVC-model by Daniel
 * <p>
 * @author Erik, Daniel, Mattias
 *
 */
public class AstroView extends JFrame implements IView {

	private static final long serialVersionUID 			= 2371008027508651564L;

	public static final String OPEN_GRAPHWINDOW 		= "openGraphwindow";
	public static final String START_SIMULATION 		= "startSimulation";
	public static final String OPEN_STOCKTABLE			= "stockTable";	
	public static final String WINDOW_CLOSE 			= "windowClose";
	public static final String OPEN_PORTFOLIOVIEW 		= "PortfolioView";
	public static final String SHOW_LOG			 		= "ShowLog";
	public static final String CLEAR_LOG			 	= "ClearLog";
	public static final String EXPORT_LOG			 	= "ExportLog";
	public static final String ADD_LISTITEM				= "AddListItem";

	private final int HEIGHT_FRAME_SUBTRACTED 	= 146;
	private final int HEIGHT_FRAME_EXPANDED 	= 655;
	private final int WIDTH_FRAME = 373;


	private JButton btnSimulate 								= new JButton("Simulate Algorithms");
	private JButton btnGraph 									= new JButton("Graph Window");
	private JButton btnStocks 									= new JButton("Browse Stocks");
	private JButton btnPortfolio 								= new JButton("Open Portfolio");
	private JButton btnClearLog 								= new JButton("Clear Log");
	private JButton btnExportLog 								= new JButton("Export Log");
	
	private JCheckBox cbxAutoScroll 							= new JCheckBox("Autoscroll Log");
	private JCheckBox chckbxShowLog								= new JCheckBox("Show Log");
	private JPanel contentPane 									= new JPanel();

	private WindowListener windowListener;

	private JList log;
	private DefaultListModel logModel;
	private JScrollPane scrollPane;
	private JPanel pnlLog;

	/**
	 * Create the frame.
	 */
	public AstroView() {
		setTitle("ASTRo Main");
		logModel = new DefaultListModel();
		
		setBounds(150, 150, 373, 655);
		
		contentPane.setBackground(SystemColor.control);
		contentPane.setForeground(Color.GREEN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		pnlLog = new JPanel();
		pnlLog.setBackground(null);
		
		
		chckbxShowLog.setForeground(Color.BLACK);
		chckbxShowLog.setBackground(SystemColor.control);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(pnlLog, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 361, Short.MAX_VALUE)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSimulate, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
						.addComponent(btnPortfolio, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnGraph, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
						.addComponent(btnStocks, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(chckbxShowLog)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPortfolio)
						.addComponent(btnStocks))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSimulate)
						.addComponent(btnGraph))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxShowLog)
					.addGap(22)
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
		

		cbxAutoScroll.setBackground(null);
		cbxAutoScroll.setForeground(Color.BLACK);
		cbxAutoScroll.setSelected(true);
		

		GroupLayout gl_pnlLog = new GroupLayout(pnlLog);
		gl_pnlLog.setHorizontalGroup(
			gl_pnlLog.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLog.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_pnlLog.createSequentialGroup()
					.addGroup(gl_pnlLog.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_pnlLog.createSequentialGroup()
							.addGap(6)
							.addComponent(cbxAutoScroll, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(btnClearLog, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnExportLog, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addGap(9))
		);
		gl_pnlLog.setVerticalGroup(
			gl_pnlLog.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlLog.createSequentialGroup()
					.addGap(5)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
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
	}
	
	private void addLogItem(Object o){
		String str = (String) o;
		logModel.addElement(str);
		if(cbxAutoScroll.isSelected()){
			log.ensureIndexIsVisible(logModel.size()-1);
		}
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
		IModel astro = (IModel) model;
		astro.addObserver(this);
		hideLog();
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
		for (ActionListener al : chckbxShowLog.getActionListeners()) {
			chckbxShowLog.removeActionListener(al);
		}
		for (ActionListener al : btnPortfolio.getActionListeners()) {
			btnPortfolio.removeActionListener(al);
		}
		for (ActionListener al : btnClearLog.getActionListeners()) {
			btnClearLog.removeActionListener(al);
		}
		for (ActionListener al : btnExportLog.getActionListeners()) {
			btnExportLog.removeActionListener(al);
		}
	}


	@Override
	public void addActions(Map<String, EventListener> actions) {

		btnSimulate.addActionListener((ActionListener) actions.get(START_SIMULATION));
		btnGraph.addActionListener((ActionListener) actions.get(OPEN_GRAPHWINDOW));
		btnStocks.addActionListener((ActionListener) actions.get(OPEN_STOCKTABLE));
		btnPortfolio.addActionListener((ActionListener) actions.get(OPEN_PORTFOLIOVIEW));
		chckbxShowLog.addActionListener((ActionListener) actions.get(SHOW_LOG));
		btnClearLog.addActionListener((ActionListener) actions.get(CLEAR_LOG));
		btnExportLog.addActionListener((ActionListener) actions.get(EXPORT_LOG));
		windowListener = (WindowListener) actions.get(WINDOW_CLOSE);
	
		addWindowListener(windowListener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(AstroView.ADD_LISTITEM)){
			addLogItem(evt.getNewValue());
		}
	}

	public File openChooseDirectory() {
		 JFileChooser fc = new JFileChooser();
         int returnVal = fc.showSaveDialog(this);
         if (returnVal == JFileChooser.APPROVE_OPTION) {
        	 File file = fc.getSelectedFile();
        	 return file;
         }
         return null;
	}

	public void clearLog() {
		logModel.clear();
	}

	public ListModel getLogModel() {
		return logModel;
	}
}