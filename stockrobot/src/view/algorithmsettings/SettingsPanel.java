package view.algorithmsettings;

import java.awt.TextField;


import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.components.GUIFactory;

/**
 * 
 * 
 * @author kristian
 *
 */
public class SettingsPanel extends JPanel implements ChangeListener {
	
	/**
	 * Serial Version!
	 */
	private static final long serialVersionUID = -5023291097157808280L;
	
	private double _minValue;
	private double _maxValue;
	private double _initValue;
	private String _desc;
	
	private TextField 	ta;
	
	public SettingsPanel( String desc, double initValue, double minValue, double maxValue ) {
		
		_desc = desc;
		_minValue = minValue;
		_maxValue = maxValue;
		_initValue = initValue;
		
		init();
	}
	
	public void init() {
		
		//Factory baby
		GUIFactory fact = new GUIFactory();
		
		JPanel container = fact.getDefaultContainer();
		add( container );
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		//Instantiate a header text saying
		JLabel header = fact.getDefaultLabel("Something");
		header.setText( _desc );
		container.add( header );
		
		JPanel subContainer = fact.getInvisibleContainer();
		container.add( subContainer );
		
		ta = new TextField();
		ta.setBounds(0, 0, 100, 30);
		ta.setText( "" + _initValue );
		ta.setSize( 100, 30 );
		subContainer.add( ta );
		
		JSlider fromDate = new JSlider( JSlider.HORIZONTAL, (int) Math.round( _minValue ), (int) Math.round( _maxValue ), (int) Math.round( _initValue ) );
		fromDate.addChangeListener(this);
		subContainer.add( fromDate );
	}
	
	/**
	 * TODO: Maybe not have this in the view
	 */
	public void stateChanged( ChangeEvent e ) {
		
		JSlider source = (JSlider) e.getSource();
		ta.setText("" + source.getValue());
		
	}
}