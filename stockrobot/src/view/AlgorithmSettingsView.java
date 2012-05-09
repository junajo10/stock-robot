package view;

import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AlgorithmSettingsView extends JFrame implements IView {

	private static final long serialVersionUID = 1L;

	private String		algorithmName;
	private JPanel 		container;
	
	public AlgorithmSettingsView( String algorithmName ) {
		
		this.algorithmName = algorithmName;
	}
	
	public void addSetting( String desc, double init, double min, double max ) {
		
		//JPanel panel = ViewFactory.getSettingsPanel( desc, init, min, max );
		JButton panel = new JButton("hey");
		container.add( panel );
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
		
	}

	@Override
	public void display(Object model) {
		
		//Main container for the algorithm settings window
		container = new JPanel();
		add( container );
		
		//Add some settings, a loop just for now
		for( int i = 0; i < 6; i ++ ) {
			
			addSetting( "Setting" + i, 20 + (i * 2), 0, 99);
		}
		
		//Add save button
		JButton saveBtn = new JButton();
		saveBtn.setText( "Save settings" );
		container.add( saveBtn );
		
		//Settings for the window
		setTitle( "Algorithm settings for " + algorithmName );
		setSize( 320,500 );
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActions(Map<String, EventListener> actions) {
		// TODO Auto-generated method stub
		
	}
}