package gui.view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gui.components.GUIFactory;
import gui.components.IGUIFactory;
import gui.controller.IController;
import gui.controller.MainMenuController;

/**
 * 
 * @author kristian
 *
 */
public class MainMenuView extends JPanel implements IView {

	private IController controller;
	
	private IGUIFactory guiFactory = new GUIFactory();
	
	public MainMenuView() {}
	
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
		
		//Open stock info window!
		JButton btn_openStockInfoWindow = guiFactory.getDefaultButton( "Open Stock Info" );
		pnl_Container.add(btn_openStockInfoWindow);
		
		//Bind the open stock info window to actually open a window
		((MainMenuController) controller).bindStockInfoGUIButton(btn_openStockInfoWindow);
		
		//Algorithm settings button
		JButton btn_openAlgorithmSettingsWindow = guiFactory.getDefaultButton( "Open Algorithm Settings" );
		pnl_Container.add( btn_openAlgorithmSettingsWindow );
		
		//Bind bindAlgorithmSettingsGUIButton to open a new algorithm setting view
		((MainMenuController) controller).bindAlgorithmSettingsGUIButton( btn_openAlgorithmSettingsWindow );
	}
	
	@Override
	public void registerController(IController controller) {
		// TODO Auto-generated method stub
		
		this.controller = controller;
	}
}
