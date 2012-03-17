package algorithms.loader;

import java.util.List;

import javax.swing.JTextField;

import generic.Pair;
import gui.LabelledItemPanel;
import gui.StandardDialog;

@SuppressWarnings("serial")
/**
 * 
 * @author Daniel
 *
 * Will init the settings-GUI
 */
public class AlgorithmSettingsGUI  extends StandardDialog {
	private LabelledItemPanel settingsContentPane = new LabelledItemPanel();

	/**
	 * 
	 * @param settingStringFields
	 * @param settingIntegerFields
	 * @param settingDoubleFields
	 * @param settingTexts
	 */
	public AlgorithmSettingsGUI(
			List<Pair<Integer, JTextField>> settingStringFields,
			List<Pair<Integer, JTextField>> settingIntegerFields,
			List<Pair<Integer, JTextField>> settingDoubleFields, 
			List<String> settingTexts) {
		
		for (int i = 0; i < settingTexts.size(); i++) {
			if (settingStringFields.get(i).getLeft().compareTo(i) == 0) {
				settingsContentPane.addItem(settingTexts.get(i), settingStringFields.get(i).getRight());
			}
			else if (settingIntegerFields.get(i).getLeft().compareTo(i) == 0) {
				settingsContentPane.addItem(settingTexts.get(i), settingStringFields.get(i).getRight());
			}
			else if (settingDoubleFields.get(i).getLeft().compareTo(i) == 0) {
				settingsContentPane.addItem(settingTexts.get(i), settingStringFields.get(i).getRight());
			}
		}
		setContentPane(settingsContentPane);
	}

}
