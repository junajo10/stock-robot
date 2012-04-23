package view;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import javax.swing.WindowConstants;

import model.portfolio.PortfolioHandler;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class SimulationView extends JFrame {
	private static final long serialVersionUID = -5260564848833766872L;
	private JPanel jPanel1;
	private JButton jButton1 = new JButton();;
	private JProgressBar jProgressBar1;
	private JComboBox jComboBox1;

	/**
	* Auto-generated main method to display this JFrame
	*/
	/*
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SimulationView inst = new SimulationView();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	*/
	public SimulationView() {
		super();
		
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jPanel1 = new JPanel();
				BorderLayout jPanel1Layout = new BorderLayout();
				jPanel1.setLayout(jPanel1Layout);
				getContentPane().add(jPanel1, BorderLayout.CENTER);

					ComboBoxModel jComboBox1Model = getAlgorithms();
					jComboBox1 = new JComboBox();
					jPanel1.add(jComboBox1, BorderLayout.NORTH);
					jComboBox1.setModel(jComboBox1Model);

					jPanel1.add(jButton1, BorderLayout.SOUTH);
					jButton1.setText("jButton1");
					jProgressBar1 = new JProgressBar();
					jPanel1.add(jProgressBar1, BorderLayout.CENTER);
					jProgressBar1.setValue(0);

			}
			pack();
			this.setSize(225, 108);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}

	public ComboBoxModel getAlgorithms() {
		List<String> algorithmNames = null;
		if (PortfolioHandler.getInstance() == null) {
			algorithmNames = new LinkedList<String>();
			algorithmNames.add("apa");
			algorithmNames.add("bepa");
		}
		else {
			algorithmNames = PortfolioHandler.getInstance().getAlgorithmNames();
		}
		DefaultComboBoxModel cbm = new DefaultComboBoxModel();
		for (String a : algorithmNames) {
			cbm.addElement(a);
		}
		return cbm;
	}
	public void addGoListener(ActionListener a) {
		jButton1.addActionListener(a);
	}

	public void init() {
		initGUI();
		this.setVisible(true);
	}

	public String getSelectedAlgorithm() {
		return jComboBox1.getSelectedItem().toString();
	}

	public void setProgress(int newValue) {
		this.jProgressBar1.setValue(newValue);
		
	}
}
