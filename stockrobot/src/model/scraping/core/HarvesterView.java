package model.scraping.core;

import java.awt.event.ActionListener;

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
	private JButton btnNewButton;
	private JList log;
	private JCheckBox chckbxSimulateStocks;

	public HarvesterView(Harvester model) {
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("ASTRo Harvester");
		
		btnStartParser = new JButton("Start Parser");
		btnStopParser = new JButton("Stop Parser");
		
		log = new JList();
		
		textField = new JTextField();
		textField.setText("45000");
		textField.setToolTipText("Assign Port number");
		textField.setColumns(10);
		
		btnNewButton = new JButton("Clear Log");
		
		JLabel lblPortNumber = new JLabel("Port Number:");
		
		chckbxSimulateStocks = new JCheckBox("Simulate Stocks");
		chckbxSimulateStocks.setSelected(true);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxSimulateStocks)
						.addComponent(log, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblPortNumber)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
								.addComponent(btnStartParser, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
							.addComponent(btnStopParser, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStartParser, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnStopParser, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPortNumber))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxSimulateStocks)
					.addGap(10)
					.addComponent(log, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
		// TODO Auto-generated constructor stub
	}
	
	public void addbtnStartParserListener(ActionListener al){
		btnStartParser.addActionListener(al);
	}
	
	public void addbtnStopParserListener(ActionListener al){
		btnStopParser.addActionListener(al);
	}
	
	public String getPortTextbox(){
		return textField.getText();
	}
	
	public void addLogItem(String text){
		//Implement
	}
	
	public boolean simulateStocksChecked(){
		return chckbxSimulateStocks.isSelected();
	}
}
