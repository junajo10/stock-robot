package gui.components;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Mattias Markehed
 * mattias.markehed@gmail.com
 *
 * filename: IGUIFactory.java
 * Description:
 * IGUIFactory is a factory used to produce GUI components used in the windows.
 */
public interface IGUIFactory {

	/**
	 * Generates a label that can be used with Swing.
	 * This should be used as the standard text in the GUI.
	 * 
	 * @param text the displayed in the label
	 * @return A text label
	 */
	public JLabel getDefaultLabel(String text);
	
	/**
	 * Generates a label that can be used with Swing.
	 * This should be used as the title for different categories in the GUI.
	 * 
	 * @param text the displayed in the label
	 * @return A text label
	 */
	public JLabel getTitleLabel(String text);
	
	/**
	 * Generates a button that can be used with Swing.
	 * This should be used as the standard button in the GUI.
	 * 
	 * @param text the displayed in the button
	 * @return A button compatible with swing
	 */
	public JButton getDefaultButton(String text);
	
	/**
	 * Generates a container that can hold different kind of objects.
	 * This with be used to group pieces of data together in the GUI. 
	 *
	 * @return container
	 */
	public JPanel getDefaultContainer();
	
	/**
	 * Generates an container that holds al other content in
	 * the window.
	 *
	 * @return container
	 */
	public JPanel getMainContainer();
	
	/**
	 * Generates an container without background that can hold different 
	 *
	 * @return container
	 */
	public JPanel getInvisibleContainer();
	
	/**
	 * Generates a new StockTableView instance
	 * 
	 * @return a new StockTableView instance
	 */
	public JPanel getStockTableView();
	
	/**
	 * Modifies a window returning the window with default style.
	 * This method should be called by each new JFrame.
	 * 
	 * @param window the window tom modify
	 * @return the window with modified style
	 */
	public JFrame modifyDefaultWinow(JFrame window);
}
