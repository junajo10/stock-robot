package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;
import java.util.Map;

import javax.imageio.ImageIO;
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
<<<<<<< HEAD
<<<<<<< HEAD
import javax.swing.UIManager;
=======
>>>>>>> Change background color of StartView to match current and future logo better.
=======
>>>>>>> fca02af876f8f019442f6a2b141e13a5c9ec85bd

/**
 * 
 * @author Daniel
 */
public class StartView extends JFrame implements IView {
	private static final long serialVersionUID = -8131015783822143236L;
	private JPanel contentPane;
	private JTextField txtLocalhost;
	private JButton btnStartAstro = new JButton("Start ASTRo");
	private JButton btnStopAstro = new JButton("Stop ASTRo");
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
<<<<<<< HEAD
<<<<<<< HEAD
		chckbxConnectToParser.setForeground(UIManager.getColor("Tree.textForeground"));
=======
>>>>>>> Change background color of StartView to match current and future logo better.
=======
>>>>>>> fca02af876f8f019442f6a2b141e13a5c9ec85bd
		chckbxConnectToParser.setSelected(true);
		setResizable(false);
		setTitle("ASTRo Stock Robot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 301, 420);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		txtLocalhost = new JTextField();
		txtLocalhost.setForeground(Color.BLACK);
		txtLocalhost.setBackground(UIManager.getColor("control"));
		txtLocalhost.setText("localhost:54551");
		txtLocalhost.setColumns(10);
		
		btnStopAstro.setEnabled(false);
		
		JLabel lblLogLevel = new JLabel("Log level:");

		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("logo.jpg"));
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		JLabel lblNewLabel  = new JLabel(new ImageIcon( myPicture ));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
						.addComponent(btnStartParser, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
						.addComponent(txtLocalhost, 248, 256, Short.MAX_VALUE)
						.addComponent(lblLogLevel)
						.addComponent(comboBox, 0, 256, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnStartAstro, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnStopAstro, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
						.addComponent(chckbxConnectToParser))
					.addGap(27))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnStartParser, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(chckbxConnectToParser)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtLocalhost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStartAstro, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnStopAstro, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblLogLevel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
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
