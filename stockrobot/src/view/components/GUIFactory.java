package view.components;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class GUIFactory implements IGUIFactory {

	private final Color COLOR_WINDOW_FOREGROUND	= Color.WHITE;
	private final Color COLOR_WINDOW_BACKGROUND	= Color.BLACK;
	
	private final Color COLOR_CONTAINER_MAIN_BODY = new Color(240,240,240);
	
	private final Color COLOR_DARK_TEXT 		= Color.DARK_GRAY;
	private final Color COLOR_LIGHT_TEXT 		= Color.WHITE;
	
	private final Color COLOR_CONTAINER_BODY 	= Color.LIGHT_GRAY;
	private final Color COLOR_CONTAINER_BORDER 	= Color.GRAY;
	
	private final Color COLOR_FST_CLR_CONTAINER_BODY 	= new Color(80,140,230);
	private final Color COLOR_FST_CLR_CONTAINER_BORDER 	= new Color(70,100,200);
	
	private final Color COLOR_BUTTON			= Color.WHITE;
	@SuppressWarnings("unused")
	private final Color COLOR_BUTTON_BORDER		= Color.DARK_GRAY;
	
	private final Color COLOR_DEFAULT_BORDER		= Color.DARK_GRAY;
	
	@Override
	public JLabel getDefaultLabel(String text) {
		JLabel label = new JLabel(text);
		label.setForeground(COLOR_DARK_TEXT);
		label.setFont(new Font("SansSerif", Font.PLAIN,14));
		
		return label;
	}
	
	@Override
	public JLabel getLightLabel(String text) {
		JLabel label = new JLabel(text);
		label.setForeground(COLOR_LIGHT_TEXT);
		label.setFont(new Font("SansSerif", Font.PLAIN,14));
		
		return label;
	}
	
	@Override
	public JLabel getSubtitleLabel(String text) {
		JLabel label = getDefaultLabel(text);
		label.setFont(new Font("SansSerif", Font.BOLD,14));
		
		return label;
	}
	
	@Override
	public JLabel getLightSubtitleLabel(String text) {
		JLabel label = getLightLabel(text);
		label.setFont(new Font("SansSerif", Font.BOLD,14));
		
		return label;
	}
	
	@Override
	public JLabel getTitleLabel(String text) {
		JLabel label = getDefaultLabel(text);
		label.setFont(new Font("SansSerif", Font.BOLD,20));
		
		return label;
	}
	
	@Override
	public JLabel getLightTitleLabel(String text) {
		JLabel label = getDefaultLabel(text);
		label.setForeground(COLOR_LIGHT_TEXT);
		label.setFont(new Font("SansSerif", Font.BOLD,20));
		
		return label;
	}

	@Override
	public JButton getDefaultButton(String text) {
		
		JButton button = new JButton(text);
		button.setBackground(COLOR_BUTTON);
		button.setForeground(COLOR_DARK_TEXT);
		
		return button;
	}
	
	@Override
	public JPanel getDefaultContainer() {
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(COLOR_CONTAINER_BORDER));
		panel.setBackground(COLOR_CONTAINER_BODY);
		
		return panel;
	}
	
	@Override
	public JPanel getFstColoredContainer() {
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(COLOR_FST_CLR_CONTAINER_BORDER));
		panel.setBackground(COLOR_FST_CLR_CONTAINER_BODY);
		
		return panel;
	}

	@Override
	public JPanel getInvisibleContainer() {
		
		JPanel panel = new JPanel();
		panel.setBackground(null);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		return panel;
	}
	
	@Override
	public JFrame modifyDefaultWinow(JFrame window) {
		
		window.setForeground(COLOR_WINDOW_FOREGROUND);
		window.setBackground(COLOR_WINDOW_BACKGROUND);
		
		return window;
	}
	
	@Override
	public JPanel getMainContainer() {
		JPanel panel = new JPanel();
		panel.setBackground(COLOR_CONTAINER_MAIN_BODY);
		
		return panel;
	}
	
	@Override
	public Border getDefaultBorder() {
		
		Border border = BorderFactory.createLineBorder(COLOR_DEFAULT_BORDER);
		return border;
	}
	
	@Override
	public JTextField getDefaultTextField() {
		JTextField textField = new JTextField();
		
		return textField;
	}
}