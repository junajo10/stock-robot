package view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

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

import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.simulation.SimModel;
import javax.swing.SwingConstants;

/**
 * 
 * @author Daniel
 */
public class SimView extends JFrame implements IView {

	private static final long serialVersionUID = -4933445365240811334L;
	private JPanel contentPane;
	private JTextField textField;
	private JComboBox comboBox = new JComboBox();
	private JButton btnSimulate = new JButton("Simulate");
	private DefaultComboBoxModel algorithms;
	private SimModel model;
	private JTextField textFieldStartAmount;
	private JButton btnConfigureAlgorithm = new JButton("Configure Algorithm");
	
	public static final String COMBOBOX = "ComboboxListener";
	public static final String STARTSIMULATION = "Start Simulation";
	public static final String CONFIGUREALGORTIHM = "Configure Algorithm";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
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
		
		JLabel lblAmountToStart = new JLabel("Amount to start with");
		
		textFieldStartAmount = new JTextField();
		textFieldStartAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldStartAmount.setText("100000");
		textFieldStartAmount.setColumns(10);
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
								.addComponent(textFieldStartAmount, Alignment.LEADING)
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
					.addComponent(textFieldStartAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
	public void addActions(Map<String, EventListener> actions) {
		
		comboBox.addActionListener((ActionListener) actions.get(COMBOBOX));
		btnSimulate.addActionListener((ActionListener) actions.get(STARTSIMULATION));
		btnConfigureAlgorithm.addActionListener((ActionListener) actions.get(CONFIGUREALGORTIHM));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {} //NOPMD

	public long getInitialValue() {
		long value;
		try {
			value = Long.valueOf(textFieldStartAmount.getText());
		} catch (NumberFormatException e) {
			value = Long.valueOf("100000000");
		}
		
		return value;
	}
}