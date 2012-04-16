package gui.components;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

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
	 * This should be used as the standard text in the GUI.
	 * This label will have a light color
	 * 
	 * @param text the displayed in the label
	 * @return A text label
	 */
	public JLabel getLightLabel(String text);
	
	/**
	 * Generates a label that can be used with Swing.
	 * This should be used as the title for different categories in the GUI.
	 * 
	 * @param text the displayed in the label
	 * @return A text label
	 */
	public JLabel getTitleLabel(String text);
	
	/**
	 * Generates a label that can be used with Swing.
	 * This should be used as the title for different categories in the GUI.
	 * The color of the text is a light color.
	 * 
	 * @param text the displayed in the label
	 * @return A text label
	 */
	public JLabel getLightTitleLabel(String text);
	
	/**
	 * Generates a border that should be used with most JComponents using
	 * borders in the application. The color of the border can changed to
	 * fit better with the other components.
	 * 
	 * @return a border
	 */
	public Border getDefaultBorder();
	
	/**
	 * Generates a label that can be used with Swing.
	 * This should be used as the sub title for different categories in the GUI.
	 * 
	 * @param text the displayed in the label
	 * @return A text label
	 */
	public JLabel getSubtitleLabel(String text);
	
	/**
	 * Generates a label that can be used with Swing.
	 * This should be used as the sub title for different categories in the GUI.
	 * This label should have a light color.
	 * 
	 * @param text the displayed in the label
	 * @return A text label
	 */
	public JLabel getLightSubtitleLabel(String text);
	
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
	 * Generates a colored container that can hold different kind of objects.
	 * This with be used to group pieces of data together in the GUI. 
	 * 
	 * @return a colored container
	 */
	public JPanel getFstColoredContainer();
	
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
	
	/**
	 * Generates an default text field 
	 *
	 * @return a text field
	 */
	public JTextField getDefaultTextField();
}
