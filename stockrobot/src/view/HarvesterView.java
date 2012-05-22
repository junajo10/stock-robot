package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ListModel;

import java.awt.Dialog.ModalExclusionType;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.EventListener;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.UIManager;

import model.scraping.core.Harvester;
import model.scraping.core.HarvesterLog;

import java.awt.SystemColor;

/**
 * View for Harvester.
 * <p>
 * Uses MVC-model by Daniel.
 * <p>
 * @author Erik
 *
 */
public class HarvesterView extends JFrame implements IView{

	private static final long serialVersionUID = 1614338829073701762L;
	
	private JTextField textField;
	private JButton btnStartParser;
	private JButton btnStopParser;
	private JButton btnClearLog;
	private JButton btnExportLog;
	private JList log;
	private Logger logHandler;
	private JCheckBox chckbxSimulateStocks;
	private JButton btnStatus;
	private DefaultListModel logModel;
	private JCheckBox chckbxForceStop;
	private JScrollPane scrollPane;
	private JProgressBar parserBar;
	private JCheckBox chckbxAutoscrollLog;
	
	public static final String START_PARSER				= "startParser";
	public static final String STOP_PARSER				= "stopParser";
	public static final String PRINT_STATUS				= "printStatus";	
	public static final String CLEAR_LOG				= "clearLog";
	public static final String EXPORT_LOG 				= "exportLog";
	public static final String WINDOW_CLOSE 			= "windowClose";
	
	long totalLoops		= 0;
	int connected 		= 0;
	
	WindowListener windowListener;

	
	public HarvesterView() {
		getContentPane().setBackground(SystemColor.control);
		
		setResizable(false);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("ASTRo Harvester");
		
		btnStartParser = new JButton("Start Parser");
		btnStartParser.setForeground(Color.BLACK);
		btnStopParser = new JButton("Stop Parser");
		btnStopParser.setEnabled(false);
		logModel = new DefaultListModel();
		scrollPane = new JScrollPane();
		logHandler = new Logger();
		
		textField = new JTextField();
		textField.setBackground(SystemColor.controlHighlight);
		textField.setForeground(Color.BLACK);
		textField.setText("45000");
		textField.setToolTipText("Assign Port number");
		textField.setColumns(10);
		
		btnClearLog = new JButton("Clear Log");
		
		JLabel lblPortNumber = new JLabel("Port Number:");
		lblPortNumber.setForeground(UIManager.getColor("Tree.textForeground"));
		
		chckbxSimulateStocks = new JCheckBox("Simulate Stocks");
		chckbxSimulateStocks.setForeground(Color.BLACK);
		chckbxSimulateStocks.setBackground(UIManager.getColor("control"));
		
		btnStatus = new JButton("Print Status");
		
		chckbxForceStop = new JCheckBox("Force Stop");
		chckbxForceStop.setBackground(UIManager.getColor("control"));
		chckbxForceStop.setForeground(Color.BLACK);
		
		btnExportLog = new JButton("Export Log");
		
		parserBar = new JProgressBar(0, 20000);
		parserBar.setBackground(Color.WHITE);
		parserBar.setForeground(new Color(51, 153, 255));
		parserBar.setToolTipText("Parsing progress.");
		parserBar.setStringPainted(true);
		parserBar.setVisible(false);
		
		chckbxAutoscrollLog = new JCheckBox("Autoscroll Log");
		chckbxAutoscrollLog.setForeground(Color.BLACK);
		chckbxAutoscrollLog.setBackground(SystemColor.control);
		chckbxAutoscrollLog.setSelected(true);

		/**
		 * Windowbuilder generated code below.
		 */
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblPortNumber)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
								.addComponent(chckbxSimulateStocks)
								.addComponent(btnStartParser, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(parserBar, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
								.addComponent(btnStopParser, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
								.addComponent(chckbxForceStop, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnStatus, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
							.addComponent(btnClearLog, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExportLog, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
						.addComponent(chckbxAutoscrollLog, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStartParser, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnStopParser, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPortNumber)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxForceStop))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxSimulateStocks)
						.addComponent(parserBar, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 385, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxAutoscrollLog)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStatus, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnClearLog, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnExportLog, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		/**
		 * End windowbuilder code.
		 */
		
		log = new JList();
		log.setBackground(Color.WHITE);
		log.setForeground(Color.BLACK);
		scrollPane.setViewportView(log);
		log.setModel(logModel);
		
		JLabel lblNewLabel = new JLabel("Harvester Log");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblNewLabel);
		getContentPane().setLayout(groupLayout);
	}
	
	public String getPortTextbox(){
		return textField.getText();
	}
	
	public void addLogItem(String text){
		logModel.addElement(text);
		if(chckbxAutoscrollLog.isSelected()){
			log.ensureIndexIsVisible(logModel.size()-1);
		}
	}
	
	public boolean simulateStocksChecked(){
		return chckbxSimulateStocks.isSelected();
	}
	
	public boolean forceStopChecked(){
		return chckbxForceStop.isSelected();
	}

	public void clearLog() {
		logModel.clear();		
	}
	
	public void setStartInactive() {
		btnStartParser.setEnabled(false);

	}
	public void setStopInactive() {btnStopParser.setEnabled(false);}
	
	public void setStartActive() {
		btnStartParser.setEnabled(true);
		parserBar.setVisible(false);
	}	
	public void setStopActive() {
		parserBar.setVisible(true);
		parserBar.setEnabled(true);
		parserBar.setValue(0);
		
		btnStopParser.setEnabled(true);
	}
	
	private void addToList(String input){
		Date date= new java.util.Date();
		String time = new Timestamp(date.getTime()) + "";
		addLogItem("[" + time.substring(11, 19) + "] - " + input);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(HarvesterLog.PARSING_DONE)){
			logHandler.parsingLoop((Long) evt.getNewValue());
		}
		
		if(evt.getPropertyName().equals(HarvesterLog.PARSING_PROGRESS)){
			setParserBarProgress((Integer) evt.getNewValue());
		}
		
		if(evt.getPropertyName().equals(HarvesterLog.CONNECTED)){
			logHandler.connected((String) evt.getNewValue());
		}
		
		if(evt.getPropertyName().equals(HarvesterLog.DISCONNECTED)){
			logHandler.disconnected((String) evt.getNewValue());
		}
		
		if(evt.getPropertyName().equals(HarvesterLog.TEXT)){
			logHandler.addText((String) evt.getNewValue());
		}
		
		if(evt.getPropertyName().equals(HarvesterLog.SHUTDOWN)){
			logHandler.showDownServer();
		}
		
		if(evt.getPropertyName().equals(HarvesterLog.SERVER_UP)){
			logHandler.serverUp();
		}
		
		if(evt.getPropertyName().equals(HarvesterLog.SERVER_DOWN)){
			logHandler.finishStopped();
		}
	}

	@Override
	public void display(Object model) {
		Harvester harv = (Harvester) model;
		harv.addObserver(this);
		this.setSize(new Dimension(450, 588));
		this.setVisible(true);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int X = (screen.width / 2) - (450 / 2);
		int Y = (screen.height / 2) - (588 / 2);

		this.setBounds(X,Y , 450,588);
	}

	@Override
	public void cleanup() {

		for (ActionListener al : btnStartParser.getActionListeners()) {
			btnStartParser.removeActionListener(al);
		}
		for (ActionListener al : btnStopParser.getActionListeners()) {
			btnStopParser.removeActionListener(al);
		}
		for (ActionListener al : btnClearLog.getActionListeners()) {
			btnClearLog.removeActionListener(al);
		}
		for (ActionListener al : btnExportLog.getActionListeners()) {
			btnExportLog.removeActionListener(al);
		}
		for (ActionListener al : btnStatus.getActionListeners()) {
			btnStatus.removeActionListener(al);
		}
		for (ActionListener al : btnStartParser.getActionListeners()) {
			btnStartParser.removeActionListener(al);
		}
	} 

	@Override
	public void addActions(Map<String, EventListener> actions) {
		
		btnStartParser.addActionListener((ActionListener) actions.get(START_PARSER));
		btnStopParser.addActionListener((ActionListener) actions.get(STOP_PARSER));
		btnStatus.addActionListener((ActionListener) actions.get(PRINT_STATUS));
		btnClearLog.addActionListener((ActionListener) actions.get(CLEAR_LOG));
		btnExportLog.addActionListener((ActionListener) actions.get(EXPORT_LOG));
		
		windowListener = (WindowListener) actions.get(WINDOW_CLOSE);
		addWindowListener(windowListener);
	}

	public ListModel getLogModel() {
		return logModel;
	}

	public void setParserBarProgress(int progress){
		parserBar.setValue(progress);
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
	public void start(){
		addToList("Parser initializing.");
	}	
	
	public void failPortNumber(String portTextbox) {
		addToList(portTextbox + " is not a valid port-number. ");
	}

	public void printStatus(boolean status){
		if(status){
			addToList("Parser is up and running. ");
			addToList("Total parsing loops done: " + totalLoops);
			addToList("Number of connected to server: " + connected);
		}
		else {
			addToList("Parser closed,crashed or shutting down.");
		}
	}
	
	public void stop(){
		addToList("Parser starting to shutdown.");
	}
	
	public void failStart() {
		addToList("Parser failed to start. Already started or crashed.");
	}
	
	public void exportLog(){
		File logTxtFile = openChooseDirectory();
		if (logTxtFile != null) {
			ListModel model = getLogModel();
			PrintStream out = null;
			try {
				out = new PrintStream(new FileOutputStream(logTxtFile));
		        int len = model.getSize(); 
		        for(int i = 0; i < len; i++) { 
		        	out.println(model.getElementAt(i).toString()); 
		        } 
				addToList("Log exported to "+logTxtFile.getAbsolutePath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}  finally {
				if (out != null)
					out.close();
			}
		}
    }
	
	private class Logger {


		public void serverUp(){
			addToList("Server is up and accepting connections.");
		}	
		
		public void parsingLoop(long timeElapsed){
			totalLoops++;
			addToList("Parsing loop finished in " + timeElapsed + " ms. ");
		}		
		
		public void finishStopped(){
			addToList("Parser shutdown complete.");
		}
		
		public void showDownServer(){
			addToList("Shutting down server.");
		}


		
		public void connected(String hostname) {
			connected++;
			addToList(hostname + " has connected to Harvester.");
		}
		
		public void disconnected(String hostname) {
			connected--;
			addToList(hostname + " has disconnected from Harvester.");
		}
		
		public void addText(String text) {
			addToList(text);
		}
		

		
 
	}
	
}
