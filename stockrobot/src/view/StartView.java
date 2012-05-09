package view;

import java.awt.EventQueue;
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


/**
 * 
 * @author Daniel
 */
public class StartView extends JFrame implements IView {
	private static final long serialVersionUID = -8131015783822143236L;
	private JPanel contentPane;
	private JTextField txtLocalhost;
	JButton btnStartAstro = new JButton("Start ASTRo");
	JButton btnStopAstro = new JButton("Stop ASTRo");
	JButton btnStartParser = new JButton("Open Parser");
	
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
		
		
		
		txtLocalhost = new JTextField();
		txtLocalhost.setText("localhost:54551");
		txtLocalhost.setColumns(10);
		
		JLabel lblParserServer = new JLabel("Parser server:");
		
		
		btnStopAstro.setEnabled(false);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnStartAstro)
									.addGap(12)
									.addComponent(btnStopAstro))
								.addComponent(btnStartParser)
								.addComponent(lblParserServer))
							.addContainerGap(102, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txtLocalhost)
							.addGap(99))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(btnStartParser)
					.addGap(32)
					.addComponent(lblParserServer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtLocalhost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStartAstro)
						.addComponent(btnStopAstro))
					.addContainerGap(29, Short.MAX_VALUE))
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
	public void addActions(Map<String, EventListener> actions) {
		
		btnStartAstro.addActionListener((ActionListener) actions.get("Start Astro"));
		
		btnStartParser.addActionListener((ActionListener) actions.get("Start Parser"));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
}
