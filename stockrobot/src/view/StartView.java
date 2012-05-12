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
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JLabel;

import model.robot.StartModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import utils.global.Log;
import javax.swing.JCheckBox;


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
	private JButton btnStartParser = new JButton("Open Parser");
	private JComboBox comboBox = new JComboBox();
	private StartModel model;
	
	public final static String STARTASTRO = "Start Astro";
	public final static String STARTPARSER = "Start Parser";
	public final static String LOGLEVEL = "Combobox";
	private final JCheckBox chckbxConnectToParser = new JCheckBox("Connect to parser");
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
		chckbxConnectToParser.setSelected(true);
		setResizable(false);
		setTitle("Stock Robot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 301, 244);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		
		txtLocalhost = new JTextField();
		txtLocalhost.setText("localhost:54551");
		txtLocalhost.setColumns(10);
		
		
		btnStopAstro.setEnabled(false);
		
		JLabel lblLogLevel = new JLabel("Log level:");
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnStartParser)
						.addComponent(txtLocalhost, 248, 248, 248)
						.addComponent(lblLogLevel)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btnStartAstro)
								.addGap(12)
								.addComponent(btnStopAstro)))
						.addComponent(chckbxConnectToParser))
					.addContainerGap(29, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnStartParser)
					.addGap(28)
					.addComponent(chckbxConnectToParser)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtLocalhost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStartAstro)
						.addComponent(btnStopAstro))
					.addGap(18)
					.addComponent(lblLogLevel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(13, Short.MAX_VALUE))
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
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {
		
		btnStartAstro.addActionListener((ActionListener) actions.get(STARTASTRO));
		
		btnStartParser.addActionListener((ActionListener) actions.get(STARTPARSER));
		
		comboBox.addActionListener((ActionListener) actions.get(LOGLEVEL));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
	}
	
	public String getParserLocation() {
		return txtLocalhost.getText();
	}
}
