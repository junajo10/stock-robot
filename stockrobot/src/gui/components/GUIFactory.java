package gui.components;

import gui.view.StockTableView;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class GUIFactory implements IGUIFactory {

	@Override
	public JLabel getDefaultLabel(String text){
		JLabel label = new JLabel(text);
		label.setForeground(Color.DARK_GRAY);
		label.setFont(new Font("SansSerif", Font.PLAIN,14));
		
		return label;
	}
	
	@Override
	public JLabel getTitleLabel(String text){
		JLabel label = getDefaultLabel(text);
		label.setFont(new Font("SansSerif", Font.BOLD,14));
		
		return label;
	}

	@Override
	public JButton getDefaultButton(String text){
		
		JButton button = new JButton(text);
		button.setBackground(Color.WHITE);
		
		return button;
	}
	
	@Override
	public JPanel getDefaultContainer(){
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.GRAY));
		panel.setBackground(Color.LIGHT_GRAY);
		
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
	public JFrame modifyDefaultWinow(JFrame window){
		
		window.setForeground(Color.WHITE);
		window.setBackground(Color.BLACK);
		
		return window;
	}

	@Override
	public JPanel getStockTableView() {
		
		return new StockTableView();
	}
	
	@Override
	public JPanel getMainContainer() {
		JPanel panel = new JPanel();
		Color backroundColor = new Color(230,230,230);
		panel.setBackground(backroundColor);
		
		return panel;
	}
}
