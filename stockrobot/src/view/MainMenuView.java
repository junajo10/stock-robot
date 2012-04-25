package view;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import view.components.GUIFactory;
import view.components.IGUIFactory;

/**
 * 
 * @author kristian
 *
 */
public class MainMenuView extends JPanel {

	/**
	 * Serial version!
	 */
	private static final long serialVersionUID = -5839048480274874628L;
	
	private IGUIFactory guiFactory = new GUIFactory();
	private JButton btn_openAlgorithmSettingsWindow; 
	private JButton btn_openStockInfoWindow;
	private JButton btn_openSimulation;
	private JButton btn_openGraphView;
	
	public MainMenuView() {
		init();
	}
	
	public void init() {
		
		//Background, invisible!
		setBackground(null);
		
		JPanel pnl_Container = guiFactory.getDefaultContainer();
		pnl_Container.setLayout(new BoxLayout(pnl_Container, BoxLayout.Y_AXIS));
		add(pnl_Container);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel header = guiFactory.getSubtitleLabel("Main Menu:");
		header.setHorizontalAlignment(SwingConstants.LEFT);
		pnl_Container.add(header);
		
		GUIFactory fact = new GUIFactory();
		
		btn_openStockInfoWindow = fact.getDefaultButton("Stock Info");
		pnl_Container.add(btn_openStockInfoWindow);
		
		btn_openAlgorithmSettingsWindow = fact.getDefaultButton("Algorithm settings");
		pnl_Container.add(btn_openAlgorithmSettingsWindow);
		
		btn_openSimulation = fact.getDefaultButton("Simulation");
		pnl_Container.add(btn_openSimulation);
		
		btn_openGraphView = fact.getDefaultButton("Graph view");
		pnl_Container.add(btn_openGraphView);
	}
	
	//Bind bindAlgorithmSettingsGUIButton to open a new algorithm setting view
	public void bindStockInfoWindow(ActionListener listener) {
		btn_openStockInfoWindow.addActionListener(listener);
	}

	//Open stock info window!
	public void bindOpenAlgorithmSettings(ActionListener listener) {
		btn_openAlgorithmSettingsWindow.addActionListener(listener);
	}
	
	public void bindOpenSimulation(ActionListener listener) {
		btn_openSimulation.addActionListener(listener);
	}
	
	public void bindOpenGraphView(ActionListener listener) {
		btn_openGraphView.addActionListener(listener);
	}	
}