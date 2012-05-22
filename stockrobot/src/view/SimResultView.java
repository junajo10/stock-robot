package view;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;
import java.util.EventListener;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JProgressBar;

import utils.AbstractWindowCloseAdapter;
import utils.global.FinancialLongConverter;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

import model.simulation.SimulationHandler;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import javax.swing.JButton;

/**
 * The View for simulation results.
 * @author Daniel
 */
public class SimResultView extends JFrame implements IView {
	private static final long serialVersionUID = -5877884096041331653L;
	private JPanel contentPane;

	private JProgressBar progressBar = new JProgressBar();
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private JPanel panel = new JPanel();
	
	private WindowListener windowListener = new AbstractWindowCloseAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			pcs.firePropertyChange(SimulationHandler.RESULTCLOSE, false, true);
		}
	};
	private final JLabel lblPortfolioWorth = new JLabel("Portfolio change:");
	private final JTextField textField = new JTextField();
	private JTextField txtStartamount;
	private JTextField txtCurrentamount;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					SimResultView frame = new SimResultView();
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
	public SimResultView() {
		textField.setEditable(false);
		textField.setText("0.00%");
		textField.setColumns(10);
		setBounds(100, 100, 729, 571);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		progressBar.setStringPainted(true);
		
		JLabel lblSimulationProgress = new JLabel("Simulation progress:");
		
		txtStartamount = new JTextField();
		txtStartamount.setEditable(false);
		txtStartamount.setColumns(10);
		
		JLabel lblStartamount = new JLabel("Start Amount:");
		
		txtCurrentamount = new JTextField();
		txtCurrentamount.setEditable(false);
		txtCurrentamount.setColumns(10);
		
		JLabel lblCurrentAmount = new JLabel("Current Amount:");
		
		JButton btnShowHistory = new JButton("Show History");
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSimulationProgress)
								.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(29)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblStartamount)
								.addComponent(txtStartamount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(13)
									.addComponent(lblCurrentAmount))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(18)
									.addComponent(txtCurrentamount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
									.addComponent(btnShowHistory))
								.addComponent(lblPortfolioWorth))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSimulationProgress)
						.addComponent(lblStartamount)
						.addComponent(lblCurrentAmount)
						.addComponent(lblPortfolioWorth))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtStartamount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtCurrentamount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnShowHistory))
					.addGap(18)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
	}

	@Override
	public void display(Object model) {
		setVisible(true);
		this.addWindowListener(windowListener);
	}

	@Override
	public void cleanup() {} //NOPMD

	@Override
	public void addActions(Map<String, EventListener> actions) {} //NOPMD

	public void setProgress(int progress) {
		progressBar.setValue(progress);
	}
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
    
    @Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
    	pcs.removePropertyChangeListener(listener);
    }

	public void setWorth(double change) {
		DecimalFormat df = new DecimalFormat("#.##");
		textField.setText("" + df.format(change) + "%");
	}
	public void setPieView(Map<String, Long> pieData) {
        DefaultPieDataset result = new DefaultPieDataset();
        
        for (Entry<String, Long> e : pieData.entrySet()) {
        	result.setValue(e.getKey(), FinancialLongConverter.toDouble(e.getValue()));
        }
        
        JFreeChart chart = createChart(result);
        ChartPanel chartPanel = new ChartPanel(chart);
        
        panel.removeAll();
        panel.add(chartPanel);
        panel.setVisible(true);
        //pack();
        validate();
	}
	
    private JFreeChart createChart(PieDataset dataset) {
        
        //JFreeChart chart = ChartFactory.createPieChart3D(
    	JFreeChart chart = ChartFactory.createPieChart(
            "Stock Distribution",  				// chart title
            dataset,                // data
            true,                   // include legend
            true,
            false
        );
    	
        PiePlot plot = (PiePlot) chart.getPlot();
        //PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }

	@Override
	public void propertyChange(PropertyChangeEvent evt) {} //NOPMD
}