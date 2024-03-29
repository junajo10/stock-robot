package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JLabel;

import model.robot.StartModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import utils.global.Log;
import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.UIManager;

/**
 * 
 * @author Daniel
 */
public class StartView extends JFrame implements IView {
	private static final long serialVersionUID = -8131015783822143236L;
	private JPanel contentPane;
	private JTextField txtLocalhost;
	private JButton btnStartAstro = new JButton("Start ASTRo");
	private JButton btnStartParser = new JButton("Open Harvester");
	private JComboBox comboBox = new JComboBox();
	private StartModel model;
	
	public final static String STARTASTRO = "Start Astro";
	public final static String STARTPARSER = "Start Parser";
	public final static String LOGLEVEL = "Combobox";
	private final JCheckBox chckbxConnectToParser = new JCheckBox("Connect to Harvester Server");
	private String oldLocalhostField = "";
	private final JCheckBox chckbxUseAndroidServer = new JCheckBox("Use Android Server");
	private final JTextField textField = new JTextField();
	
	/**
	 * Create the frame.
	 */
	public StartView() {
		chckbxUseAndroidServer.setBackground(Color.WHITE);
		chckbxUseAndroidServer.setForeground(Color.BLACK);
		chckbxUseAndroidServer.setSelected(true);
		textField.setText("44000");
		textField.setColumns(10);
		chckbxConnectToParser.setBackground(Color.WHITE);
		setResizable(false);
		setTitle("ASTRo Stock Robot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 264, 458);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		txtLocalhost = new JTextField();
		txtLocalhost.setForeground(Color.BLACK);
		txtLocalhost.setBackground(UIManager.getColor("control"));
		txtLocalhost.setText("localhost:54551");
		txtLocalhost.setColumns(10);
		
		JLabel lblLogLevel = new JLabel("Log level:");


		JLabel lblNewLabel  = new JLabel();
		lblNewLabel.setIcon(new ImageIcon(StartView.class.getResource("/images/logo.png")));
		
		JLabel lblPortNr = new JLabel("Port Nr");

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(btnStartParser, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(17)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(94, Short.MAX_VALUE)
							.addComponent(lblLogLevel, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnStartAstro, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxConnectToParser, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(txtLocalhost, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblPortNr)
							.addGap(8)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(chckbxUseAndroidServer)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(btnStartParser, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(chckbxConnectToParser)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtLocalhost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(chckbxUseAndroidServer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPortNr))
					.addGap(5)
					.addComponent(lblLogLevel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnStartAstro, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		chckbxConnectToParser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chckbxConnectToParser.isSelected()) {
					txtLocalhost.setText(oldLocalhostField);
					txtLocalhost.setEnabled(true);
				}
				else {
					oldLocalhostField  = txtLocalhost.getText();
					txtLocalhost.setText("");
					txtLocalhost.setEnabled(false);
				}
			}
		});
	}

	@Override
	public void display(Object model) {
		this.model = (StartModel) model;
		
		txtLocalhost.setText(this.model.getParserServer());
		DefaultComboBoxModel comboboxModel = new DefaultComboBoxModel();
		comboboxModel.addElement(Log.TAG.NORMAL);
		comboboxModel.addElement(Log.TAG.VERBOSE);
		comboBox.setModel(comboboxModel);
		
		this.setVisible(true);
	}

	@Override
	public void cleanup() {} //NOPMD

	@Override
	public void addActions(Map<String, EventListener> actions) {
		
		btnStartAstro.addActionListener((ActionListener) actions.get(STARTASTRO));
		
		btnStartParser.addActionListener((ActionListener) actions.get(STARTPARSER));
		
		comboBox.addActionListener((ActionListener) actions.get(LOGLEVEL));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {} //NOPMD
	
	public String getParserLocation() {
		return txtLocalhost.getText();
	}
}
