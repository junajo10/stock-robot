package view;

import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.algorithmsettings.SettingsPanel;

/**
 * View that could be used for modifying an algortihm's settings
 * 
 * It should load all settings in a specific algorithm and then display some sort of mean to change that
 * For instance a dragger between 0 and 100
 * 
 * TODO: The SettingsPanel is not yet written in an MVC manner. Should be in MVC
 * 
 * @author kristian
 *
 */
public class AlgorithmSettingsView extends JFrame implements IView {

	private static final long serialVersionUID = 1L;

	private String		algorithmName;
	private JPanel 		container;
	
	public AlgorithmSettingsView( String algorithmName ) {
		
		this.algorithmName = algorithmName;
		
		//Main container for the algorithm settings window
		container = new JPanel();
		add( container );
		
		//Add some settings, a loop just for now
		for( int i = 0; i < 6; i ++ ) {
			
			addSetting( "Setting" + i, Math.round( 20 + (i * 2) ), 0, 99);
		}
		
		//Add save button
		JButton saveBtn = new JButton();
		saveBtn.setText( "Save settings" );
		container.add( saveBtn );
	}
	
	public void addSetting( String desc, double init, double min, double max ) {
		
		SettingsPanel panel = new SettingsPanel( desc, init, min, max );
		container.add(panel);
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
		
	}

	@Override
	public void display(Object model) {
		
		//Settings for the window
		setTitle( "Algorithm settings for " + algorithmName );
		setSize( 320,500 );
		setVisible(true);
	}

	@Override
	public void cleanup() {
		
		
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {
		
	}
}