package view;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
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
	public static final String WINDOWCLOSE = "Window Close";

	/**
	 * Create the frame.
	 */
	public SimView() {
		setResizable(false);
		setTitle("Simulate Algorithms");
		setBounds(100, 100, 252, 276);
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
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(lblAmountToStart)
							.addContainerGap(107, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnSimulate, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
								.addComponent(btnConfigureAlgorithm, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
								.addComponent(textFieldStartAmount, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(slider, 0, 0, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
								.addComponent(comboBox, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE))
							.addGap(147))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNumberOfStocks)
							.addContainerGap(218, Short.MAX_VALUE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(btnConfigureAlgorithm)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNumberOfStocks)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAmountToStart)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldStartAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnSimulate, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(15, Short.MAX_VALUE))
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
		
		for (ActionListener a : btnConfigureAlgorithm.getActionListeners()) {
			btnConfigureAlgorithm.removeActionListener(a);
		}
		for (WindowListener a : getWindowListeners()) {
			removeWindowListener(a);
		}
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {
		cleanup();
		
		comboBox.addActionListener((ActionListener) actions.get(COMBOBOX));
		btnSimulate.addActionListener((ActionListener) actions.get(STARTSIMULATION));
		btnConfigureAlgorithm.addActionListener((ActionListener) actions.get(CONFIGUREALGORTIHM));
		
		addWindowListener((WindowListener) actions.get(WINDOWCLOSE));
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