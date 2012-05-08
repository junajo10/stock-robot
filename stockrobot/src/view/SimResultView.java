package view;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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

import utils.WindowCloseAdapter;
import utils.global.FinancialLongConverter;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

public class SimResultView extends JFrame implements IView {

	private static final long serialVersionUID = -5877884096041331653L;
	private JPanel contentPane;
	JProgressBar progressBar = new JProgressBar();
	PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	JPanel panel = new JPanel();
	
	WindowListener windowListener = new WindowCloseAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			pcs.firePropertyChange("Sim Result Close", false, true);
		}
	};
	private final JLabel lblPortfolioWorth = new JLabel("Portfolio worth:");
	private final JTextField textField = new JTextField();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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
		textField.setText("-5%");
		textField.setColumns(10);
		setBounds(100, 100, 612, 483);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		progressBar.setStringPainted(true);
		progressBar.setValue(50);
		
		JLabel lblSimulationProgress = new JLabel("Simulation progress:");
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSimulationProgress)
								.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(51)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPortfolioWorth))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSimulationProgress)
						.addComponent(lblPortfolioWorth))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
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
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {
		// TODO Auto-generated method stub
		
	}

	public void setProgress(int progress) {
		progressBar.setValue(progress);
	}
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
    
    
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

}
