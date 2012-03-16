package algorithms.loader;

import java.util.List;

import javax.swing.JTextField;

import generic.Pair;
import gui.LabelledItemPanel;
import gui.StandardDialog;

@SuppressWarnings("serial")
public class AlgorithmSettingsGUI  extends StandardDialog {
	private LabelledItemPanel myContentPane = new LabelledItemPanel();

	public AlgorithmSettingsGUI(int settings,
			List<Pair<Integer, JTextField>> settingStringFields,
			List<Pair<Integer, JTextField>> settingIntegerFields,
			List<Pair<Integer, JTextField>> settingDoubleFields) {
		
		for (int i = 0; i < settings; i++) {
			if (settingStringFields.get(i).getLeft().compareTo(i) == 0) {
				myContentPane.add(settingStringFields.get(i).getRight());
			}
			else if (settingIntegerFields.get(i).getLeft().compareTo(i) == 0) {
				myContentPane.add(settingStringFields.get(i).getRight());
			}
			else if (settingDoubleFields.get(i).getLeft().compareTo(i) == 0) {
				myContentPane.add(settingStringFields.get(i).getRight());
			}
		}
	}

}
