package gui.components.wizard;

import javax.swing.JPanel;

public abstract class WizardPage extends JPanel {

	private static final long serialVersionUID = 5355811249972492866L;
	
	protected abstract void load();
}
