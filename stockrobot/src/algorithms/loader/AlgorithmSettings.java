package algorithms.loader;

import generic.Pair;
import gui.LabelledItemPanel;
import gui.StandardDialog;

import java.util.*;

import javax.swing.JTextField;

import algorithms.IAlgorithm;

/**
 * 
 * @author daniel
 * This class will manage the settings of an algorithm
 */
public class AlgorithmSettings {
	IAlgorithm algorithm;
	List<Pair<Integer, Integer>> integerSettings = new LinkedList<Pair<Integer, Integer>>();
	List<Pair<Integer, String>> stringSettings = new LinkedList<Pair<Integer, String>>();
	List<Pair<Integer, Double>> doubleSettings = new LinkedList<Pair<Integer, Double>>();
	List<String> settings = new LinkedList<String>();
	
	public AlgorithmSettings(IAlgorithm algorithm) {
		this.algorithm = algorithm;
	}
	public void showSettingsDialog() {
		int i = algorithm.getNumberOfSettings();
		
		LabelledItemPanel myContentPane = new LabelledItemPanel();
		List<JTextField> settingFields = new LinkedList<JTextField>();
		StandardDialog dialog = new StandardDialog();
		
		for (int currentSetting = 0; currentSetting <= i; currentSetting++) {
			String type = algorithm.getSettingType(currentSetting);
			settings.add(algorithm.getSettingText(currentSetting));

			if (type.equalsIgnoreCase("String")) {
				stringSettings.add(new Pair<Integer, String>(currentSetting, algorithm.getCurrentStringSetting(currentSetting)));
				
				JTextField textField = new JTextField(20);
				settingFields.add(textField);
				myContentPane.addItem(algorithm.getSettingText(currentSetting), textField);
			}
			else if (type.equalsIgnoreCase("Int")) {
				integerSettings.add(new Pair<Integer, Integer>(currentSetting, algorithm.getCurrentIntSetting(currentSetting)));
				
				JTextField textField = new JTextField(20);
				settingFields.add(textField);
				myContentPane.addItem(algorithm.getSettingText(currentSetting), textField);
			}
			else if (type.equalsIgnoreCase("Double")) {
				doubleSettings.add(new Pair<Integer, Double>(currentSetting, algorithm.getCurrentDoubleSetting(currentSetting)));
				
				JTextField textField = new JTextField(20);
				settingFields.add(textField);
				myContentPane.addItem(algorithm.getSettingText(currentSetting), textField);
			}
			else {
				System.out.println("ASDFASDFASDF");
			}
		}
		//TODO: start in new thread + singleton
		
		dialog.setContentPane(myContentPane);
		dialog.setVisible(true);
		
		//TODO: Fetch the data
		if (dialog.hasUserCancelled()) {
			System.out.println("cancel");
		}
		else {
			System.out.println("bepa ok");
		}
	}
	public boolean setNewSettings() {
		boolean allGood = true;
		for (Pair<Integer, Integer> pair : integerSettings) {
			allGood = allGood && algorithm.giveSetting(pair.getLeft(), pair.getRight());
		}
		for (Pair<Integer, String> pair : stringSettings) {
			allGood = allGood && algorithm.giveSetting(pair.getLeft(), pair.getRight());
		}
		for (Pair<Integer, Double> pair : doubleSettings) {
			allGood = allGood && algorithm.giveSetting(pair.getLeft(), pair.getRight());
		}
		
		// TODO: if !allgood => set defalut settings/previous
		return allGood;
	}

}
