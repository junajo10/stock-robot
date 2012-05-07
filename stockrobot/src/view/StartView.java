package view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JLabel;

import model.robot.StartModel;


public class StartView extends JFrame implements IView {
	private static final long serialVersionUID = -8131015783822143236L;
	private JPanel contentPane;
	private JTextField txtLocalhost;
	private JTextField textField;
	JButton btnStartAstro = new JButton("Start ASTRo");
	JButton btnStopParser = new JButton("Stop Parser");
	JButton btnStopAstro = new JButton("Stop ASTRo");
	JButton btnStartParser = new JButton("Start Parser");
	
	private StartModel model;
	
	
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
		setResizable(false);
		setTitle("Stock Robot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 371, 209);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JCheckBox chckbxSimulateStocks = new JCheckBox("Simulate Stocks");
		
		
		
		txtLocalhost = new JTextField();
		txtLocalhost.setText("localhost:54551");
		txtLocalhost.setColumns(10);
		
		
		
		textField = new JTextField();
		textField.setText("54551");
		textField.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		
		JLabel lblParserServer = new JLabel("Parser server:");
		
		
		btnStopParser.setEnabled(false);
		
		
		btnStopAstro.setEnabled(false);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(chckbxSimulateStocks)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblPort)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, 0, 0, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnStartParser)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnStopParser))
						.addComponent(lblParserServer)
						.addComponent(txtLocalhost)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnStartAstro)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnStopAstro)))
					.addContainerGap(96, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxSimulateStocks)
						.addComponent(lblPort)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStartParser)
						.addComponent(btnStopParser))
					.addGap(23)
					.addComponent(lblParserServer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtLocalhost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStartAstro)
						.addComponent(btnStopAstro))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	@Override
	public void display(Object model) {
		this.model = (StartModel) model;
		
		txtLocalhost.setText(this.model.getParserServer());
		
		this.setVisible(true);
		
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActions(Map<String, ActionListener> actions) {
		
		btnStartAstro.addActionListener(actions.get("Start Astro"));
	}
}
