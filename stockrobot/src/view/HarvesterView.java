package view;

import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;

import model.scraping.core.Harvester;

/**
 * View for Harvester.
 * @author Erik
 *
 */
public class HarvesterView extends JFrame {

	private static final long serialVersionUID = 1614338829073701762L;
	private JTextField textField;
	private JButton btnStartParser;
	private JButton btnStopParser;
	private JButton btnClearLog;
	private JList log;
	private JCheckBox chckbxSimulateStocks;
	private JButton btnStatus;
	private DefaultListModel logModel;
	private JCheckBox chckbxForceStop;
	private JScrollPane scrollPane;

	public HarvesterView(Harvester model) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("ASTRo Harvester");
		
		btnStartParser = new JButton("Start Parser");
		btnStopParser = new JButton("Stop Parser");
		logModel = new DefaultListModel();
		scrollPane = new JScrollPane();
		
		textField = new JTextField();
		textField.setText("45000");
		textField.setToolTipText("Assign Port number");
		textField.setColumns(10);
		
		btnClearLog = new JButton("Clear Log");
		
		JLabel lblPortNumber = new JLabel("Port Number:");
		
		chckbxSimulateStocks = new JCheckBox("Simulate Stocks");
		
		btnStatus = new JButton("Print Status");
		
		chckbxForceStop = new JCheckBox("Force Stop");
		

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnStartParser, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblPortNumber)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
									.addComponent(btnStopParser, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(chckbxForceStop, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnStatus, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnClearLog, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
						.addComponent(chckbxSimulateStocks))
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
						.addComponent(chckbxForceStop)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPortNumber))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxSimulateStocks)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClearLog, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnStatus, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addGap(13))
		);
		
		log = new JList();
		scrollPane.setViewportView(log);
		log.setModel(logModel);
		getContentPane().setLayout(groupLayout);
		// TODO Auto-generated constructor stub
	}
	
	public void addbtnStartParserListener(ActionListener al){
		btnStartParser.addActionListener(al);
	}
	
	public void addbtnStopParserListener(ActionListener al){
		btnStopParser.addActionListener(al);
	}
	
	public void addbtnStatusListener(ActionListener al){
		btnStatus.addActionListener(al);
	}	
	
	public void addbtnClearLogListener(ActionListener al){
		btnClearLog.addActionListener(al);
	}
	
	public String getPortTextbox(){
		return textField.getText();
	}
	
	public void addLogItem(String text){
		logModel.addElement(text);
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
	
	public void setStartInactive() {btnStartParser.setEnabled(false);}
	public void setStopInactive() {btnStopParser.setEnabled(false);}
	
	public void setStartActive() {btnStartParser.setEnabled(true);}	
	public void setStopActive() {btnStopParser.setEnabled(true);}	
}
