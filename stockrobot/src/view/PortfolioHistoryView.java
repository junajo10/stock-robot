package view;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.EventListener;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import model.portfolio.PortfolioHistoryModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.ScrollPaneConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 * 
 * @author Daniel
 */
public class PortfolioHistoryView extends JFrame implements IView {

	private static final long serialVersionUID = -7366514125495992396L;
	private JPanel contentPane;
	
	JPanel panel_1 = new JPanel();
	JPanel panelChart = new JPanel();
	private JTable table = new JTable();
	private PortfolioHistoryModel portfolioModel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PortfolioHistoryView frame = new PortfolioHistoryView(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void createFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Portfolio History");
		setBounds(100, 100, 1015, 601);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panelChart, GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelChart, GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE))
		);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
			},
			new String[] {
				"Generating data"
			}
		));
		
		
		
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(false);
		
		scrollPane.setViewportView(table);
		
		JTextArea txtrStats = new JTextArea();
		txtrStats.setEditable(false);
		txtrStats.setRows(40);
		txtrStats.setText("Stats\nFavorite Stock:\nBla:\nBle:");
		
		JLabel lblStatistics = new JLabel("Statistics:");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblStatistics))
						.addComponent(txtrStats, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(102, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(4)
					.addComponent(lblStatistics)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtrStats, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(155, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		contentPane.setLayout(gl_contentPane);		
		
		setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public PortfolioHistoryView(PortfolioHistoryModel portfolioModel) {
		this.portfolioModel = portfolioModel;
		
		if (portfolioModel != null) {
			table.setModel(portfolioModel.getTable());
			

		}
		else {
			table.setModel(new DefaultTableModel(
					new Object[][] {
						{null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null},
						{null, null, null, null, null, null, null},
					},
					new String[] {
							"Name","Market","Bought for","Sold for", "Profit %","BuyDate","SellDate"
					}
				));
		}
		
		createFrame();
	}
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
            null, 
            "Date", 
            "Value",
            dataset, 
            true, 
            true, 
            false
        );
        final XYItemRenderer renderer = chart.getXYPlot().getRenderer();
        final StandardXYToolTipGenerator g = new StandardXYToolTipGenerator(
            StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
            new SimpleDateFormat("d-MMM-yyyy"), new DecimalFormat("0.00")
        );
        return chart;
    }
	@Override
	public void display(Object model) {
		this.portfolioModel = (PortfolioHistoryModel) model;
		
		portfolioModel.addPropertyChangeListener(this);
		portfolioModel.startGeneratingPortfolioDate();
	}
	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addActions(Map<String, EventListener> actions) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().contentEquals("Table Generated")) {
			table.setModel((TableModel) evt.getNewValue());
		}
		else if (evt.getPropertyName().contentEquals("Time Series")) {
	    	@SuppressWarnings("unchecked")
			Map<String, TimeSeries> apa =  (Map<String, TimeSeries>) evt.getNewValue();
	        final TimeSeries portfolioBalance = apa.get("Portfolio Balance");
	        final TimeSeries portfolioWorth = apa.get("Worth");
	        
	        
	        final TimeSeriesCollection timeSeriesDataset = new TimeSeriesCollection();
	        timeSeriesDataset.addSeries(portfolioBalance);
	        timeSeriesDataset.addSeries(portfolioWorth);
			
	        XYDataset dataset = timeSeriesDataset;
			
	        final JFreeChart chart = createChart(dataset);
			
			final ChartPanel chartPanel = new ChartPanel(chart);
			
			chartPanel.setPreferredSize(new java.awt.Dimension(900, 200));
			
			panelChart.add(chartPanel);
		}
		
	}
}
