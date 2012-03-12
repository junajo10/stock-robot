package gui;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class SettingsGUITest extends StandardDialog {
	private LabelledItemPanel myContentPane = new LabelledItemPanel();
	private JTextField setting1 = new JTextField();
	private JTextField setting2 = new JTextField();
	private JTextField setting3 = new JTextField();
	private JTextField setting4 = new JTextField();
	private JTextField setting5 = new JTextField();
	private JTextArea myAddressField = new JTextArea(3, 20);
	
	public SettingsGUITest() {
		 myContentPane.addItem("Inställning 1", setting1);
		 myContentPane.addItem("Inställning 2", setting2);
		 myContentPane.addItem("Inställning 3", setting3);
		 myContentPane.addItem("Inställning 4", setting4);
		 myContentPane.addItem("Inställning 5", setting5);
		 myContentPane.addItem("Inställning 6", new JScrollPane(myAddressField,
		            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		 setContentPane(myContentPane);
		 
	}
	public static void main(String[] args) {
		SettingsGUITest test = new SettingsGUITest();
		test.pack();
		test.setVisible(true);
		
		if (test.hasUserCancelled()) {
			System.out.println("HEJ");
		}
	}
}
