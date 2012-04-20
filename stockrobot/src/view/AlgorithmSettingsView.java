package view;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.algorithmsettings.SettingsPanel;

import controller.gui.IController;

public class AlgorithmSettingsView extends JFrame implements IView {

	private static final long serialVersionUID = 1L;

	
	private String		algorithmName;
	private JPanel 		container;
	
	public AlgorithmSettingsView( String algorithmName ) {
		
		this.algorithmName = algorithmName;
		
		init();
	}
	
	@Override
	public void init() {
		
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
		setVisible( true );
	}
	
	public void addSetting( String desc, double init, double min, double max ) {
		
		SettingsPanel panel = new SettingsPanel( desc, init, min, max );
		container.add( panel );
	}
	
	public static void main( String[] args ) {
		
		System.out.println( "Test components!" );
		
		new AlgorithmSettingsView( "Testalgorithm!" );
	}

	@Override
	public void registerController(IController controller) {
		// TODO Auto-generated method stub
		
	}
}