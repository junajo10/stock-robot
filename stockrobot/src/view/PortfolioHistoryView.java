package view;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.util.EventListener;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import model.portfolio.PortfolioHistoryModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;

/**
 * 
 * @author Daniel
 */
public class PortfolioHistoryView extends JFrame implements IView {

	private static final long serialVersionUID = -7366514125495992396L;
	private JPanel contentPane;
	
	JPanel panel_1 = new JPanel();
	private JTable table = new JTable();
	private PortfolioHistoryModel model;
	
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
		setBounds(100, 100, 1018, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel_3 = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
				.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 262, Short.MAX_VALUE))
					.addGap(9)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
		);
		
		
		
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
		
		JLabel lblGraphPPengafrndring = new JLabel("Graph på pengaförändring");
		panel_3.add(lblGraphPPengafrndring);
		contentPane.setLayout(gl_contentPane);		
		
		setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public PortfolioHistoryView(PortfolioHistoryModel portfolioModel) {
		this.model = portfolioModel;
		
		if (portfolioModel != null) {
			table.setModel(model.getTable());
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
		/*
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		
		table.getColumnModel().getColumn(5).setPreferredWidth(300);
		table.getColumnModel().getColumn(6).setPreferredWidth(300);
		*/
		createFrame();
	}
	@Override
	public void display(Object model) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
}
