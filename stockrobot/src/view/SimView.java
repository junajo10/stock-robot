package view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.LayoutStyle.ComponentPlacement;

import utils.global.Pair;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.simulation.SimModel;
import javax.swing.SwingConstants;

public class SimView extends JFrame implements IView {

	private static final long serialVersionUID = -4933445365240811334L;
	private JPanel contentPane;
	private JTextField textField;
	JComboBox comboBox = new JComboBox();
	JButton btnSimulate = new JButton("Simulate");
	DefaultComboBoxModel algorithms;
	SimModel model;
	private JTextField textField_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimView frame = new SimView();
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
	public SimView() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Apa", "Bepa"}));
		
		final JSlider slider = new JSlider();
		slider.setValue(300);
		slider.setMaximum(2000);
		
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				
				if (model != null) {
					System.out.println("apapapapa" + slider.getValue());
					model.setStocksBack(slider.getValue());
					
					textField.setText("" + slider.getValue());
				}
			}
		});
		
		JLabel lblNumberOfStocks = new JLabel("Number of stocks to simulate");
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setText("300");
		textField.setColumns(10);
		
		JButton btnConfigureAlgorithm = new JButton("Configure Algorithm");
		
		JLabel lblAmountToStart = new JLabel("Amount to start with");
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_1.setText("100000");
		textField_1.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btnSimulate)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnConfigureAlgorithm)
								.addContainerGap())
							.addComponent(lblNumberOfStocks, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(slider, 0, 0, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addGap(229))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(textField_1, Alignment.LEADING)
								.addComponent(lblAmountToStart, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addContainerGap(356, Short.MAX_VALUE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSimulate)
								.addComponent(btnConfigureAlgorithm))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNumberOfStocks)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAmountToStart)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(98, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	@Override
	public void display(Object model) {
		this.model = (SimModel) model;
		
		algorithms = new DefaultComboBoxModel();
		for (String a : this.model.getAlgorithms()) {
			algorithms.addElement(a);
		}
		comboBox.setModel(algorithms);
		
		
		setVisible(true);
	}

	@Override
	public void cleanup() {
		for (ActionListener a : comboBox.getActionListeners()) {
			comboBox.removeActionListener(a);
		}
		
		for (ActionListener a : btnSimulate.getActionListeners()) {
			btnSimulate.removeActionListener(a);
		}
	}

	@Override
	public void addActions(List<Pair<String, ActionListener>> actions) {
		for (Pair<String, ActionListener> action : actions) {
			if (action.getLeft().contentEquals("ComboboxListener")) {
				comboBox.addActionListener(action.getRight());
			}
			
			else if (action.getLeft().contentEquals("Start Simulation")) {
				btnSimulate.addActionListener(action.getRight());
			}
		}
	}
}