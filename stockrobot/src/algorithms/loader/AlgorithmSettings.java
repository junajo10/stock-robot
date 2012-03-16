package algorithms.loader;

import generic.Pair;
import gui.LabelledItemPanel;
import gui.StandardDialog;

import java.util.*;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import algorithms.IAlgorithm;

/**
 * @author Daniel
 * 
 * This class will manage the setting of an algorithm
 */
public class AlgorithmSettings {
	IAlgorithm algorithm;
	List<Pair<Integer, JTextField>> settingIntegerFields = new LinkedList<Pair<Integer, JTextField>>();
	List<Pair<Integer, JTextField>> settingStringFields = new LinkedList<Pair<Integer, JTextField>>();
	List<Pair<Integer, JTextField>> settingDoubleFields = new LinkedList<Pair<Integer, JTextField>>();
	
	List<String> settings = new LinkedList<String>();
	
	public AlgorithmSettings(IAlgorithm algorithm) {
		this.algorithm = algorithm;
	}
	public void showSettingsDialog() {
		int i = algorithm.getNumberOfSettings();

		for (int currentSetting = 0; currentSetting <= i; currentSetting++) {
			String type = algorithm.getSettingType(currentSetting);
			settings.add(algorithm.getSettingText(currentSetting));

			if (type.equalsIgnoreCase("String")) {
				JTextField textField = new JTextField(algorithm.getCurrentStringSetting(currentSetting), 20);
				
				settingStringFields.add(new Pair<Integer, JTextField> (currentSetting, textField));
			}
			else if (type.equalsIgnoreCase("Int")) {
				JTextField textField = new JTextField("" + algorithm.getCurrentIntSetting(currentSetting), 20);
				
				settingIntegerFields.add(new Pair<Integer, JTextField> (currentSetting, textField));
			}
			else if (type.equalsIgnoreCase("Double")) {
				JTextField textField = new JTextField("" + algorithm.getCurrentDoubleSetting(currentSetting), 20);
				
				settingDoubleFields.add(new Pair<Integer, JTextField> (currentSetting, textField));
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				AlgorithmSettingsGUI settingGUI = new AlgorithmSettingsGUI(algorithm.getNumberOfSettings(), settingStringFields, settingIntegerFields, settingDoubleFields);
				settingGUI.pack();
				settingGUI.setVisible(true);
				
				if (!settingGUI.hasUserCancelled()) {
					if (!setNewSettings()) {
						//TODO: Report error to user
					}
				}
			}
		});
	}
	
	/**
	 * Sets the new settings entered by the user
	 * @return True if the new settings were valid to the algorithm
	 */
	public boolean setNewSettings() {
		boolean allGood = true;
		
		List<Pair<Integer, Integer>> oldIntegerSettings = new LinkedList<Pair<Integer,Integer>>();
		List<Pair<Integer, String>> oldStringSettings = new LinkedList<Pair<Integer,String>>();
		List<Pair<Integer, Double>> oldDoubleSettings = new LinkedList<Pair<Integer,Double>>();
		
		
		// Store old settings if the new settings will be invalid
		for (int i = 0; i < algorithm.getNumberOfSettings(); i++) {
			String type = algorithm.getSettingType(i); 
			if (type.equalsIgnoreCase("String")) {
				oldStringSettings.add(new Pair<Integer, String>(i, algorithm.getCurrentStringSetting(i)));
			}
			else if (type.equalsIgnoreCase("Int")) {
				oldIntegerSettings.add(new Pair<Integer, Integer>(i, algorithm.getCurrentIntSetting(i)));
			}
			else if (type.equalsIgnoreCase("Double")) {
				oldDoubleSettings.add(new Pair<Integer, Double>(i, algorithm.getCurrentDoubleSetting(i)));
			}
		}
		
		for (Pair<Integer, JTextField> pair : settingIntegerFields) {
			try {
				allGood = allGood && algorithm.giveSetting(pair.getLeft(), Integer.parseInt(pair.getRight().getText()));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Pair<Integer, JTextField> pair : settingStringFields) {
			allGood = allGood && algorithm.giveSetting(pair.getLeft(), pair.getRight().getText());
		}
		for (Pair<Integer, JTextField> pair : settingDoubleFields) {
			try {
				allGood = allGood && algorithm.giveSetting(pair.getLeft(), Double.parseDouble(pair.getRight().getText()));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if (!allGood) {
			for (Pair<Integer, Integer> pair : oldIntegerSettings) {
				algorithm.giveSetting(pair.getLeft(), pair.getRight());
			}
			for (Pair<Integer, String> pair : oldStringSettings) {
				algorithm.giveSetting(pair.getLeft(), pair.getRight());
			}
			for (Pair<Integer, Double> pair : oldDoubleSettings) {
				algorithm.giveSetting(pair.getLeft(), pair.getRight());
			}
		}
		
		return allGood;
	}

}
