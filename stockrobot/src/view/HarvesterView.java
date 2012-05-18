package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

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
import java.util.EventListener;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;

/**
 * View for Harvester.
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
	private JCheckBox chckbxSimulateStocks;
	private JButton btnStatus;
	private DefaultListModel logModel;
	private JCheckBox chckbxForceStop;
	private JScrollPane scrollPane;
	private JProgressBar parserBar;

	
	public static final String START_PARSER = "startParser";
	public static final String STOP_PARSER = "stopParser";
	public static final String PRINT_STATUS = "printStatus";	
	public static final String CLEAR_LOG = "clearLog";
	public static final String EXPORT_LOG = "exportLog";
	private JCheckBox chckbxAutoscrollLog;

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
		parserBar.setBackground(Color.BLACK);
		parserBar.setForeground(Color.GREEN);
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
						.addComponent(chckbxAutoscrollLog, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
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
		log.setForeground(Color.GREEN);
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {} //NOPMD

	@Override
	public void display(Object model) {
		this.setSize(new Dimension(450, 588));
		this.setVisible(true);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int X = (screen.width / 2) - (450 / 2);
		int Y = (screen.height / 2) - (588 / 2);

		this.setBounds(X,Y , 450,588);
	}

	@Override
	public void cleanup() {} //NOPMD

	@Override
	public void addActions(Map<String, EventListener> actions) {
		
		btnStartParser.addActionListener((ActionListener) actions.get(START_PARSER));
		btnStopParser.addActionListener((ActionListener) actions.get(STOP_PARSER));
		btnStatus.addActionListener((ActionListener) actions.get(PRINT_STATUS));
		btnClearLog.addActionListener((ActionListener) actions.get(CLEAR_LOG));
		btnExportLog.addActionListener((ActionListener) actions.get(EXPORT_LOG));
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
}
