package gui.view;

import gui.controller.IController;

import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AlgorithmSettingsView extends JFrame implements ChangeListener, IView {

	private static final long serialVersionUID = 1L;

	private TextField 	ta;
	private String		algorithmName;
	
	public AlgorithmSettingsView( String algorithmName ) {
		
		this.algorithmName = algorithmName;
	}
	
	@Override
	public void init() {
		
		//Main container for the algorithm settings window
		JPanel container = new JPanel();
		add( container );
		
		//Instantiate a header text saying
		JTextPane header = new JTextPane();
		header.setText( "Algorithm settings for: " + algorithmName );
		container.add( header );
		
		ta = new TextField();
		ta.setBounds(0, 0, 100, 30);
		ta.setText("?");
		ta.setSize(100, 30);
		container.add(ta);
		
		JSlider fromDate = new JSlider( JSlider.HORIZONTAL, 0, 99, 0 );
		fromDate.addChangeListener(this);
		container.add( fromDate );
		
		setSize( 300,300 );
		setVisible( true );
	}
	
	/**
	 * TODO: Maybe not have this in the view
	 */
	public void stateChanged( ChangeEvent e ) {
		
		JSlider source = (JSlider) e.getSource();
		ta.setText("" + source.getValue());
		
	}
	
	public static void main( String[] args ) {
		
		System.out.println( "Test components!" );
		
		AlgorithmSettingsView tc = new AlgorithmSettingsView( "Testalgorithm!" );
	}

	@Override
	public void registerController(IController controller) {
		// TODO Auto-generated method stub
		
	}
}