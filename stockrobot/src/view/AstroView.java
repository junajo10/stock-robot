package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;
import java.util.Observable;

import javax.swing.JButton;

public class AstroView extends JFrame implements IView {

	private static final long serialVersionUID = 2371008027508651564L;

	public static final String OPEN_GRAPHWINDOW = "openGraphwindow";
	public static final String START_SIMULATION = "startSimulation";
	public static final String OPEN_STOCKTABLE = "stockTable";	
	public static final String WINDOW_CLOSE = "windowClose";
	public static final String OPEN_PORTFOLIOVIEW = "PortfolioView";
	
	int i = 2133;
	private JPanel contentPane;
	JButton btnSimulate = new JButton("Simulate");
	JButton btnGraph = new JButton("Graph");
	JButton btnStocks = new JButton("Stocks");
	JButton btnPortfolio = new JButton("Portfolio");
	
	WindowListener windowListener;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AstroView frame = new AstroView();
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
	public AstroView() {
		setBounds(100, 100, 254, 223);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblAstroStatus = new JLabel("ASTRo Status:");
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setForeground(Color.BLUE);
		progressBar.setValue(50);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblAstroStatus)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnPortfolio))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnStocks))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnSimulate))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnGraph)))
					.addContainerGap(95, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblAstroStatus)
							.addGap(28)
							.addComponent(btnPortfolio)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnStocks)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSimulate)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnGraph))
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	@Override
	public void display(Object model) {
		setVisible(true);
		
	}

	@Override
	public void cleanup() {
		removeWindowListener(windowListener);
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {
		
		btnSimulate.addActionListener((ActionListener) actions.get(START_SIMULATION));
		btnGraph.addActionListener((ActionListener) actions.get(OPEN_GRAPHWINDOW));
		btnStocks.addActionListener((ActionListener) actions.get(OPEN_STOCKTABLE));
		btnPortfolio.addActionListener((ActionListener) actions.get(OPEN_PORTFOLIOVIEW));
		windowListener = (WindowListener) actions.get(WINDOW_CLOSE);
		
		addWindowListener(windowListener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

}