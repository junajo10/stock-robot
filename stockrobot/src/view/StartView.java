package view;

import java.awt.EventQueue;
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
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					StartView frame = new StartView();
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
	public StartView() {
		chckbxConnectToParser.setBackground(Color.WHITE);
		chckbxConnectToParser.setSelected(true);
		setResizable(false);
		setTitle("ASTRo Stock Robot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 252, 402);
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

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxConnectToParser, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtLocalhost, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnStartAstro, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnStartParser, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGap(1)
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
						.addComponent(lblLogLevel, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnStartParser, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(chckbxConnectToParser)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtLocalhost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLogLevel)
					.addGap(5)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnStartAstro, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addGap(9))
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
		
		comboBox.setModel(new DefaultComboBoxModel(Log.TAG.values()));
		
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
